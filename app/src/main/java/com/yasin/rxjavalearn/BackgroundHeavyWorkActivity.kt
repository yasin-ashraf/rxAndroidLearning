package com.yasin.rxjavalearn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_background.*

class BackgroundHeavyWorkActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    private val heavyTaskObserver: DisposableObserver<String>
        get() = object : DisposableObserver<String>() {
            override fun onNext(o: String) {

            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {

            }
        }

    private val heavyTaskObservable: Observable<String>
        get() = Observable.just("Haha i did it!")
                .map { s -> s != "" }
                .map { "It's not empty!!!" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background)
        rv_photos.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        //TODO : get the photos from Gallery inside observable and set on rv

        val disposableObserver = heavyTaskObserver
        heavyTaskObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver)

        compositeDisposable.add(disposableObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
