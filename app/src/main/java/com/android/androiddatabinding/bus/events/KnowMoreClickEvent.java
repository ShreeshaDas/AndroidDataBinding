package com.android.androiddatabinding.bus.events;

import com.android.androiddatabinding.model.People;

/**
 * Created by Shreesha on 22-07-2017.
 */

public class KnowMoreClickEvent {

    public People people;

    public KnowMoreClickEvent(People people) {
        this.people = people;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }

}
