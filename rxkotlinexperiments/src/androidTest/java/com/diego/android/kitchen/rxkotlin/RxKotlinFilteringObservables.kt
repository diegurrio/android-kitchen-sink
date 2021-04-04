package com.diego.android.kitchen.rxkotlin

import com.diego.android.kitchen.rxkotlin.helpers.Droids
import com.diego.android.kitchen.rxkotlin.helpers.Droids.C3PO
import com.diego.android.kitchen.rxkotlin.helpers.Droids.R2D2
import detours
import episodeI
import episodeII
import episodeIII
import episodeIV
import exampleOf
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.PublishSubject
import landOfDroids
import liveLongAndProsper
import mayThe4thBeWithYou
import mayTheOdds
import org.junit.Test
import tomatometerRatingsMovies
import wookieWorld

/**
 * Filtering operators apply conditional constrains to next events to only pass-through to
 * subscribers the elements you want
 */
class RxKotlinFilteringObservables {

    /**
     * On this example we ignore all elements emitted on purpose but we keep track of
     * when the observable is done emitting all events.
     */
    @Test
    fun exmapleOfIgnoreElements() {
        exampleOf("This is an example of completed elements") {
            val subscriptions = CompositeDisposable()
            val cannedProjects = PublishSubject.create<String>() // This is the observable

            subscriptions.add(
                cannedProjects
                    .ignoreElements()
                    .subscribeBy {
                        // subscribeBy is used to subscribe to this observable
                        println("Completed")
                    })
            cannedProjects.onNext(landOfDroids)
            cannedProjects.onNext(wookieWorld)
            cannedProjects.onNext(detours)

            cannedProjects.onComplete() // Our subscription only gets this event.
        }
    }

    /**
     * This is an example of element at; which only takes the element emitted at a particular
     * index.
     */
    @Test
    fun exampleOfElementAt() {
        exampleOf("This is an example that uses element at") {
            val subscriptions = CompositeDisposable()
            val quotes = PublishSubject.create<String>()

            subscriptions.add(quotes.elementAt(2).subscribeBy(
                onSuccess = { println("it") },
                onComplete = { println("Completed") }
            ))

            quotes.onNext(mayTheOdds)
            quotes.onNext(liveLongAndProsper)
            quotes.onNext(mayThe4thBeWithYou)
        }
    }

    /**
     * This is an example of filter.
     * index.
     */
    @Test
    fun exampleOfFilterBy() {
        exampleOf("This is an example of the filter option") {
            val subscriptions = CompositeDisposable()
            val movieObservable = Observable.fromIterable(tomatometerRatingsMovies)

            subscriptions.add(movieObservable.filter { moview ->
                moview.rating >= 90
            }.subscribe {
                println(it)
            })
        }
    }

    /**
     * Example of how to use skip and skip until.
     */
    @Test
    fun exampleOfSkipWhileAndSkipUntil() {
        exampleOf("Example of skip while") {
            val subscriptions = CompositeDisposable()
            subscriptions.add(
                Observable.fromIterable(tomatometerRatingsMovies)
                    .skipWhile { movie ->
                        movie.rating < 90
                    }.subscribe {
                        // This subscribes this function as an observer
                        println(it)
                    })

            subscriptions.clear()
        }

        /**
         * Skip until will not emmit any results until a trigger is received.
         */
        exampleOf("Example of skip until") {
            val subscriptions = CompositeDisposable()

            val subject = PublishSubject.create<String>()
            val trigger = PublishSubject.create<Unit>()

            subscriptions.add(subject
                .skipUntil(trigger)
                .subscribe {
                    println(it)
                })

            subject.onNext(episodeI)
            subject.onNext(episodeII)
            subject.onNext(episodeIII)

            trigger.onNext(Unit)

            // This will be the only value emitted because we were waiting for the
            // the trigger to be emitted.
            subject.onNext(episodeIV)

            subscriptions.clear()
        }
    }

    /**
     * Example of how to use take while and take until operators
     */
    @Test
    fun exampleOfTakeWhileAndTakeUntil() {
        exampleOf("Example of take while") {
            val subscriptions = CompositeDisposable()
            subscriptions.add(
                Observable.fromIterable(tomatometerRatingsMovies)
                    .takeWhile { movie ->
                        movie.rating >= 90
                    }.subscribe {
                        // This subscribes this function as an observer
                        println(it)
                    })

            subscriptions.clear()
        }

        /**
         * Skip until will not emmit any results until a trigger is received.
         */
        exampleOf("Example of skip until") {
            val subscriptions = CompositeDisposable()

            val subject = PublishSubject.create<String>()
            val trigger = PublishSubject.create<Unit>()

            subscriptions.add(subject
                .takeUntil(trigger)
                .subscribe {
                    println(it)
                })

            // This will be the only value emitted because are taking values until we
            // fire the trigger.
            subject.onNext(episodeIV)

            trigger.onNext(Unit)

            subject.onNext(episodeIII)
            subject.onNext(episodeII)
            subject.onNext(episodeI)

            subscriptions.clear()
        }
    }

    /**
     * Example of using distinct Until changed.
     */
    @Test
    fun exampleOfDistinctUntilChanged(){
        exampleOf("Example of distinct until changed") {
            // In this example the second C3PO will not be emitted because it's not distinc from
            // the first C3PO emitted.
            val subscriptions = CompositeDisposable()
            subscriptions
                .add(Observable.just(R2D2, C3PO, C3PO, R2D2)
                    .distinctUntilChanged()
                    .subscribe {
                println(it)
            })
        }
    }
}