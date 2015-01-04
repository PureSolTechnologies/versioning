package com.puresoltechnologies.commons.versioning;

/**
 * This class bundles mathematical functions for versions as it is done by
 * {@link Math} class.
 * 
 * @author Rick-Rainer Ludwig
 */
public class VersionMath {

    /**
     * This class returns the minimum version of the provided versions within
     * the array.
     * 
     * @param versions
     *            is an optional parameter array with additional versions to be
     *            checked for minimum version.
     * @return A {@link Version} object is returned containing the minimum
     *         version.
     */
    public static Version min(Version... versions) {
	if (versions.length == 0) {
	    throw new IllegalArgumentException(
		    "At least one version needs to be provided for minimum calculation.");
	}
	Version minimum = versions[0];
	for (int index = 1; index < versions.length; ++index) {
	    Version version = versions[index];
	    if (minimum.compareTo(version) > 0) {
		minimum = version;
	    }
	}
	return minimum;
    }

    /**
     * This class returns the maximum version of the provided versions within
     * the array.
     * 
     * @param versions
     *            is an optional parameter array with additional versions to be
     *            checked for maximum version.
     * @return A {@link Version} object is returned containing the maximum
     *         version.
     */
    public static Version max(Version... versions) {
	if (versions.length == 0) {
	    throw new IllegalArgumentException(
		    "At least one version needs to be provided for maximum calculation.");
	}
	Version maximum = versions[0];
	for (int index = 1; index < versions.length; ++index) {
	    Version version = versions[index];
	    if (maximum.compareTo(version) < 0) {
		maximum = version;
	    }
	}
	return maximum;
    }

    /**
     * Private constructor to avoid instantiation.
     */
    private VersionMath() {
    }
}
