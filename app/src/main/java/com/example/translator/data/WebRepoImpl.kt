package com.example.translator.data

import com.example.translator.entities.DataModel
import io.reactivex.Observable
import retrofit2.Retrofit

class WebRepoImpl(private val retrofit: Retrofit) : Repo {
    private val service: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override fun getData(word: String): Observable<List<DataModel>> {
        return service.searchObservable(word)
    }
}