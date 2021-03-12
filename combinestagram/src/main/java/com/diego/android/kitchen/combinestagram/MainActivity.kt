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
                    updateUI(photos)
                } else {
                    actionClear()
                }
            }
        })
    }

    private fun updateUI(photos: List<Photo>) {
        // Update the state of the button and disable saving if there's no image to be saved and
        // if there's an empty spot on the collage.
        saveButton.isEnabled = photos.isNotEmpty() && (photos.size % 2 == 0)
        clearButton.isEnabled = photos.isNotEmpty()
        addButton.isEnabled = photos.size < 6
        title = if (photos.isNotEmpty()){
            resources.getQuantityString(R.plurals.photos_format, photos.size, photos.size)
        } else{
            getString(R.string.collage)
        }
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
