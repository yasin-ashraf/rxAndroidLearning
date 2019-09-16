package com.yasin.rxjavalearn.BackgroundHeavyWorkWithRx

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yasin.rxjavalearn.PhotosAdapter
import com.yasin.rxjavalearn.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_background.*

class BackgroundHeavyWorkActivity : AppCompatActivity() {

    private lateinit var photosAdapter: PhotosAdapter
    private val compositeDisposable = CompositeDisposable()
    private val REQUEST_PERMISSIONS = 100

    private val heavyTaskObserver: DisposableObserver<String>
        get() = object : DisposableObserver<String>() {
            override fun onComplete() {
                Log.e("OnComplete","Yea Completed")
            }

            override fun onNext(t: String) {
                photosAdapter.addPhotos(t)
                Log.e("OnNext", t)
            }

            override fun onError(e: Throwable) {
                Log.e("onError",e.toString())
            }

        }
    private val heavyTaskObservable: Observable<String>
        get() = Observable.just(getAllImages())
                .flatMap { t -> Observable.fromIterable(t) }
                .map { uri -> "file://$uri" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background)
        rv_photos.setHasFixedSize(true)
        rv_photos.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL)
        val listOfUri = mutableListOf<String>()
        photosAdapter = PhotosAdapter(listOfUri)
        rv_photos.adapter = photosAdapter
        if(checkStoragePermission()){
            subscribeTask()
        }
    }

    private fun checkStoragePermission() : Boolean {
        return if (ContextCompat.checkSelfPermission(applicationContext, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)) {
                false
            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(READ_EXTERNAL_STORAGE),
                        REQUEST_PERMISSIONS)
                false
            }
        } else {
            Log.d("Else", "Has Read Permission")
            true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_PERMISSIONS && resultCode == Activity.RESULT_OK) {
            subscribeTask()
        }
    }

    private fun subscribeTask() {
        val disposableObserver = heavyTaskObserver
        heavyTaskObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver)

        compositeDisposable.add(disposableObserver)
    }

    private fun getAllImages(): List<String> {
        val uri : Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val listOfAllImages : MutableList<String> = mutableListOf()
        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        val cursor : Cursor? = this.contentResolver.query(uri,projection,null,null,null)
        val columnIndexData: Int?
        var absolutePathOfImage : String?
        columnIndexData = cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        while (cursor!!.moveToNext()) {
            absolutePathOfImage = cursor.getString(columnIndexData!!)
            listOfAllImages.add(absolutePathOfImage)
        }
        Log.d("Getting iMAGES","Yep")
        cursor.close()
        return listOfAllImages
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
