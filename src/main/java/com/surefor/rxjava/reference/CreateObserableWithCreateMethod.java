package com.surefor.rxjava.reference;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by chae on 2/11/2016.
 */
public class CreateObserableWithCreateMethod {
    public static Observable<Integer> getBlockingObservable() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
                subscriber.onNext(4);
                subscriber.onCompleted();
            }
        }) ;
    }

    public static Observable<Integer> getNonBlockingObservable() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
                subscriber.onNext(4);
                subscriber.onCompleted();
            }
        }) ;
    }

    public static void main(String[] args) {
        Observable<Integer> blocking = CreateObserableWithCreateMethod.getBlockingObservable() ;
        blocking.subscribe(new Action1<Integer>() {
            public void call(Integer i) {
                System.out.println(String.valueOf(i)) ;
            }
        }) ;
    }
}
