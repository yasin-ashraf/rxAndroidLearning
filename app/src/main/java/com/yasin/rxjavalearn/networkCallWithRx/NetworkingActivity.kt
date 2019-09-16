package com.yasin.rxjavalearn.networkCallWithRx

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yasin.rxjavalearn.network.ApiUtils
import com.yasin.rxjavalearn.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_network.*

class NetworkingActivity : AppCompatActivity() {

    private lateinit var usersAdapter: UsersAdapter
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)
        rv_photos_network.layoutManager = LinearLayoutManager(this)
        val listOfUri = mutableListOf<User>()
        usersAdapter = UsersAdapter(listOfUri)
        rv_photos_network.adapter = usersAdapter
        getPhotos()
    }

    private fun getPhotos() {
        val disposableObserver = getUserObserver
        ApiUtils.getServices().users
                .flatMap { t -> Observable.fromIterable(t) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver)
        compositeDisposable.add(disposableObserver)
    }

    private val getUserObserver : DisposableObserver<User>
    get() = object : DisposableObserver<User>() {
        override fun onComplete() {
            Log.d("Observer", "OnComplete : Completed")
        }

        override fun onNext(t: User) {
            Log.d("Network Observer","OnNext : ${t.name}")
            usersAdapter.addUser(t)
        }

        override fun onError(e: Throwable) {
            Log.e("NetworkWithRx", e.toString())
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}