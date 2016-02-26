package com.surefor.rxjava.reference;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 /**
 * General ways to create an observable object with Observable.from()
 *
 * Once an observable object is created, RxJava will synchronously invoke a method corresponding to onNext()
 * or three methods corresponding to onNext(), onError() and onComplete().
 *
 * And we get Subscriber run in asynchronous way with subscribeOn(Scheduler) method.
 *
 * Created by chae on 2/11/2016.
 */
public class CreateObserableWithCreate {

    public static Observable<Integer> getObservable7() {
        return getObservable7(5) ;
    }

    public static Observable<Integer> getObservable7(int end) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            public void call(Subscriber<? super Integer> subscriber) {
                for(int i = 0 ; i < end ; i ++) {
                    if(!subscriber.isUnsubscribed()) {
                        subscriber.onNext(i);
                    }
                }
                if(!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        }) ;
    }

    public static Observable<Integer> getObservable8() {
        return getObservable8(5) ;
    }

    public static Observable<Integer> getObservable8(int end) {
        return Observable.create(subscriber -> {
                for(int i = 0 ; i < end ; i ++) {
                    if(!subscriber.isUnsubscribed()) {
                        subscriber.onNext(i);
                    }
                }
                if(!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
        }) ;
    }

    public static void subscribe7() {
        subscribe7(5) ;
    }

    public static void subscribe7(int end) {
        Observable<Integer> observerable = getObservable7(end) ;
        observerable.subscribe(new Action1<Integer>() { // for onNext()
            public void call(Integer i) {
                System.out.printf("Java 7 %d\n", i) ;
            }
        }) ;
    }

    public static void subscribe8() {
        subscribe8(5) ;
    }

    public static void subscribe8(int end) {
        Observable<Integer> observerable = getObservable7() ;
        observerable.subscribe(i -> System.out.printf("Java 8 %d\n", i)) ;
    }

    public static void subscribeAsync8_1() {
        Observable<Integer> observerable = getObservable8(10000) ;
        observerable.subscribeOn(Schedulers.newThread()).subscribe(i -> System.out.printf("subscribeAsync1 - %d\n", i)) ;
    }

    public static void subscribeAsync8_2() {
        Observable<Integer> observerable = getObservable8(10000) ;
        observerable.subscribeOn(Schedulers.newThread()).subscribe(i -> System.out.printf("subscribeAsync2 - %d\n", i)) ;
    }

    /**
     * Here, we want to invoke a subscriber action in both, synchronous way and asynchronous way.
     */
    public static void main(String[] args) throws InterruptedException {
        CreateObserableWithCreate.subscribe7() ;
        CreateObserableWithCreate.subscribe8() ;
        CreateObserableWithCreate.subscribeAsync8_1() ;
        CreateObserableWithCreate.subscribeAsync8_2() ;

        while(true) {
            Thread.sleep(10);
        }
    }
}
