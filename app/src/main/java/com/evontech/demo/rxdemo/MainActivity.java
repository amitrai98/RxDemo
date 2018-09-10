package com.evontech.demo.rxdemo;

import android.arch.core.util.Function;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private Button button = null;
    private TextView textView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printHello();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void printHello(){
        textView.setText("");
        Observable.just(textView.getText().toString())
                .map(new io.reactivex.functions.Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return s;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        textView.append("\n on subscribed");
                        Log.e(TAG, "on subscribed was called");
                    }

                    @Override
                    public void onNext(String s) {
                        textView.append("\n on next");
                        Log.e(TAG, "on next was called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.append("\n on error");
                        Log.e(TAG, "on error was called");
                    }

                    @Override
                    public void onComplete() {
                        textView.append("\n on complete");
                        Log.e(TAG, "on complete was called");
                    }
                });

    }
}
