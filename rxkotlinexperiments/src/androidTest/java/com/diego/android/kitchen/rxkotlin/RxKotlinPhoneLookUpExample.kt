package com.diego.android.kitchen.rxkotlin

import exampleOf
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.PublishSubject
import org.junit.Test

class RxKotlinPhoneLookUpExample {

    /**
     * This example shows how to use different operators to search a map of numbers and emmit
     * the result if it's found.
     */
    @Test
    fun phoneLookUpExample() {
        exampleOf("Challenge 1") {

            val subscriptions = CompositeDisposable()

            val contacts = mapOf(
                "603-555-1212" to "Florent",
                "212-555-1212" to "Junior",
                "408-555-1212" to "Marin",
                "617-555-1212" to "Scott")

            fun phoneNumberFrom(inputs: List<Int>): String {
                val phone = inputs.map { it.toString() }.toMutableList()
                phone.add(3, "-")
                phone.add(7, "-")
                return phone.joinToString("")
            }

            val input = PublishSubject.create<Int>()

            subscriptions.add(
                input
                    .skipWhile { it == 0 }
                    .filter { it < 10 }
                    .take(10)
                    .toList() // Returns a Single
                    .subscribeBy( onSuccess = {
                        val phone = phoneNumberFrom(it)
                        val contact = contacts[phone]
                        if (contact != null) {
                            println("AQUI - Dialing $contact ($phone)...")
                        } else {
                            println("AQUI - Contact not found")
                        }
                    }))

            input.onNext(0)
            input.onNext(603)

            input.onNext(2)
            input.onNext(1)

            // Confirm that 7 results in "Contact not found", and then change to 2 and confirm that Junior is found
            input.onNext(2)

            "5551212".forEach {
                input.onNext(it.toString().toInt()) // Need toString() or else Char conversion is done
            }

            input.onNext(9)
        }
    }
}