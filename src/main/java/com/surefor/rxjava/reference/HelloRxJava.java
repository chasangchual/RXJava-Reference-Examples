package com.surefor.rxjava.reference;


import rx.Observable;
import rx.functions.Action1;

/**
 * Created by chae on 2/11/2016.
 */
public class HelloRxJava {
    public static void run(String...names) {
        Observable.from(names).subscribe(new Action1<String>() {
            public void call(String s) {
                System.out.println("Hello " + s) ;
            }
        });
    }

    public static void main(String[] args) {
        HelloRxJava.run("Ethan", "Grace", "Ashley", "Chloe");
    }
}
