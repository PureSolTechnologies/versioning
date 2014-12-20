package com.puresoltechnologies.commons.versioning;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;

public class VersionRangeTest {

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRangeLeftUnboundButIncluded() {
	new VersionRange(null, true, new Version(1, 0, 0), true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRangeRightUnboundButIncluded() {
	new VersionRange(new Version(1, 0, 0), true, null, true);
    }

    @Test
    public void testIncludesClosed() {
	VersionRange range = new VersionRange(new Version(1, 0, 0), true,
		new Version(2, 0, 0), true);
	assertFalse(range.includes(new Version(0, 99, 99)));
	assertTrue(range.includes(new Version(1, 0, 0)));
	assertTrue(range.includes(new Version(1, 5, 0)));
	assertTrue(range.includes(new Version(2, 0, 0)));
	assertFalse(range.includes(new Version(2, 0, 1)));
    }

    @Test
    public void testIncludesLeftClosedRightOpen() {
	VersionRange range = new VersionRange(new Version(1, 0, 0), true,
		new Version(2, 0, 0), false);
	assertFalse(range.includes(new Version(0, 99, 99)));
	assertTrue(range.includes(new Version(1, 0, 0)));
	assertTrue(range.includes(new Version(1, 5, 0)));
	assertFalse(range.includes(new Version(2, 0, 0)));
	assertFalse(range.includes(new Version(2, 0, 1)));
    }

    @Test
    public void testIncludesLeftOpenRightClosed() {
	VersionRange range = new VersionRange(new Version(1, 0, 0), false,
		new Version(2, 0, 0), true);
	assertFalse(range.includes(new Version(0, 99, 99)));
	assertFalse(range.includes(new Version(1, 0, 0)));
	assertTrue(range.includes(new Version(1, 5, 0)));
	assertTrue(range.includes(new Version(2, 0, 0)));
	assertFalse(range.includes(new Version(2, 0, 1)));
    }

    @Test
    public void testIncludesOpen() {
	VersionRange range = new VersionRange(new Version(1, 0, 0), false,
		new Version(2, 0, 0), false);
	assertFalse(range.includes(new Version(0, 99, 99)));
	assertFalse(range.includes(new Version(1, 0, 0)));
	assertTrue(range.includes(new Version(1, 5, 0)));
	assertFalse(range.includes(new Version(2, 0, 0)));
	assertFalse(range.includes(new Version(2, 0, 1)));
    }

    @Test
    public void testToStringClosedInterval() {
	VersionRange range = new VersionRange(new Version(1, 0, 0), true,
		new Version(2, 0, 0), true);
	assertEquals("[1.0.0, 2.0.0]", range.toString());
    }

    @Test
    public void testToStringLeftClosedRightOpenInterval() {
	VersionRange range = new VersionRange(new Version(1, 0, 0), true,
		new Version(2, 0, 0), false);
	assertEquals("[1.0.0, 2.0.0)", range.toString());
    }

    @Test
    public void testToStringLeftOpenRightClosedInterval() {
	VersionRange range = new VersionRange(new Version(1, 0, 0), false,
		new Version(2, 0, 0), true);
	assertEquals("(1.0.0, 2.0.0]", range.toString());
    }

    @Test
    public void testToStringOpenInterval() {
	VersionRange range = new VersionRange(new Version(1, 0, 0), false,
		new Version(2, 0, 0), false);
	assertEquals("(1.0.0, 2.0.0)", range.toString());
    }

    @Test
    public void testToStringLeftClosedRightUnboundInterval() {
	VersionRange range = new VersionRange(new Version(1, 0, 0), true, null,
		false);
	assertEquals("[1.0.0, )", range.toString());
    }

    @Test
    public void testToStringLeftOpenRightUnboundInterval() {
	VersionRange range = new VersionRange(new Version(1, 0, 0), false,
		null, false);
	assertEquals("(1.0.0, )", range.toString());
    }

    @Test
    public void testToStringLeftUnboundRightClosedInterval() {
	VersionRange range = new VersionRange(null, false,
		new Version(1, 0, 0), true);
	assertEquals("(0.0.0, 1.0.0]", range.toString());
    }

    @Test
    public void testToStringLeftUnboundRightOpenInterval() {
	VersionRange range = new VersionRange(null, false,
		new Version(1, 0, 0), false);
	assertEquals("(0.0.0, 1.0.0)", range.toString());
    }

    @Test
    public void testJSONSerialization() throws JsonGenerationException,
	    JsonMappingException, IOException {
	VersionRange original = new VersionRange(new Version(1, 2, 3, "pre1",
		"meta1"), false, new Version(4, 5, 6, "pre2", "meta2"), true);
	String serialized = JSONSerializer.toJSONString(original);
	VersionRange deserialized = JSONSerializer.fromJSONString(serialized,
		VersionRange.class);
	assertEquals(original, deserialized);
    }
}
