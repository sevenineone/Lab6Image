package ru.spbstu.image

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import ru.spbstu.image.databinding.ActivityMainBinding
import android.graphics.BitmapFactory
import android.util.Log
import java.net.URL

class MainActivity : AppCompatActivity() {
    //koshelev
    private val url1 = "http://kspt.icc.spbstu.ru/media/files/photo/people/koshelev_port_web.jpg"

    //gelich
    private val url2 =
        "http://kspt.icc.spbstu.ru/media/files/photo/people/novopashenny_port_web.jpg"

    //philippov
    private val url3 = "http://kspt.icc.spbstu.ru/media/files/photo/people/filippov_port_web.jpg"
    private lateinit var binding: ActivityMainBinding

    private lateinit var backgroundThread: Future<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("thread", "CREADTED")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.button1.setOnClickListener {
            setImage(url1)
        }
        binding.button2.setOnClickListener {
            setImage(url2)
        }
        binding.button3.setOnClickListener {
            setImage(url3)
        }
        setContentView(binding.root)
    }

    override fun onDestroy() {
        Log.i("thread", "destroyed, threads: ${Thread.getAllStackTraces().size}")
        super.onDestroy()
        backgroundThread.cancel(true)
    }

    private fun setImage(url: String) {
        Log.i("thread", "threads: ${Thread.getAllStackTraces().size}")
        backgroundThread = (applicationContext as ExecutorClass).executor.submit {
            val newUrl = URL(url)
            val mIcon = BitmapFactory.decodeStream(newUrl.openConnection().getInputStream())
            binding.image.post {
                binding.image.setImageBitmap(mIcon)
            }
        }
    }

    class ExecutorClass : Application() {
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
    }
}