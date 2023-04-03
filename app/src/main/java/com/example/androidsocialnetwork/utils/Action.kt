package com.example.androidsocialnetwork.utils

open class Action {
    private var isFirstRun = true
    fun invoke(func: () -> Unit) {
        if (isFirstRun) {
            isFirstRun = false
            func()
        }
    }
}