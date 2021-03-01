package com.diego.android.kitchen.rxkotlin

import android.app.Application
import android.content.Context
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.diego.android.kitchen.rxkotlin.helpers.FileReadError
import exampleOf
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import kotlin.text.Charsets.UTF_8

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RxKotlinExtendedObservables {

    lateinit var app: Application

    @Before fun setUp() {
        app = getApplicationContext<Application>()
    }

    @Test
    /**
     * See [RxKotlin Single](http://reactivex.io/documentation/single.html)
     */
    fun exampleOfSingle() {
        exampleOf("Example of using a Single Observable") {
            // Singles emit just one result.
            val subscription = CompositeDisposable()
            val observer = loadText("a_new_hope.txt")
                .subscribe({ text ->
                    // On Success
                    println(text)
                }, { error ->
                    // On Error
                    println("Error: $error")
                })
            subscription.add(observer)
        }
    }

    private fun loadText(fileName: String): Single<String> {
        return Single.create create@{ emitter ->

            val file =  app.applicationContext.fileList()
            println("Aqui: $file")
//            if (!file.exists()) {
//                emitter.onError(FileReadError.FileNotFound())
//                return@create
//            }
//
//            val contents = file.readText(UTF_8)
//            emitter.onSuccess(contents)
        }
    }
}