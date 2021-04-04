package com.diego.android.kitchen.rxkotlin.helpers

sealed class FileReadError : Throwable() {
    class FileNotFound : FileReadError()
}