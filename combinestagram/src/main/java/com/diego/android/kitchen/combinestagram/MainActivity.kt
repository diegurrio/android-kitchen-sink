package com.diego.android.kitchen.combinestagram

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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

        // Add an observer to the LiveData object in the view model
        viewModel.getSelectedPhotos().observe(this, Observer { photos ->
            photos?.let {
                if (it.isNotEmpty()) {
                    val bitmaps =
                        photos.map { BitmapFactory.decodeResource(resources, it.drawable) }
                    val newBitmap = combineImages(bitmaps)
                    collageImage.setImageDrawable(BitmapDrawable(resources, newBitmap))
                } else {
                    actionClear()
                }
            }
        })
    }

    private fun actionAdd() {
        viewModel.addPhoto(PhotoStore.photos[0])
    }

    private fun actionClear() {
        viewModel.clearPhoto()
        collageImage.setImageResource(android.R.color.transparent)
    }

    private fun actionSave() {
        println("actionSave")
    }
}
