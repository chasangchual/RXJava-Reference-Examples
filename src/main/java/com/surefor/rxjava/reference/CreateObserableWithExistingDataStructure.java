package com.surefor.rxjava.reference;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Basic way to create an observable with Observable.from() & Observable.just()
 *
 * These converted Observables will synchronously invoke the onNext( ) method of any subscriber that subscribes to
 * them, for each item to be emitted by the Observable, and will then invoke the subscriber’s onCompleted( ) method.
 *
 * Created by chae on 2/11/2016.
 */
public class CreateObserableWithExistingDataStructure {
    /**
     * crate a Integer array and subscribe the array to cover onNext() in synchronous manner
     */
    public static void withFromJava7OnlyOnNext() {
        Integer[] list = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0} ;
        Observable<Integer> observable = Observable.from(list) ;

        Subscription s = observable.subscribe(new Action1<Integer>() {
            public void call(Integer i) {
                System.out.println(String.valueOf(String.valueOf(i))) ;
            }
        }) ;
    }

    /**
     * crate a Integer array and subscribe the array to cover onNext(), onError() and onComplete() in synchronous manner
     */
    public static void withFromJava7Full() {
        Integer[] list = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0} ;
        Observable<Integer> observable = Observable.from(list) ;

        Subscription s = observable.subscribe( new Action1<Integer>() { // for onNext()
            public void call(Integer i) {
                System.out.println(String.valueOf(String.valueOf(i))) ;
            }
        }, new Action1<Throwable>() { // for onError
            @Override
            public void call(Throwable throwable) {
                System.out.println(throwable.getMessage()) ;
            }
        }, new Action0() { // for onComplete
            @Override
            public void call() {
                System.out.println("complete of withFromJava7OnlyFull") ;
            }
        }) ;
    }

    /**
     * crate a Integer array and subscribe the array to cover onNext() in synchronous manner
     */
    public static void withFromJava8OnlyOnNext() {
        Integer[] list = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        Observable<Integer> observable = Observable.from(list);

        System.out.println("Before subscribe()) : " + String.valueOf(System.currentTimeMillis()));
        Subscription s = observable.subscribe(i -> System.out.println(String.valueOf(i))) ;
    }

    /**
     * crate a Integer array and subscribe the array to cover onNext(), onError() and onComplete() in synchronous manner
     */
    public static void withFromJava8OFull() {
        // crate an obserable with Integer array
        Integer[] list = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        Observable<Integer> observable = Observable.from(list);

        Subscription s = observable.subscribe(
                (i) -> System.out.println(String.valueOf(i)),
                (throwable) -> System.out.println(throwable.getMessage()),
                ()-> System.out.println("complete of withFromJava8OnlyOnNext")) ;
    }

    /**
     * crate a Integer array and subscribe the array to cover onNext() in synchronous manner
     */
    public static void withJust() {
        Observable<Integer> observable = Observable.just(9999) ;

        Subscription s = observable.subscribe(new Action1<Integer>() {
            public void call(Integer i) {
                System.out.println(String.valueOf(i)) ;
            }
        }) ;
    }

    public static void main(String[] args) {
        CreateObserableWithExistingDataStructure.withFromJava7Full();
        CreateObserableWithExistingDataStructure.withFromJava8OFull();
        CreateObserableWithExistingDataStructure.withJust();
    }
}
