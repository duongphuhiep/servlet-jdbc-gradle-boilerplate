package com.hiep.tuto;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class HelloTests {
    public static final Hello h = new Hello();;

    @Test
    public void GetFullNamesTest() {

        ArrayList<String> fullNames = h.GetFullNames();
        assertTrue(fullNames.size() > 0);
    }

    @Test
    public void LogInfoTest() {
        h.logInfo("bababa");
    }
}