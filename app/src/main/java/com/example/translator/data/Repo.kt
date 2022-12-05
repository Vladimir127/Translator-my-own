package com.example.translator.data

import com.example.translator.entities.DataModel
import io.reactivex.Observable

interface Repo {
    fun getData(word: String) : Observable<List<DataModel>>
}