package com.evolutionnext.javarx;

import org.junit.Test;
import rx.Observable;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * Demo 3: Standard Functional Operators
 */
public class ObservableCombiningTest {

    @Test
    public void testStartWith() throws InterruptedException {
        Observable<Integer> observable1 = Observable.range(10, 5);
        observable1.startWith(1, 2, 3).subscribe(System.out::println);
        Thread.sleep(3000);
    }


    @Test
    public void testMerge() throws InterruptedException {
        Observable<String> observable1 =
                Observable.interval(100, TimeUnit.MILLISECONDS)
                .map(x -> {
                    System.out.println("Hello1" + LocalDateTime.now());return "O1:" + x;});
        Observable<String> observable2 =
                Observable.interval(150, TimeUnit.MILLISECONDS)
                .map(x -> {
                    System.out.println("Hello2" + LocalDateTime.now());return "O2:" + x;});

        observable1.mergeWith(observable2).take(5).subscribe(System.out::println);
        Thread.sleep(1000);
        System.out.println("Midway" + LocalDateTime.now());
        observable1.mergeWith(observable2).take(5).subscribe(System.out::println);
        Thread.sleep(10000);
    }

    @Test
    public void testConcat() throws InterruptedException {
        Observable<String> observable1 =
                Observable.interval(100, TimeUnit.MILLISECONDS)
                .map(x -> "O1:" + x).take(5);
        Observable<String> observable2 =
                Observable.interval(150, TimeUnit.MILLISECONDS)
                .map(x -> "O2:" + x).take(5);
        observable1.concatWith(observable2).subscribe(System.out::println);
        Thread.sleep(20000);
    }

    @Test
    public void testAmb() throws InterruptedException {
        Observable<Integer> oneTo10 = Observable.range(1, 5)
                .delay(5, TimeUnit.SECONDS);
        Observable<Integer> tenTo20 = Observable.range(10, 10)
                .delay(2, TimeUnit.SECONDS);
        Observable<Integer> twentyTo30 = Observable.range(30, 10)
                .delay(8, TimeUnit.SECONDS);

        Observable.amb(oneTo10, tenTo20, twentyTo30)
                .subscribe(System.out::println, Throwable::printStackTrace);
        Thread.sleep(10000);
    }


    @Test
    public void testAmbWith() throws InterruptedException {
        Observable<Integer> oneTo10 = Observable.range(1, 10)
                .delay(5, TimeUnit.SECONDS);
        Observable<Integer> tenTo20 = Observable.range(30, 10)
                .delay(2, TimeUnit.SECONDS);
        Observable<Integer> twentyTo30 = Observable.range(90, 10)
                .delay(8, TimeUnit.SECONDS);

        oneTo10.ambWith(tenTo20).ambWith(twentyTo30)
                .subscribe(System.out::println, Throwable::printStackTrace);

        Thread.sleep(10000);
    }


}
