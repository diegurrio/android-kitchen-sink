package com.diego.android.kitchen.rxkotlin

import androidx.test.ext.junit.runners.AndroidJUnit4
import doOrDoNot
import exampleOf
import eyesCanDeceive
import iAmYourFather
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject
import itsNotMyFault
import lackOfFaith
import mayThe4thBeWithYou
import mayTheForceBeWithYou
import org.junit.Test
import org.junit.runner.RunWith
import printWithLabel
import stayOnTarget
import theForceIsStrong
import useTheForce

@RunWith(AndroidJUnit4::class)
class RxKotlinSubjects {

    /**
     * Publish subjects will send a result to a subscriber after the subscription is made
     * and a new event is sent.
     */
    @Test
    fun exampleOfPublishSubject() {
        exampleOf(" Publish subject example") {
            // Publish subjects start out empty. That's why we need to specify the type.
            val quotes = PublishSubject.create<String>()

            // We put a request but there are no subscribers yet.
            quotes.onNext(itsNotMyFault)

            // Subscribe. The next event will be now received.
            val subscriptionOne = quotes.subscribeBy(
                onNext = { printWithLabel("1)", "Complete") },
                onComplete = { printWithLabel("1)", "onComplete") }
            )

            // subscriptionOne will receive this event.
            quotes.onNext(doOrDoNot)

            val subscriptionTwo = quotes.subscribeBy(
                onNext = { printWithLabel("2)", "Complete") },
                onComplete = { printWithLabel("2)", "onComplete") }
            )

            // subscriptionOne and subscriptionTwo will receive this.
            quotes.onNext(lackOfFaith)

            // Stop listening
            subscriptionOne.dispose()

            // Because subscriptionOne.dispose() was called only subscriptionTwo will receive this
            quotes.onNext(eyesCanDeceive)

            // We're done no more quotes.
            quotes.onComplete()

            val subscriptionTree = quotes.subscribeBy(
                onNext = { printWithLabel("3)", "Complete") },
                onComplete = { printWithLabel("3)", "onComplete") }
            )

            // Because we called onNext() after saying that this subject was done. subscriptionThree
            // will only get the onComplete() event. quotes.next(stayOnTarget) will not get restarted.
            quotes.onNext(stayOnTarget)

            // Clean up. If you don't dispose they will leak.
            subscriptionTwo.dispose()
            subscriptionTree.dispose()
        }
    }

    /**
     * With a behavior subject always pushes the latest event even if the subscriber starts
     * listening after the event was originally sent.
     */
    @Test
    fun exampleOfBehaviorSubject() {
        exampleOf("Example of behavior subject") {
            val subscriptions = CompositeDisposable()

            // The type of the subject is inferred by the initial value provided.
            val quotes = BehaviorSubject.createDefault(iAmYourFather)

            val subscriptionOne = quotes.subscribeBy(
                onNext = { printWithLabel("1)", it) },
                onError = { printWithLabel("1)", it) },
                onComplete = { printWithLabel("1)", "onComplete") }
            )

            // Send an error. subscriptionOne will get it
            quotes.onError(Quote.NeverSaidThat())

            // Add a new subscription. It will get the error, not the default value
            subscriptions.add(quotes.subscribeBy(
                onNext = { printWithLabel("2)", it) },
                onError = { printWithLabel("2)", it) },
                onComplete = { printWithLabel("2)", "onComplete") }
            ))

            // The second subscription will be cleaned up because we used a CompositeDisposable()
            subscriptionOne.dispose()
        }
    }

    /**
     * Behavior subject stores its value as a state.
     */
    @Test
    fun exampleOfBehaviorSubjectState() {
        exampleOf("Example of behavior subject storing its state") {
            val subscriptions = CompositeDisposable()
            val quotes = BehaviorSubject.createDefault(mayTheForceBeWithYou)

            subscriptions.add(quotes.subscribeBy {
                printWithLabel("1)", it)
            })

            // Subscription one will get the onNext
            quotes.onNext(mayThe4thBeWithYou)

            // Print the current value.
            printWithLabel("State: ", quotes.value)

            // Clean up done by CompositeDisposable
        }
    }

    /**
     * Replay subjects can store multiple events.
     */
    @Test
    fun exmapleOfReplaySubjects() {
        exampleOf("Example of Replay subjects") {
            val subscriptions = CompositeDisposable()

            // Create a ReplaySubject that can hold two events.
            val subject = ReplaySubject.createWithSize<String>(2)

            // Send an event
            subject.onNext(useTheForce)

            // Create a subscriber. It will get the event even though it subscribed after it was sent.
            subscriptions.add(subject.subscribeBy(
                onNext = { printWithLabel("1)", it) },
                onError = { printWithLabel("1)", it) },
                onComplete = { printWithLabel("1)", "onError") }
            ))

            // 1) Will get this event also. It subscribed before it was sent.
            subject.onNext(theForceIsStrong)

            // This subscriber will get both events replayed
            subscriptions.add(subject.subscribeBy(
                onNext = { printWithLabel("2)", it) },
                onError = { printWithLabel("2)", it) },
                onComplete = { printWithLabel("2)", "onError") }
            ))
        }
    }
}