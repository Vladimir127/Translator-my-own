package com.example.translator.presentation.main

import com.example.translator.data.Repo
import com.example.translator.data.rx.SchedulerProvider
import com.example.translator.entities.DataModel
import com.example.translator.presentation.base.BasePresenter
import com.example.translator.presentation.base.BaseView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class MainActivityPresenterImpl(private val repo: Repo) :
    BasePresenter {
    private var mainActivityView: BaseView? = null

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val schedulerProvider: SchedulerProvider = SchedulerProvider()

    override fun attach(mainActivityView: BaseView) {
        this.mainActivityView = mainActivityView
    }

    override fun detach() {
        compositeDisposable.clear()
        mainActivityView = null
    }

    override fun getData(word: String) {
        compositeDisposable.add(
            repo.getData(word)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe{ mainActivityView?.showLoading() }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<List<DataModel>> {
        return object : DisposableObserver<List<DataModel>>() {

            override fun onNext(dataModel: List<DataModel>) {
                mainActivityView?.showData(dataModel)
            }

            override fun onError(e: Throwable) {
                mainActivityView?.showError(e.message)
            }

            override fun onComplete() {
            }
        }
    }
}