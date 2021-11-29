package com.project.orthodonticclinic.position;

import com.ocadotechnology.gembus.test.Arranger;

public class PositionTestHelper {

    public static Position createPosition() {
        var position = new Position();
        position.setId(Arranger.someLong());
        position.setName(Arranger.someString());
        return position;
    }
}
