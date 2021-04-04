package com.diego.android.kitchen.combinestagram

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


class SharedViewModel : ViewModel() {

    private val selectedPhotos = MutableLiveData<List<Photo>>()
    private val thumbnailStatus = MutableLiveData<ThumbnailStatus>()
    private val subscriptions = CompositeDisposable()
    private val imagesSubject: BehaviorSubject<MutableList<Photo>> = BehaviorSubject.createDefault(
        mutableListOf()
    )

    init {
        subscriptions.add(imagesSubject.subscribe {
            selectedPhotos.value = imagesSubject.value
        })
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }

    fun clearPhoto() {
        imagesSubject.value.clear()
    }

    fun subscribeSelectedPhotos(fragment: PhotosBottomDialogFragment) {
        val newPhotos = fragment.selectedPhotos.share()

        subscriptions.add(newPhotos
            .doOnComplete {
                Log.v("SharedViewModel", "subscriptions::doOnComplete")
            }
            // Only allow up to 6 images in the collage.
            .takeWhile {
                imagesSubject.value.size < 6
            }
            // Filter out portrait images.
            .filter { newImage ->
                val bitmap = BitmapFactory.decodeResource(fragment.resources, newImage.drawable)
                bitmap.width > bitmap.height
            }
            // Filter out images that are already in the collage
            .filter { newImage ->
                !(imagesSubject.value.map {
                    it.drawable
                }.contains(newImage.drawable))

            }
            .subscribe {
                imagesSubject.value.add(it)
                imagesSubject.onNext(imagesSubject.value)
            })
        subscriptions.add(newPhotos.ignoreElements().subscribe {
            thumbnailStatus.postValue(ThumbnailStatus.READY)
        })
    }

    fun getSelectedPhotos(): LiveData<List<Photo>> = selectedPhotos
    fun getThumbnailStatus(): LiveData<ThumbnailStatus> = thumbnailStatus

    // This function is an example of a custom Observable. The observable returns a single String;
    // which is the path where the image was saved, or an exception.
    fun saveBitmapFromImageView(imageView: ImageView, context: Context): Single<String> {
        return Single.create { observer ->
            val tmpImg = "${System.currentTimeMillis()}.png"

            val os: OutputStream?

            val collagesDirectory = File(context.getExternalFilesDir(null), "collages")
            if (!collagesDirectory.exists()) {
                collagesDirectory.mkdirs()
            }

            val file = File(collagesDirectory, tmpImg)

            try {
                os = FileOutputStream(file)
                val bitmap = (imageView.drawable as BitmapDrawable).bitmap
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
                os.flush()
                os.close()
                observer.onSuccess(tmpImg)
            } catch (e: IOException) {
                Log.e("MainActivity", "Problem saving collage", e)
                observer.onError(e)
            }
        }

    }
}