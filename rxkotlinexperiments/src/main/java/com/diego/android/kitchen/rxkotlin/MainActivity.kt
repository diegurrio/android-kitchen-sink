package com.diego.android.kitchen.rxkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.diego.android.kitchen.rxkotlin.helpers.FileReadError
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}