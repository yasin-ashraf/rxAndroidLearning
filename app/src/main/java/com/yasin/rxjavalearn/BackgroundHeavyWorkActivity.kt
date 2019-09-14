package com.yasin.rxjavalearn

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
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
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_background.*

class BackgroundHeavyWorkActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    private val REQUEST_PERMISSIONS = 100

    private val heavyTaskObserver: DisposableObserver<List<String>>
        get() = object : DisposableObserver<List<String>>() {
            override fun onComplete() {
                Log.e("ONCOmplete","Yea Completed")
            }

            override fun onNext(t: List<String>) {
                val photosAdapter = PhotosAdapter(t)
                rv_photos.adapter = photosAdapter
                photosAdapter.notifyDataSetChanged()
                Log.e("OnNext",t.toString())
            }

            override fun onError(e: Throwable) {
                Log.e("onError",e.toString())
            }

        }
    private val heavyTaskObservable: Observable<List<String>>
        get() = Observable.just(getAllImages())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background)
        rv_photos.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        if(checkStoragePermission()){
            subscribeTask()
        }
    }

    private fun checkStoragePermission() : Boolean {
        if (ContextCompat.checkSelfPermission(applicationContext, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            WRITE_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)) {
                return false
            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(READ_EXTERNAL_STORAGE),
                        REQUEST_PERMISSIONS)
                return false
            }
        } else {
            Log.d("Else", "Has Read Permission")
            return true
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
