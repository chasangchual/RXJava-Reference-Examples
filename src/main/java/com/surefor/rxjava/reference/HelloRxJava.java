package com.surefor.rxjava.reference;

import rx.Observable;
import rx.functions.Action1;

/**
 * Hello RxJava, the first RxJava codes
 *
 * Created by chae on 2/11/2016.
 */
public class HelloRxJava {
    public static void run7(String...names) {
        Observable.from(names).subscribe(new Action1<String>() {
            public void call(String s) {
                System.out.printf("Hello %s ! \n", s) ;
            }
        });
    }

    public static void run8(String...names) {
        Observable.from(names).subscribe(s -> System.out.printf("Hello %s ! \n", s));
    }

    public static void main(String[] args) {
        HelloRxJava.run7("Ethan", "Grace", "Ashley", "Chloe");
        HelloRxJava.run8("Ethan", "Grace", "Ashley", "Chloe");
    }
}
