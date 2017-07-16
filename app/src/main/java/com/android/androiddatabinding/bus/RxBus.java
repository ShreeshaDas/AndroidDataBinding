package com.android.androiddatabinding.bus;


import io.reactivex.subjects.PublishSubject;

/**
 * Created by shreesha on 13/7/17.
 */

public class RxBus {
    private static RxBus sRxBus;

    private final PublishSubject<Object> bus = PublishSubject.create();

    public RxBus() {
    }

    public static RxBus getInstance() {
        if (sRxBus == null) {
            sRxBus = new RxBus();
        }
        return sRxBus;
    }

    public PublishSubject<Object> getBus() {return bus;}

    public void send(Object o) {
        bus.onNext(o);
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}
