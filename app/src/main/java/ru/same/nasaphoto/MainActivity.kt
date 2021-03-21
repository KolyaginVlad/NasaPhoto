package ru.same.nasaphoto

import android.graphics.Bitmap
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class MainActivity : AppCompatActivity(), Presenter.View {
    private lateinit var image: ImageView
    private lateinit var date: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var calendar: CalendarView
    private lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Добавляем toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        image = findViewById(R.id.image)
        date = findViewById(R.id.date)
        calendar = findViewById(R.id.calendar)
        progressBar = findViewById(R.id.proBar)
        presenter = Presenter(this)
        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            var day = ""
            if (dayOfMonth<10)
             day = "0$dayOfMonth"
            else day = dayOfMonth.toString()
            presenter.setDate("$year-${month + 1}-$day")
            presenter.getData()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        showCalendar()
        return super.onOptionsItemSelected(item)
    }
    override fun setData(bitmap: Bitmap?, date: String) {
        runOnUiThread {

            if (bitmap == null) {
                this.date.text = date
                this.date.visibility = View.VISIBLE
                image.setImageBitmap(null)
            } else {
                val display = windowManager.defaultDisplay
                val metricsB = DisplayMetrics()
                display.getMetrics(metricsB)
                val bmap = Bitmap.createScaledBitmap(
                    bitmap,
                    metricsB.widthPixels,
                    metricsB.widthPixels,
                    true
                )
                image.setImageBitmap(bmap)
                this.date.text = date
                this.date.visibility = View.VISIBLE
                image.visibility = View.VISIBLE
            }
        }

    }

    override fun showProgressBar() {
        runOnUiThread {
            calendar.visibility = View.INVISIBLE
            date.visibility = View.INVISIBLE
            image.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
        }

    }

    override fun hideProgressBar() {
        runOnUiThread {
            date.visibility = View.VISIBLE
            image.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
        }

    }

    override fun showCalendar() {
        runOnUiThread {
            date.visibility = View.INVISIBLE
            image.visibility = View.INVISIBLE
            calendar.visibility = View.VISIBLE
        }
    }
}