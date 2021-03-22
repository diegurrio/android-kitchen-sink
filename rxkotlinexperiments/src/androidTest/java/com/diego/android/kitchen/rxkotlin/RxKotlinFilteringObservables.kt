package com.diego.android.kitchen.rxkotlin

import detours
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
import tomatometerRatings
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
        exampleOf("This is an example that uses element at"){
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
            val movieObservable = Observable.fromIterable(tomatometerRatings)

            subscriptions.add(movieObservable.filter { moview ->
                moview.rating >= 90
            }.subscribe {
                println(it)
            })
        }
    }
}