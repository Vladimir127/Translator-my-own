package com.example.translator.presentation.base

import com.example.translator.entities.DataModel

interface BaseView {
    fun showData(data: List<DataModel>)
    fun showError(message: String?)
    fun showLoading()
}