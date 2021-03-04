
package com.diego.android.kitchen.combinestagram

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

  private lateinit var viewModel: SharedViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    title = getString(R.string.collage)

    viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

    addButton.setOnClickListener {
      actionAdd()
    }

    clearButton.setOnClickListener {
      actionClear()
    }

    saveButton.setOnClickListener {
      actionSave()
    }
  }

  private fun actionAdd() {
    println("actionAdd")
  }

  private fun actionClear() {
    println("actionClear")
  }

  private fun actionSave() {
    println("actionSave")
  }
}
