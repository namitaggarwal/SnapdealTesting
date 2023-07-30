package com.namit.Calculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalTest {
    int a=20;
    int b=10;
    Calculate cal = new Calculate();
    int expectedSum = a+b;
    int expectedSubtract = a-b;
    int expectedMultiply = a*b;
    int expectedDivide = a/b;
    @Test
    public void sumNumbers() {
        assertEquals(expectedSum, cal.sum(a,b));
    }
}
