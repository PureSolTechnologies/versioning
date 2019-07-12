package com.puresoltechnologies.versioning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class VersionMathTest {

    @Test
    public void testMinimumEmptyArguments() {
	assertThrows(IllegalArgumentException.class, () -> VersionMath.min());
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

    @Test
    public void testMaximumEmptyArguments() {
	assertThrows(IllegalArgumentException.class, () -> VersionMath.max());
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
