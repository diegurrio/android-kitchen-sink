package com.diego.android.kitchen.rxkotlin

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.diego.android.kitchen.rxkotlin.helpers.Droid
import episodeI
import episodeII
import episodeIII
import episodeIV
import episodeIX
import episodeV
import episodeVI
import episodeVII
import episodeVIII
import exampleOf
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.toObservable

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import rogueOne
import solo

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RxKotlinBasicExamples {

    @Test
    // Create an Observable that emits a particular item.
    // See@link: http://reactivex.io/documentation/operators/just.html
    fun createObservable() {
        exampleOf("Creating an observable") {
            // Single String observable.
            val mostPopular: Observable<String> = Observable.just(episodeV)
            // Let the compiler determine the type.
            val originalTrilogy = Observable.just(episodeIV, episodeV, episodeVI) // It will emmit 3 Strings
            // Create an observable of a list.
            val prequelTrilogy = Observable.just(listOf(episodeI, episodeII, episodeIII))
            // Create of a type from a list of that type.
            val sequelTrilogy = Observable.fromIterable(listOf(episodeVII, episodeVIII, episodeIX))
            // Use kotlin extension to create an observable
            val stories = listOf(solo, rogueOne).toObservable()
        }
    }

    @Test
    // Create an Observable and subscribe to it.
    fun subscribeToObservable() {
        exampleOf("Creating an observable") {
            // Let the compiler determine the type.
            val originalTrilogy = Observable.just(episodeIV, episodeV, episodeVI)
            originalTrilogy.subscribe { element ->
                println(element)
            }
        }
    }

    @Test
    // Create an Observable and subscribe to it.
    fun subscribeToObservableWithLambdas() {
        exampleOf("Creating an observable and subscribing to it") {
            // Let the compiler determine the type.
            val originalTrilogy = Observable.just(episodeIV, episodeV, episodeVI)
            originalTrilogy.subscribeBy(
                onNext = { element -> println(element) },
                onComplete = { println("Completed") },
                onError = { error -> println("Error: ${error.localizedMessage}") }
            )
        }
    }

    @Test
    // This is an example of en empty observable that just emits an event.
    fun emptyObservableExample() {
        exampleOf("Example of empty") {
            val mostPopular: Observable<String> = Observable.just(episodeV)
            val subscription = mostPopular.subscribe { element ->
                println(element)
            }

            // Dispose of the connection. Stops observing. This also happens automatically
            // when a complete events is received.
            subscription.dispose()
        }
    }

    @Test
    fun exampleOfNever() {
        exampleOf("Observer that never emits") {
            // Note: Any is the root of all kotlin classes. Similar to java Object class
            val observable = Observable.never<Any>()
            observable.subscribeBy(
                onNext = { println(it) },
                onComplete = { println("Completed") }
            )
        }
    }

    @Test
    fun exampleOfDispose() {
        exampleOf("Example of disposing") {
            // Note: Any is the root of all kotlin classes. Similar to java Object class
            val observable = Observable.never<Any>()
            observable.subscribeBy(
                onNext = { println(it) },
                onComplete = { println("Completed") }
            )
        }
    }

    @Test
    // This is the most common pattern.
    fun exampleOfCompositeDisposable() {
        /**
         * @see <a href="https://stackoverflow.com/questions/46655279/what-is-compositedisposable-in-rxjava">What is a composite disposable?</a>
         */

        // Composite disposable automatically calls dispose when the owner of the subscription
        // is deallocated. This is the most common pattern used.
        exampleOf("Example of composite disposable") {
            val topGrossing = listOf(episodeVII, episodeI, rogueOne)
            val subscriptions = CompositeDisposable()
            subscriptions.add(topGrossing.toObservable().subscribe(){ element ->
                println(element)
            })
        }
    }

    @Test
    fun exampleOfCreateOperator() {
        exampleOf("This is an example of the create Operator") {
            val subscription = CompositeDisposable()
            val droids = Observable.create<String> { emitter->
                // Here we can create a new observable of type String. The observable will emit
                // three Strings. Very much like this example @link #createObservable() createObservable function.
                emitter.onNext("R2-D2")
                emitter.onNext("C-3PO")
                emitter.onNext("K-2SO")
            }

            val observer = droids.subscribeBy (
                onNext = { println(it)},
                onError = { println("Error, $it")},
                onComplete = { println("Completed")}
            )

            subscription.add(observer)
        }
    }

    @Test
    fun exampleOfCreateOperatorWithError() {
        exampleOf("This is an example of the create Operator") {
            val subscription = CompositeDisposable()
            val droids = Observable.create<String> { emitter->
                // Here we emmit a throwable which will trigger the error call and terminate
                // the subscription. CompositeDisposable will handle clean up automatically.
                emitter.onNext("R2-D2")
                emitter.onNext("C-3PO")
                emitter.onError(Droid.OU812()) // Oops we found an error.
                emitter.onNext("K-2SO")
            }

            val observer = droids.subscribeBy (
                onNext = { println(it)},
                onError = { println("Error, $it")},
                onComplete = { println("Completed")}
            )

            subscription.add(observer)
        }
    }
}