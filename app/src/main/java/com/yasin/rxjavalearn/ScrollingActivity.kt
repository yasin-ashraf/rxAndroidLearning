package com.yasin.rxjavalearn

import android.content.Intent
import android.os.Bundle

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yasin.rxjavalearn.backgroundHeavyWorkWithRx.BackgroundHeavyWorkActivity
import com.yasin.rxjavalearn.nestedFlatMap.NestedFlatMapActivity
import com.yasin.rxjavalearn.networkCallWithRx.NetworkingActivity

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.content_scrolling.*

class ScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {  }
        background_work.setOnClickListener { startActivity(Intent(this, BackgroundHeavyWorkActivity::class.java))}
        network.setOnClickListener { startActivity(Intent(this, NetworkingActivity::class.java))}
        nested_network.setOnClickListener { startActivity(Intent(this, NestedFlatMapActivity::class.java))}
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            startActivity(Intent(this, NestedFlatMapActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun basic() {

        val disposable = Observable.just("First Item", "Second Item")
                .subscribe()
        disposable.dispose()

        Observable.just("First Item", "Second Item")
                .subscribeOn(Schedulers.io())
                .doOnNext { s -> Log.e("APP", "on-next:" + Thread.currentThread().name + ":" + s) }
                .subscribe()
    }


}
