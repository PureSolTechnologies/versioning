package com.puresoltechnologies.versioning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class VersionRangeTest {

    @Test
    public void testInvalidRangeLeftUnboundButIncluded() {
	assertThrows(IllegalArgumentException.class, () -> new VersionRange(null, true, new Version(1, 0, 0), true));
    }

    @Test
    public void testInvalidRangeRightUnboundButIncluded() {
	assertThrows(IllegalArgumentException.class, () -> new VersionRange(new Version(1, 0, 0), true, null, true));
    }

    @Test
    public void testIncludesClosed() {
	VersionRange range = new VersionRange(new Version(1, 0, 0), true, new Version(2, 0, 0), true);
	assertFalse(range.includes(new Version(0, 99, 99)));
	assertTrue(range.includes(new Version(1, 0, 0)));
	assertTrue(range.includes(new Version(1, 5, 0)));
	assertTrue(range.includes(new Version(2, 0, 0)));
	assertFalse(range.includes(new Version(2, 0, 1)));
    }

    @Test
    public void testIncludesLeftClosedRightOpen() {
	VersionRange range = new VersionRange(new Version(1, 0, 0), true, new Version(2, 0, 0), false);
	assertFalse(range.includes(new Version(0, 99, 99)));
	assertTrue(range.includes(new Version(1, 0, 0)));
	assertTrue(range.includes(new Version(1, 5, 0)));
	assertFalse(range.includes(new Version(2, 0, 0)));
	assertFalse(range.includes(new Version(2, 0, 1)));
    }

    @Test
    public void testIncludesLeftOpenRightClosed() {
	VersionRange range = new VersionRange(new Version(1, 0, 0), false, new Version(2, 0, 0), true);
	assertFalse(range.includes(new Version(0, 99, 99)));
	assertFalse(range.includes(new Version(1, 0, 0)));
	assertTrue(range.includes(new Version(1, 5, 0)));
	assertTrue(range.includes(new Version(2, 0, 0)));
	assertFalse(range.includes(new Version(2, 0, 1)));
    }

    @Test
    public void testIncludesOpen() {
	VersionRange range = new VersionRange(new Version(1, 0, 0), false, new Version(2, 0, 0), false);
	assertFalse(range.includes(new Version(0, 99, 99)));
	assertFalse(range.includes(new Version(1, 0, 0)));
	assertTrue(range.includes(new Version(1, 5, 0)));
	assertFalse(range.includes(new Version(2, 0, 0)));
	assertFalse(range.includes(new Version(2, 0, 1)));
    }

    @Test
    public void testToStringClosedInterval() {
	VersionRange range = new VersionRange(new Version(1, 0, 0), true, new Version(2, 0, 0), true);
	assertEquals("[1.0.0, 2.0.0]", range.toString());
    }

    @Test
    public void testToStringLeftClosedRightOpenInterval() {
	VersionRange range = new VersionRange(new Version(1, 0, 0), true, new Version(2, 0, 0), false);
	assertEquals("[1.0.0, 2.0.0)", range.toString());
    }

    @Test
    public void testToStringLeftOpenRightClosedInterval() {
	VersionRange range = new VersionRange(new Version(1, 0, 0), false, new Version(2, 0, 0), true);
	assertEquals("(1.0.0, 2.0.0]", range.toString());
    }

    @Test
    public void testToStringOpenInterval() {
	VersionRange range = new VersionRange(new Version(1, 0, 0), false, new Version(2, 0, 0), false);
	assertEquals("(1.0.0, 2.0.0)", range.toString());
    }

    @Test
    public void testToStringLeftClosedRightUnboundInterval() {
	VersionRange range = new VersionRange(new Version(1, 0, 0), true, null, false);
	assertEquals("[1.0.0, )", range.toString());
    }

    @Test
    public void testToStringLeftOpenRightUnboundInterval() {
	VersionRange range = new VersionRange(new Version(1, 0, 0), false, null, false);
	assertEquals("(1.0.0, )", range.toString());
    }

    @Test
    public void testToStringLeftUnboundRightClosedInterval() {
	VersionRange range = new VersionRange(null, false, new Version(1, 0, 0), true);
	assertEquals("(0.0.0, 1.0.0]", range.toString());
    }

    @Test
    public void testToStringLeftUnboundRightOpenInterval() {
	VersionRange range = new VersionRange(null, false, new Version(1, 0, 0), false);
	assertEquals("(0.0.0, 1.0.0)", range.toString());
    }
}
