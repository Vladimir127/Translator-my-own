package com.example.translator.presentation.base

interface BasePresenter {
    fun attach(mainActivityView: BaseView)
    fun detach()
    fun getData(word: String)
}