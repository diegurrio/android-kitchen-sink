package com.diego.android.kitchen.rxkotlin

import androidx.test.ext.junit.runners.AndroidJUnit4
import doOrDoNot
import exampleOf
import eyesCanDeceive
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.PublishSubject
import itsNotMyFault
import lackOfFaith
import org.junit.Test
import org.junit.runner.RunWith
import printWithLabel
import stayOnTarget

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
                onNext = {printWithLabel("1)", "Complete")},
                onComplete = {printWithLabel("1)", "onComplete")}
            )

            // subscriptionOne will receive this event.
            quotes.onNext(doOrDoNot)

            val subscriptionTwo = quotes.subscribeBy(
                onNext = {printWithLabel("2)", "Complete")},
                onComplete = {printWithLabel("2)", "onComplete")}
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
                onNext = {printWithLabel("3)", "Complete")},
                onComplete = {printWithLabel("3)", "onComplete")}
            )

            // Because we called onNext() after saying that this subject was done. subscriptionThree
            // will only get the onComplete() event. quotes.next(stayOnTarget) will not get restarted.
            quotes.onNext(stayOnTarget)

            // Clean up. If you don't dispose they will leak.
            subscriptionTwo.dispose()
            subscriptionTree.dispose()
        }
    }

    @Test
    fun exampleOfBehaviorSubject() {
        exampleOf("Example of behavior subject") {
            
        }
    }
}