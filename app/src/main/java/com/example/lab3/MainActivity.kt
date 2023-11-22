package com.example.lab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lab3.databinding.ActivityMainBinding

lateinit var mainBinding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        setSupportActionBar(mainBinding.mainToolbar)
        mainBinding.mainToolbar.showOverflowMenu()

    }
}