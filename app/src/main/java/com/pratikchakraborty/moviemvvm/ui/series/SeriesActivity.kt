package com.pratikchakraborty.moviemvvm.ui.series

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pratikchakraborty.moviemvvm.R
import com.pratikchakraborty.moviemvvm.ui.popular_movie.MainActivity
import kotlinx.android.synthetic.main.activity_series.*

class SeriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_series)
        back_btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}