package com.surefor.rxjava.reference;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

import java.util.stream.IntStream;

/**
 * Created by chae on 4/1/2016.
 */
public class FilteringDistinct {
    public static Observable<Integer> getObservable_7() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for(int i = 0 ; i < 10; i ++) {
                    subscriber.onNext(i);
                    subscriber.onNext(i);
                }

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
            IntStream.range(0, 10).forEach(i -> {subscriber.onNext(i); subscriber.onNext(i);});
        }) ;
    }

    public static void main(String[] args) {
        System.out.println("distinct()") ;
        FilteringDistinct.getObservable_7().distinct().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                System.out.println(i) ;
            }
        }) ;

        System.out.println("distinct()") ;
        FilteringFirst.getObservable_8().distinct().subscribe(v -> {System.out.println(v) ;}) ;
    }
}
