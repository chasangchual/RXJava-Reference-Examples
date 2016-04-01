package com.surefor.rxjava.reference;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

import java.util.stream.IntStream;

/**
 * Created by chae on 4/1/2016.
 */
public class FilteringTake {
    public static Observable<Integer> getObserable_7() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for(int i = 0 ; i < 10; i++) {
                    subscriber.onNext(i);
                }
                if(!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public static Observable<Integer> getObserable_8() {
        return Observable.create(subscriber -> {
            IntStream.range(0, 10).forEach(v -> subscriber.onNext(v));
            if(!subscriber.isUnsubscribed()) {
                subscriber.onCompleted();
            }
        }) ;
    }

    public static void main(String[] args) {
        System.out.println("take(5)") ;
        FilteringSkip.getObserable_7().take(5).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                System.out.println(i) ;
            }
        }) ;

        System.out.println("takeLast(5)") ;
        FilteringSkip.getObserable_7().takeLast(5).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                System.out.println(i) ;
            }
        }) ;

        System.out.println("take(5)") ;
        FilteringSkip.getObserable_8().take(5).subscribe(v -> System.out.println(v) ) ;

        System.out.println("takeLast(5)") ;
        FilteringSkip.getObserable_8().takeLast(5).subscribe(v -> System.out.println(v) ) ;
    }
}
