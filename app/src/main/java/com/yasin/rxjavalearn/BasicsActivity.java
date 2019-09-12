package com.yasin.rxjavalearn;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class BasicsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void createObservable() {
        Observable<String> theObservable = Observable.create(
                emitter -> {
                    emitter.onNext("Hey there!");
                    emitter.onComplete();
                }
        );
    }

    private void createSubscriber() {
        Subscriber<String> theSubscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private void createAction() {
        Disposable disposable = Observable.just("Haha DOn't")
                .subscribe(s -> Log.e("tag","msg"));
    }

    private void mapOperator() {
        Disposable disposable = Observable.just("Haha DOn't")
                .map(s -> s + "- Yasin")
                .subscribe(s -> Log.e("output",s));
    }

    private void mapOperatorMore() {
        Disposable disposable = Observable.just("Ha Don't")
                .map(s -> s + "- Yasin")
                .map(s -> s.hashCode())
                .subscribe(i -> Log.e("output",Integer.toString(i)));
    }
}
