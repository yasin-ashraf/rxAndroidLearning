package com.yasin.rxjavalearn.nestedFlatMap

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

class NestedFlatMapActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var postsAdapter: UserPostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)
        rv_photos_network.layoutManager = LinearLayoutManager(this)
        val listOfUri = mutableListOf<Post>()
        postsAdapter = UserPostsAdapter(listOfUri)
        rv_photos_network.adapter = postsAdapter
        getPostsOfUser()
    }

    private fun getPostsOfUser() {
        val disposableObserver = postsObserver
        ApiUtils.getServices().users
                .map { users -> users[0] }
                .flatMap { user -> getPosts(user.id) }
                .flatMap { posts -> Observable.fromIterable(posts) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver)

        compositeDisposable.add(disposableObserver)
    }

    private fun getPosts(userId: Int) : Observable<List<Post>> {
        return ApiUtils.getServices().getPosts(userId)
    }

   private val postsObserver : DisposableObserver<Post>
    get() = object : DisposableObserver<Post>() {
        override fun onNext(t: Post) {
            Log.d("NestedNetwork", "Post : ${t.title}")
            postsAdapter.addPost(t)
        }

        override fun onError(e: Throwable) {
            Log.e("NestedNetwork", e.toString())
        }

        override fun onComplete() {
            Log.d("NestedNetwork", "In OnComplete")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}