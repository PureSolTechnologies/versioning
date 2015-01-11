package com.puresoltechnologies.commons.versioning;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VersionMathTest {

    @Test(expected = IllegalArgumentException.class)
    public void testMinimumEmptyArguments() {
	VersionMath.min();
    }

    @Test
    public void testMinimum1() {
	Version version1 = new Version(0, 1, 0);
	Version minimum = VersionMath.min(version1);
	assertEquals(version1, minimum);
    }

    @Test
    public void testMinimum2() {
	Version version1 = new Version(0, 1, 0);
	Version version2 = new Version(1, 0, 0);
	Version minimum = VersionMath.min(version1, version2);
	assertEquals(version1, minimum);
    }

    @Test
    public void testMinimum3() {
	Version version1 = new Version(1, 0, 0);
	Version version2 = new Version(0, 0, 0);
	Version version3 = new Version(0, 1, 0);
	Version minimum = VersionMath.min(version1, version2, version3);
	assertEquals(version2, minimum);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaximumEmptyArguments() {
	VersionMath.max();
    }

    @Test
    public void testMaximum1() {
	Version version1 = new Version(0, 1, 0);
	Version minimum = VersionMath.max(version1);
	assertEquals(version1, minimum);
    }

    @Test
    public void testMaximum2() {
	Version version1 = new Version(0, 1, 0);
	Version version2 = new Version(1, 0, 0);
	Version minimum = VersionMath.max(version1, version2);
	assertEquals(version2, minimum);
    }

    @Test
    public void testMaximum3() {
	Version version1 = new Version(1, 0, 0);
	Version version2 = new Version(0, 0, 0);
	Version version3 = new Version(0, 1, 0);
	Version minimum = VersionMath.max(version1, version2, version3);
	assertEquals(version1, minimum);
    }
}
