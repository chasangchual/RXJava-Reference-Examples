package com.surefor.rxjava.reference;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * A way to make an observable with Obserable.create()
 *
 * Created by chae on 2/11/2016.
 */
public class CreateObserableWithCreateMethod {
    private static void triggerOnNext(Subscriber<? super Integer> subscriber) {
        for(int i = 0 ; i < 4 ; i ++) {
            if(!subscriber.isUnsubscribed()) {
                subscriber.onNext(i);
            }
        }
        if(!subscriber.isUnsubscribed()) {
            subscriber.onCompleted();
        }
    }

    public static Observable<Integer> getObservable() {
        // new Observable.OnSubscribe<Integer>() will be invoked when Observable.subscribe() is called.
        // Meaning Observable.create() creates an observable with OnSubscribe() handler, then when Observable.subscribe()
        // is called, call(Subscriber<? super Integer> subscriber) will be invoked.
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            public void call(Subscriber<? super Integer> subscriber) {
                triggerOnNext(subscriber) ;
            }
        }) ;
    }

    public static Observable<Integer> getObservable2() {

        return Observable.create(new Observable.OnSubscribe<Integer>() {
            public void call(Subscriber<? super Integer> subscriber) {
                triggerOnNext(subscriber) ;
            }
        }).observeOn(Schedulers.newThread()) ;
    }

    /**
     * Here, we want to invoke a subscriber action in both, synchronous way and asynchronous way.
     * It gets a observable to invoke onNext() 4 times with a integer parameter.
     * Then it will subscribe the observable with a scheduler or not.
     * @param args
     */
    public static void main(String[] args) {
        ////////////////////////////////////////////////////////////////////////////////////////////////
        // invoke the subscriber action in asynchronous way
        ////////////////////////////////////////////////////////////////////////////////////////////////

        // 1) take a observable to invoke onNext() 4 times with a integer parameter.
        Observable<Integer> observable = CreateObserableWithCreateMethod.getObservable() ;

        // 2) Asynchronously subscribe given observable with a scheduler.
        // So, every time when subscriber.onNext() is executed, it creates a new thread and invoke call() method of Action1
        ////////////////////////////////////////////////////////////////////////////////////////////////
        System.out.println("-- begin subscribe a observable in a asynchronous way --") ;
        observable.subscribeOn(Schedulers.newThread()).subscribe(new Action1<Integer>() {
            public void call(Integer i) {
                try {
                    System.out.println("[ " + String.valueOf(System.currentTimeMillis()) + "] non blocking call : " + String.valueOf(i)) ;
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }) ;
        System.out.println("-- end subscribe a observable in a asynchronous way --") ;

        ////////////////////////////////////////////////////////////////////////////////////////////////
        // invoke the subscriber action in synchronous way
        ////////////////////////////////////////////////////////////////////////////////////////////////
        observable = CreateObserableWithCreateMethod.getObservable() ;
        // subscribes Observers but in synchronous way.
        System.out.println("## begin subscribe a observable in a synchronous way ##") ;
        observable.subscribe(new Action1<Integer>() {
            public void call(Integer i) {
                try {
                    System.out.println("[ " + String.valueOf(System.currentTimeMillis()) + "] blocking call : " + String.valueOf(i)) ;
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }) ;
        System.out.println("## end subscribe a observable in a synchronous way ##") ;
    }
}
