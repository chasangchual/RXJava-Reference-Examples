package com.surefor.rxjava.reference;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * General ways to create an observable object with Observable.from()
 *
 * Once an observable object is created, RxJava will synchronously invoke a method corresponding to onNext()
 * or three methods corresponding to onNext(), onError() and onComplete().
 *
 * Created by chae on 2/11/2016.
 */
public class CreateObserableWithFrom {
    /**
     * Java 7, crate an observable object with Integer list. Then subscribe it covering onNext()
     */
    public static void withFromOnNext_7() {
        Integer[] list = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0} ;
        Observable<Integer> observable = Observable.from(list) ;

        Subscription s = observable.subscribe(new Action1<Integer>() { // for onNext()
            public void call(Integer i) {
                System.out.printf("%d\n", i) ;
            }
        }) ;
    }

    /**
     * Java 7, crate an observable object with Integer list. Then subscribe it covering onNext(), onError() and onComplete
     */
    public static void withFromOnNextOnErrorOnComplete_7() {
        Integer[] list = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0} ;
        Observable<Integer> observable = Observable.from(list) ;

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
    public static void withFromOnNext_8() {
        Integer[] list = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        Observable<Integer> observable = Observable.from(list);

        Subscription s = observable.subscribe(i -> System.out.printf("%d\n", i)) ;
    }

    /**
     * Java 8, crate an observable object with Integer list. Then subscribe it covering onNext(), onError() and onComplete
     */
    public static void withFromOnNextOnErrorOnComplete_8() {
        Integer[] list = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        Observable<Integer> observable = Observable.from(list);

        Subscription s = observable.subscribe(
                i -> System.out.printf("%d\n", i),
                throwable -> System.out.println(throwable.getMessage()),
                ()-> System.out.println("complete of withFromOnNextOnErrorOnComplete8()")) ;
    }

    public static void main(String[] args) {
        CreateObserableWithFrom.withFromOnNext_7();
        CreateObserableWithFrom.withFromOnNextOnErrorOnComplete_7();
        CreateObserableWithFrom.withFromOnNext_8();
        CreateObserableWithFrom.withFromOnNextOnErrorOnComplete_8();
    }
}
