package com.puresoltechnologies.commons.versioning;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * This class represents a version range based on semantic versions implemented
 * as {@link Version}.
 * 
 * This version range supports all possible combinations of bound and unbound
 * and closed and open intervals. Unbound intervals are set with null values
 * instead of specific versions and open or closed boundaries are specfied with
 * flags.
 * 
 * <b>Attention:</b> An unbound minimum version is automatically transformed
 * into an left open boundary with version 0.0.0. This is done, because the
 * version 0.0.0 itself should not be published, but is a natural minimum
 * version.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public class VersionRange implements Serializable {

    private static final long serialVersionUID = -5875033722249170166L;

    private final Version minimum;
    private final boolean minimumIncluded;
    private final Version maximum;
    private final boolean maximumIncluded;

    /**
     * This default constructor is only convenience for JSON serialization.
     */
    public VersionRange() {
	minimum = null;
	minimumIncluded = false;
	maximum = null;
	maximumIncluded = false;
    }

    /**
     * This is the full initial value constructor. The
     * 
     * @param minimum
     *            is the minimum {@link Version} of this version range. This
     *            version may be null to signal that there is no lower boundary
     *            (not left bounded).
     * @param minimumIncluded
     *            specifies whether the minimum version is included in the range
     *            (whether it is left closed). This value is not allowed to be
     *            true in case of no lower boundary.
     * @param maximum
     *            is the maximum {@link Version} of this version range. This
     *            version may be null to signal that there is no upper boundary
     *            (not right bounded).
     * @param maximumIncluded
     *            specifies whether the maximum version is included in the range
     *            (whether it is right closed). This value is not allowed to be
     *            true in case of no upper boundary.
     */
    @JsonCreator
    public VersionRange(@JsonProperty("minimum") Version minimum,
	    @JsonProperty("minimumIncluded") boolean minimumIncluded,
	    @JsonProperty("maximum") Version maximum,
	    @JsonProperty("maximumIncluded") boolean maximumIncluded) {
	super();
	if (minimum != null) {
	    this.minimum = minimum;
	    this.minimumIncluded = minimumIncluded;
	} else {
	    if (minimumIncluded) {
		throw new IllegalArgumentException(
			"If there is no lower boundary, it cannot be included.");
	    }
	    this.minimum = new Version(0, 0, 0);
	    this.minimumIncluded = false;
	}
	this.maximum = maximum;
	this.maximumIncluded = maximumIncluded;
	if ((maximum == null) && (maximumIncluded)) {
	    throw new IllegalArgumentException(
		    "If there is no upper boundary, it cannot be included.");
	}
    }

    public Version getMinimum() {
	return minimum;
    }

    public boolean isMinimumIncluded() {
	return minimumIncluded;
    }

    public Version getMaximum() {
	return maximum;
    }

    public boolean isMaximumIncluded() {
	return maximumIncluded;
    }

    /**
     * Checks whether a specified version is included in the version range or
     * not.
     * 
     * @param version
     *            is the {@link Version} to be tested agains this range.
     * @return <code>true</code> is returned in case the version is within the
     *         current range. <code>false</code> is returned otherwise.
     */
    public final boolean includes(Version version) {
	if (minimum != null) {
	    int minimumComparison = minimum.compareTo(version);
	    if (minimumComparison > 0) {
		return false;
	    }
	    if ((!minimumIncluded) && (minimumComparison == 0)) {
		return false;
	    }
	}
	if (maximum != null) {
	    int maximumComparison = maximum.compareTo(version);
	    if (maximumComparison < 0) {
		return false;
	    }
	    if ((!maximumIncluded) && (maximumComparison == 0)) {
		return false;
	    }
	}
	return true;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((maximum == null) ? 0 : maximum.hashCode());
	result = prime * result + (maximumIncluded ? 1231 : 1237);
	result = prime * result + ((minimum == null) ? 0 : minimum.hashCode());
	result = prime * result + (minimumIncluded ? 1231 : 1237);
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	VersionRange other = (VersionRange) obj;
	if (maximum == null) {
	    if (other.maximum != null)
		return false;
	} else if (!maximum.equals(other.maximum))
	    return false;
	if (maximumIncluded != other.maximumIncluded)
	    return false;
	if (minimum == null) {
	    if (other.minimum != null)
		return false;
	} else if (!minimum.equals(other.minimum))
	    return false;
	if (minimumIncluded != other.minimumIncluded)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	if (minimumIncluded) {
	    stringBuilder.append('[');
	} else {
	    stringBuilder.append('(');
	}
	if (minimum != null) {
	    stringBuilder.append(minimum.toString());
	}
	stringBuilder.append(", ");
	if (maximum != null) {
	    stringBuilder.append(maximum.toString());
	}
	if (maximumIncluded) {
	    stringBuilder.append(']');
	} else {
	    stringBuilder.append(')');
	}
	return stringBuilder.toString();
    }
}
