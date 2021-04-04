package com.diego.android.kitchen.rxkotlin

import android.app.Application
import android.content.res.Resources
import androidx.annotation.RawRes
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.diego.android.kitchen.rxkotlin.helpers.FileReadError
import exampleOf
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.InputStream

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RxKotlinExtendedObservables {

    lateinit var app: Application

    @Before
    fun setUp() {
        app = getApplicationContext<Application>()
    }

    /**
     * See [RxKotlin Single](http://reactivex.io/documentation/single.html)
     */
    @Test
    fun exampleOfSingle() {
        exampleOf("Example of using a Single Observable") {
            // Singles emit just one result.
            val subscription = CompositeDisposable()
            val observer = loadText(R.raw.a_new_hope)
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

    @Test
    fun exampleOfNeverWithSideEffects() {
        exampleOf("Observer that never emits but has side effects callbacks") {
            val subscription = CompositeDisposable()
            val observable = Observable.never<Any>()
            val observer = observable.doOnSubscribe {
                println("On Subscribe")
            }.doOnDispose {
                println("On Dispose")
            }.subscribeBy(onNext = {
                println("On Next")
            }, onComplete = {
                println("On Complete")
            })

            subscription.add(observer)
            subscription.clear()
        }
    }

    private fun loadText(@RawRes resId: Int): Single<String> {
        return Single.create create@{ emitter ->
            // Check if the raw resource exists.
            val inputStream: InputStream? = try {
                app.applicationContext.resources.openRawResource(resId)
            } catch (exception: Resources.NotFoundException) {
                emitter.onError(FileReadError.FileNotFound())
                null
            }

            // Never null because we throw an exception before getting to this point.
            val text = inputStream!!.reader().use {
                it.readText()
            }

            emitter.onSuccess(text)
        }
    }
}