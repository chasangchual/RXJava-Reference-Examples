package com.surefor.rxjava.reference;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

import java.util.stream.IntStream;

/**
 * RxJava examples for first() filtering
 * http://reactivex.io/documentation/operators/first.html
 *
 * Created by chae on 4/1/2016.
 */
public class FilteringFirst {
    public static Observable<Integer> getObservable_7() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for(int i = 0 ; i < 10; i ++) {
                    subscriber.onNext(i);
                }

                if(!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        }) ;
    }

    public static Observable<Integer> getObservable_8() {
        return Observable.create(subscriber -> {
            IntStream.range(0, 10).forEach(i -> subscriber.onNext(i));
        }) ;
    }

    public static void main(String[] args) {
        FilteringFirst.getObservable_7().first().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                System.out.println(i) ;
            }
        }) ;

        FilteringFirst.getObservable_8().first().subscribe(v -> {System.out.println(v) ;}) ;
    }
}
