package com.surefor.rxjava.reference;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
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


    /**
     * Java 7, crate an observable to omit 0 to end.
     */
    public static Observable<Integer> getObservable_7(int end) {
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

    /**
     * Java 8, crate an observable to omit 0 to end
     */
    public static Observable<Integer> getObservable_8(int end) {
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

    /**
     * Java 7, create a observable with create() method and subscribe it
     */
    public static void subscribe_7(int end) {
        Observable<Integer> observerable = getObservable_7(end) ;

        Subscription subscribe = observerable.subscribe(new Action1<Integer>() { // for onNext
            public void call(Integer i) {
                System.out.printf("Java 7 %d\n", i);
            }
        }, new Action1<Throwable>() { // for onError
            @Override
            public void call(Throwable throwable) {
            }
        }, new Action0() { // for onComplete
            @Override
            public void call() {

            }
        });
    }

    /**
     * Java 8, create a observable with create() method and subscribe it
     */
    public static void subscribe_8(int end) {
        Observable<Integer> observerable = getObservable_8(end) ;
        // observerable.subscribe(i -> System.out.printf("Java 8 %d\n", i), throwable -> System.out.println(throwable.getMessage()), () -> System.out.println("complete")) ;
        Subscription subscription = observerable.subscribe(
                (i) -> {
                    System.out.printf("Java 8 %d\n", i);
                },
                (throwable) -> {
                    System.out.println(throwable.getMessage());
                },
                () -> {
                    System.out.println("complete");
                }) ;
    }

    /**
     * Java 8, create a observable with create() method and subscribe it on a new thread
     */
    public static void subscribeAsync8_1() {
        Observable<Integer> observerable = getObservable_8(10000) ;
        observerable.subscribeOn(Schedulers.newThread()).subscribe(i -> System.out.printf("subscribeAsync1 - %d\n", i)) ;
    }

    /**
     * Java 8, create a observable with create() method and subscribe it on a new thread
     */
    public static void subscribeAsync8_2() {
        Observable<Integer> observerable = getObservable_8(10000) ;
        observerable.subscribeOn(Schedulers.newThread()).subscribe(i -> System.out.printf("subscribeAsync2 - %d\n", i)) ;
    }

    /**
     * Here, we want to invoke a subscriber action in both, synchronous way and asynchronous way.
     */
    public static void main(String[] args) throws InterruptedException {
        // subscribe a observable created with create() method
        CreateObserableWithCreate.subscribe_7(10) ;
        CreateObserableWithCreate.subscribe_8(10) ;

        // complete two methods in subscribing
        CreateObserableWithCreate.subscribeAsync8_1() ;
        CreateObserableWithCreate.subscribeAsync8_2() ;

        while(true) {
            Thread.sleep(10);
        }
    }
}
