package com.surefor.rxjava.reference;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * General ways to create an observable object with Observable.just()
 *
 * Once an observable object is created, RxJava will synchronously invoke a method corresponding to onNext()
 * or three methods corresponding to onNext(), onError() and onComplete().
 *
 * Created by chae on 2/24/2016.
 */
public class CreateObserableWithJust {
    /**
     * Java 7, crate an observable object with Integer list. Then subscribe it covering onNext()
     */
    public static void withJustOnNext7() {
        Observable<Integer> observable = Observable.just(1, 2) ;

        Subscription s = observable.subscribe(new Action1<Integer>() { // for onNext()
            public void call(Integer i) {
                System.out.printf("%d\n", i) ;
            }
        }) ;
    }

    /**
     * Java 7, crate an observable object with Integer list. Then subscribe it covering onNext(), onError() and onComplete
     */
    public static void withFromOnNextOnErrorOnComplete7() {
        Observable<Integer> observable = Observable.just(1, 2) ;

        Subscription s = observable.subscribe( new Action1<Integer>() { // for onNext()
            public void call(Integer i) {
                System.out.printf("%d\n", i) ;
            }
        }, new Action1<Throwable>() { // for onError
            @Override
            public void call(Throwable throwable) {
                System.out.println(throwable.getMessage()) ;
            }
        }, new Action0() { // for onComplete
            @Override
            public void call() {
                System.out.println("complete of withFromOnNextOnErrorOnComplete7") ;
            }
        }) ;
    }

    /**
     * Java 8, crate an observable object with Integer list. Then subscribe it covering onNext()
     */
    public static void withJustOnNext8() {
        Observable<Integer> observable = Observable.just(1, 2) ;

        Subscription s = observable.subscribe(i -> System.out.printf("%d\n", i)) ;
    }

    /**
     * Java 8, crate an observable object with Integer list. Then subscribe it covering onNext(), onError() and onComplete
     */
    public static void withJustOnNextOnErrorOnComplete8() {
        Observable<Integer> observable = Observable.just(1, 2) ;

        Subscription s = observable.subscribe(
                i -> System.out.printf("%d\n", i),
                throwable -> System.out.println(throwable.getMessage()),
                ()-> System.out.println("complete of withJustOnNextOnErrorOnComplete8()")) ;
    }

    public static void main(String[] args) {
        CreateObserableWithJust.withJustOnNext7();
        CreateObserableWithJust.withFromOnNextOnErrorOnComplete7();
        CreateObserableWithJust.withJustOnNext8();
        CreateObserableWithJust.withJustOnNextOnErrorOnComplete8();
    }
}
