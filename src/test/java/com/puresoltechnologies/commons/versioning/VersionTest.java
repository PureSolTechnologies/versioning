package com.puresoltechnologies.commons.versioning;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;

public class VersionTest {

    @Test
    public void testLegalVersions() {
	new Version(0, 0, 1, "", "");
	new Version(0, 0, 1, "a.a.a", "b.b.b");
	new Version(0, 0, 1, "AA.2-3", "BB.4-5");
	new Version(0, 0, 1, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalLeadingZeroMajorNumber() {
	Version.valueOf("01.2.3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalLeadingZeroMinorNumber() {
	Version.valueOf("1.02.3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalLeadingZeroPatchNumber() {
	Version.valueOf("1.2.03");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalLeadingZeroPreReleaseInformation() {
	Version.valueOf("1.2.3-1.02");
    }

    @Test
    public void testValueOf() {
	Version version = Version.valueOf("1.2.3-pre+build");
	assertThat(version.getMajor(), equalTo(1));
	assertThat(version.getMinor(), equalTo(2));
	assertThat(version.getPatch(), equalTo(3));
	assertThat(version.getPreReleaseInformation(), equalTo("pre"));
	assertThat(version.getBuildMetadata(), equalTo("build"));

	version = Version.valueOf("1.2.3-alpha");
	assertThat(version.getMajor(), equalTo(1));
	assertThat(version.getMinor(), equalTo(2));
	assertThat(version.getPatch(), equalTo(3));
	assertThat(version.getPreReleaseInformation(), equalTo("alpha"));
	assertThat(version.getBuildMetadata(), equalTo(null));

	version = Version.valueOf("1.3.5-alpha.1");
	assertThat(version.getMajor(), equalTo(1));
	assertThat(version.getMinor(), equalTo(3));
	assertThat(version.getPatch(), equalTo(5));
	assertThat(version.getPreReleaseInformation(), equalTo("alpha.1"));
	assertThat(version.getBuildMetadata(), equalTo(null));

	version = Version.valueOf("1.0.0-0.3.7");
	assertThat(version.getMajor(), equalTo(1));
	assertThat(version.getMinor(), equalTo(0));
	assertThat(version.getPatch(), equalTo(0));
	assertThat(version.getPreReleaseInformation(), equalTo("0.3.7"));
	assertThat(version.getBuildMetadata(), equalTo(null));

	version = Version.valueOf("1.0.0-x.7.z.92");
	assertThat(version.getMajor(), equalTo(1));
	assertThat(version.getMinor(), equalTo(0));
	assertThat(version.getPatch(), equalTo(0));
	assertThat(version.getPreReleaseInformation(), equalTo("x.7.z.92"));
	assertThat(version.getBuildMetadata(), equalTo(null));

	version = Version.valueOf("1.0.0-alpha+001");
	assertThat(version.getMajor(), equalTo(1));
	assertThat(version.getMinor(), equalTo(0));
	assertThat(version.getPatch(), equalTo(0));
	assertThat(version.getPreReleaseInformation(), equalTo("alpha"));
	assertThat(version.getBuildMetadata(), equalTo("001"));

	version = Version.valueOf("1.0.0+20130313144700");
	assertThat(version.getMajor(), equalTo(1));
	assertThat(version.getMinor(), equalTo(0));
	assertThat(version.getPatch(), equalTo(0));
	assertThat(version.getPreReleaseInformation(), equalTo(null));
	assertThat(version.getBuildMetadata(), equalTo("20130313144700"));

	version = Version.valueOf("1.0.0-beta+exp.sha.5114f85");
	assertThat(version.getMajor(), equalTo(1));
	assertThat(version.getMinor(), equalTo(0));
	assertThat(version.getPatch(), equalTo(0));
	assertThat(version.getPreReleaseInformation(), equalTo("beta"));
	assertThat(version.getBuildMetadata(), equalTo("exp.sha.5114f85"));
    }

    @Test
    public void testLegalZeroVersion() {
	new Version(0, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalMajorVersion() {
	new Version(-1, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalMinorVersion() {
	new Version(0, -1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalPatchVersion() {
	new Version(0, 0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalPreReleaseInformation() {
	new Version(0, 0, 1, "a.2-3.1#");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalBuildMetadata() {
	new Version(0, 0, 1, null, "a.2-3.1#");
    }

    @Test
    public void testSimpleToString() {
	assertThat(new Version(1, 0, 0).toString(), equalTo("1.0.0"));
	assertThat(new Version(0, 2, 0).toString(), equalTo("0.2.0"));
	assertThat(new Version(0, 0, 3).toString(), equalTo("0.0.3"));
    }

    @Test
    public void testPreInformationToString() {
	assertThat(new Version(1, 0, 0, "alpha").toString(),
		equalTo("1.0.0-alpha"));
	assertThat(new Version(1, 0, 0, "alpha.1").toString(),
		equalTo("1.0.0-alpha.1"));
	assertThat(new Version(1, 0, 0, "0.3.7").toString(),
		equalTo("1.0.0-0.3.7"));
	assertThat(new Version(1, 0, 0, "x.7.z.92").toString(),
		equalTo("1.0.0-x.7.z.92"));
    }

    @Test
    public void testBuildMetadataToString() {
	assertThat(new Version(1, 0, 0, "alpha", "001").toString(),
		equalTo("1.0.0-alpha+001"));
	assertThat(new Version(1, 0, 0, "", "20130313144700").toString(),
		equalTo("1.0.0+20130313144700"));
	assertThat(new Version(1, 0, 0, "beta", "exp.sha.5114f85").toString(),
		equalTo("1.0.0-beta+exp.sha.5114f85"));
    }

    public void testSimpleCompareTo() {
	Version version1 = Version.valueOf("1.0.0");
	Version version2 = Version.valueOf("2.0.0");
	Version version3 = Version.valueOf("2.1.0");
	Version version4 = Version.valueOf("2.1.1");

	assertThat(version1.compareTo(version2), equalTo(-1));
	assertThat(version1.compareTo(version1), equalTo(0));
	assertThat(version2.compareTo(version1), equalTo(1));

	assertThat(version2.compareTo(version3), equalTo(-1));
	assertThat(version2.compareTo(version2), equalTo(0));
	assertThat(version3.compareTo(version2), equalTo(1));

	assertThat(version3.compareTo(version4), equalTo(-1));
	assertThat(version3.compareTo(version3), equalTo(0));
	assertThat(version4.compareTo(version3), equalTo(1));

	assertThat(version4.compareTo(version4), equalTo(0));
    }

    @Test
    public void testComplexCompareTo() {
	Version version1 = Version.valueOf("1.0.0-alpha");
	Version version2 = Version.valueOf("1.0.0-alpha.1");
	Version version3 = Version.valueOf("1.0.0-alpha.beta");
	Version version4 = Version.valueOf("1.0.0-beta");
	Version version5 = Version.valueOf("1.0.0-beta.2");
	Version version6 = Version.valueOf("1.0.0-beta.11");
	Version version7 = Version.valueOf("1.0.0-rc.1");
	Version version8 = Version.valueOf("1.0.0");

	assertThat(version1.compareTo(version2), equalTo(-1));
	assertThat(version1.compareTo(version1), equalTo(0));
	assertThat(version2.compareTo(version1), equalTo(1));

	assertThat(version2.compareTo(version3), equalTo(-1));
	assertThat(version2.compareTo(version2), equalTo(0));
	assertThat(version3.compareTo(version2), equalTo(1));

	assertThat(version3.compareTo(version4), equalTo(-1));
	assertThat(version3.compareTo(version3), equalTo(0));
	assertThat(version4.compareTo(version3), equalTo(1));

	assertThat(version4.compareTo(version5), equalTo(-1));
	assertThat(version4.compareTo(version4), equalTo(0));
	assertThat(version5.compareTo(version4), equalTo(1));

	assertThat(version5.compareTo(version6), equalTo(-1));
	assertThat(version5.compareTo(version5), equalTo(0));
	assertThat(version6.compareTo(version5), equalTo(1));

	assertThat(version6.compareTo(version7), equalTo(-16));
	assertThat(version6.compareTo(version6), equalTo(0));
	assertThat(version7.compareTo(version6), equalTo(16));

	assertThat(version7.compareTo(version8), equalTo(-1));
	assertThat(version7.compareTo(version7), equalTo(0));
	assertThat(version8.compareTo(version7), equalTo(1));

	assertThat(version8.compareTo(version8), equalTo(0));
    }

    @Test
    public void testJSONSerialization() throws JsonGenerationException,
	    JsonMappingException, IOException {
	Version original = new Version(1, 2, 3, "pre", "meta");
	String serialized = JSONSerializer.toJSONString(original);
	Version deserialized = JSONSerializer.fromJSONString(serialized,
		Version.class);
	assertEquals(original, deserialized);
    }
}
