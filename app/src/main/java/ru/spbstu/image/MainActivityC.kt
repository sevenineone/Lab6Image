package ru.spbstu.image

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.spbstu.image.databinding.ActivityMainBinding
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivityC : AppCompatActivity() {
    //koshelev
    private val url1 = "http://kspt.icc.spbstu.ru/media/files/photo/people/koshelev_port_web.jpg"

    //gelich
    private val url2 =
        "http://kspt.icc.spbstu.ru/media/files/photo/people/novopashenny_port_web.jpg"

    //philippov
    private val url3 = "http://kspt.icc.spbstu.ru/media/files/photo/people/filippov_port_web.jpg"

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("thread", "CREATED")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.button1.setOnClickListener {
            setImage(URL(url1))
        }
        binding.button2.setOnClickListener {
            setImage(URL(url2))
        }
        binding.button3.setOnClickListener {
            setImage(URL(url3))
        }
        setContentView(binding.root)
    }

    override fun onDestroy() {
        Log.i("thread", "destroyed, threads: ${Thread.getAllStackTraces().size}")
        super.onDestroy()

    }

    private fun setImage(url: URL) {
        lifecycleScope.launchWhenCreated {
            Log.i("thread", "threads: ${Thread.getAllStackTraces().size}")
            val mIcon = withContext(Dispatchers.IO) {
                BitmapFactory.decodeStream(url.openConnection().getInputStream())//???
            }
            binding.image.post {
                binding.image.setImageBitmap(mIcon)
            }
        }
    }


}