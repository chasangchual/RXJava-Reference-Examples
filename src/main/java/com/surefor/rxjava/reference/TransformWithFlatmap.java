package com.surefor.rxjava.reference;

import rx.Observable;
import rx.Subscriber;
import rx.functions.*;

import java.util.stream.IntStream;

/**
 * RxJava examples for map & flatmap
 *
 * Created by chae on 3/14/2016.
 */
public class TransformWithFlatmap {
    /**
     * Java 7, get observable to omit an Integer value from 0 to 9
     * @return
     */

    public static Observable<Integer> getObservable_7() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for(int i = 0 ; i < 10; i++) {
                    subscriber.onNext(i);
                }
            }
        }) ;
    }

    public static Observable<Integer> getObservable_8() {
        return Observable.create(subscriber -> {
            IntStream.range(0, 10).forEach(i -> subscriber.onNext(i));
        }) ;
    }

    public static Observable<Integer> getObserable_7_Error_Complete() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 10; i++) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(i);
                    } else {
                        break ;
                    }
                }

                if (!subscriber.isUnsubscribed()) {
                   subscriber.onError(null);
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Java 8, get observable to omit an Integer value from 0 to 9
     * @return
     */
    public static Observable<Integer> getObserable_8_Error_Complete() {
        return Observable.create(subscriber -> {
            for (int i = 0; i < 10; i++) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(i);
                } else {
                    break ;
                }
            }
            if (!subscriber.isUnsubscribed()) {
                subscriber.onError(null);
            }

            if (!subscriber.isUnsubscribed()) {
                subscriber.onCompleted();
            }
        });
    }

    /*

     */
    public static void main(String[] args) {

        // /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        // map examples, refer http://reactivex.io/documentation/operators/map.html
        //
        // /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        // 1) map an Integer value into another Integer value.

        //    http://reactivex.io/RxJava/javadoc/rx/Observable.html#map(rx.functions.Func1)
        //
        //    0, 1, 2, 3, 4, 5, 6 ...
        //   -> 0, 100, 200, 300, 400, 500, 600, ...
        //
        // /////////////////////////////////////////////////////////////////////////////////////////////////////////////

        TransformWithFlatmap.getObservable_7().map(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer v) {
                return v * 100;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer v) {
                System.out.println(v);
            }
        }) ;

        // with Java 8, lambda expression
        TransformWithFlatmap.getObservable_8().map(v -> {
            return v * 100;
        }).subscribe(v -> {
            System.out.println(v);
        }) ;

        // map an Integer value into another String value.
        TransformWithFlatmap.getObservable_7().map(new Func1<Integer, String>() {
            @Override
            public String call(Integer v) {
                return String.valueOf(v * 100);
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String v) {
                System.out.println(v);
            }
        }) ;

        // with Java 8, lambda expression
        TransformWithFlatmap.getObservable_8().map(v -> {
            return String.valueOf(v * 100);
        }).subscribe(v -> {
            System.out.println(v);
        }) ;

        // /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        // flatMap examples, http://reactivex.io/documentation/operators/flatmap.html
        //
        // /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        // 1) Returns an Observable that emits items based on applying a function that you supply to each item emitted
        //    by the source Observable, where that function returns an Observable, and then merging those resulting
        //    Observables and emitting the results of this merger.
        //
        //    http://reactivex.io/RxJava/javadoc/rx/Observable.html#flatMap(rx.functions.Func1)
        //
        //    0 -> 0
        //    1 -> 0, 1
        //    2 -> 0, 1, 2
        //    3 -> 0, 1, 2, 3
        //
        // /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        TransformWithFlatmap.getObservable_7().flatMap(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(final Integer v) {
                return Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for(int i = 0 ; i <= v; i++) {
                            if(!subscriber.isUnsubscribed()) {
                                subscriber.onNext(i); ;
                            } else {
                                break ;
                            }
                        }
                    }
                }) ;
            } ;
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer v) {
                System.out.println(v);
            }
        }) ;

        // with Java 8, lambda expression
        TransformWithFlatmap.getObservable_8().flatMap(v -> {
            return Observable.create(subscriber -> {
                for(int i = 0 ; i <= v; i++) {
                    if(!subscriber.isUnsubscribed()) {
                        subscriber.onNext(i); ;
                    } else {
                        break ;
                    }
                }
            }) ;
        }).subscribe(v -> System.out.println(v)) ;

        // /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        // 2) Returns an Observable that applies a function to each item emitted or notification raised
        //    by the source Observable and then flattens the Observables returned from these functions
        //    and emits the resulting items.
        //
        //    http://reactivex.io/RxJava/javadoc/rx/Observable.html#flatMap(rx.functions.Func1,%20rx.functions.Func1,%20rx.functions.Func0)
        //
        //    0 -> 0
        //    1 -> 0, 1
        //    2 -> 0, 1, 2
        //    3 -> 0, 1, 2, 3
        //    ...
        //    onComplete() -> 100000
        //    onError()  -> -1
        // /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        TransformWithFlatmap.getObserable_7_Error_Complete().flatMap(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(final Integer v) {
                return Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 0; i <= v; i++) {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onNext(i);
                            } else {
                                break;
                            }
                        }
                    }
                });
            }
        }, new Func1<Throwable, Observable<? extends Integer>>() {
            @Override
            public Observable<? extends Integer> call(Throwable throwable) {
                return Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        if(!subscriber.isUnsubscribed()) {
                            subscriber.onNext(-1);
                        }
                    }
                }) ;
            }
        }, new Func0<Observable<? extends Integer>>() {
            @Override
            public Observable<? extends Integer> call() {
                return Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        if(!subscriber.isUnsubscribed()) {
                            subscriber.onNext(100000);
                        }
                    }
                }) ;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer v) {
                System.out.println(v);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        }, new Action0() {
            @Override
            public void call() {

            }
        }) ;


        // with Java 8, lambda expression
        // it is polulationg
/*
        TransformWithFlatmap.getObserable_8_Error_Complete().flatMap(v -> {
        return Observable.create(subscriber -> {
            for(int i = 0 ; i <= v; i++) {
                if(!subscriber.isUnsubscribed()) {
                    subscriber.onNext(i); ;
                } else {
                    break ;
                }
            }
        }) ;
        }, throwable -> {
            return Observable.create(subscriber -> {
                subscriber.onNext(-1);
            }) ;
        }, () -> {
            return Observable.create(subscriber -> {
                subscriber.onNext(10000);
            }) ;
        }).subscribe(v -> System.out.println(v), throwable1 -> {}, () -> {}) ; ;
*/

        // /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        // 3) Returns an Observable that emits the results of a specified function to the pair of values emitted
        //    by the source Observable and a specified collection Observable.
        //
        //    http://reactivex.io/RxJava/javadoc/rx/Observable.html#flatMap(rx.functions.Func1,%20rx.functions.Func2)
        //
        //    collectionSelector selects even number and resultSelector selects odd number
        //
        //      collectionSelector : 0
        //      1, resultSelector 1
        //      collectionSelector : 2
        //      3, resultSelector 3
        //      collectionSelector : 4
        //      5, resultSelector 5
        //      collectionSelector : 6
        //      7, resultSelector 7
        //      collectionSelector : 8
        //      9, resultSelector 9
        //
        // /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        TransformWithFlatmap.getObservable_7().flatMap(new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(Integer v) {
                return Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        if (v % 2 == 0) {
                            subscriber.onNext("collectionSelector : " + String.valueOf(v));
                        } else {
                            subscriber.onNext(String.valueOf(v)) ;
                        }
                    }
                });
            }
        }, new Func2<Integer, String, String>() {
            @Override
            public String call(Integer v, String s) {
                if (v % 2 == 0) {
                    return (s);
                } else {
                    return (s + ", resultSelector " + String.valueOf(v));
                }

            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        }) ;

        // with Java 8, lambda expression
        TransformWithFlatmap.getObservable_8().flatMap(v1 -> {
            return Observable.create(subscriber -> {
                if (v1 % 2 == 0) {
                    subscriber.onNext("collectionSelector : " + String.valueOf(v1));
                } else {
                    subscriber.onNext(String.valueOf(v1)) ;
                }
            }) ;
        }, (v2, s) -> {
            if (v2 % 2 == 0) {
                return (s);
            } else {
                return (s + ", resultSelector " + String.valueOf(v2));
            }
        }) ;
    }
}
