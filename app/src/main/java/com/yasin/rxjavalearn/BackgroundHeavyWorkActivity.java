package com.yasin.rxjavalearn;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class BackgroundHeavyWorkActivity extends AppCompatActivity {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background);
        //TODO : get the photos from Gallery and set on rv

        DisposableObserver<String> disposableObserver = getHeavyTaskObserver();
        getHeavyTaskObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);

        compositeDisposable.add(disposableObserver);
    }

    private DisposableObserver<String> getHeavyTaskObserver() {
        return new DisposableObserver<String>() {
            @Override
            public void onNext(String o) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private Observable<String> getHeavyTaskObservable() {
        return Observable.just("Haha i did it!")
                .map(s -> !s.equals(""))
                .map(aBoolean -> "It's not empty!!!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
