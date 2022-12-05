package com.example.translator.data

import com.example.translator.entities.DataModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("words/search")
    fun searchObservable(@Query("search") wordToSearch: String): Observable<List<DataModel>>
}
