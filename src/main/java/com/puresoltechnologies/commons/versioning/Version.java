package com.puresoltechnologies.commons.versioning;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * <p>
 * This class contains version information which is to be interpreted as
 * described in Semantic Versioning 2.0.0 (<a
 * href="http://semver.org">semver.org</a>).
 * </p>
 * <p>
 * <b>Note: this class has a natural ordering that is inconsistent with
 * {@link #equals(Object)}.</b> equals checks correctly with build metadata
 * information included, but Semantic Versioning does not include this metadata
 * for version comparison. For any version comparison, use
 * {@link #compareTo(Version)} instead of {@link #equals(Object)}.
 * </p>
 * <h1>Semantic Versioning 2.0.0</h1> <h2>Summary</h2>
 * <p>
 * Given a version number MAJOR.MINOR.PATCH, increment the:
 * </p>
 * 
 * <ol>
 * <li>MAJOR version when you make incompatible API changes,</li>
 * <li>MINOR version when you add functionality in a backwards-compatible
 * manner, and</li>
 * <li>PATCH version when you make backwards-compatible bug fixes.</li>
 * </ol>
 * <p>
 * Additional labels for pre-release and build metadata are available as
 * extensions to the MAJOR.MINOR.PATCH format.
 * </p>
 * <h2>Introduction</h2>
 * <p>
 * In the world of software management there exists a dread place called
 * "dependency hell." The bigger your system grows and the more packages you
 * integrate into your software, the more likely you are to find yourself, one
 * day, in this pit of despair.
 * </p>
 * <p>
 * In systems with many dependencies, releasing new package versions can quickly
 * become a nightmare. If the dependency specifications are too tight, you are
 * in danger of version lock (the inability to upgrade a package without having
 * to release new versions of every dependent package). If dependencies are
 * specified too loosely, you will inevitably be bitten by version promiscuity
 * (assuming compatibility with more future versions than is reasonable).
 * Dependency hell is where you are when version lock and/or version promiscuity
 * prevent you from easily and safely moving your project forward.
 * </p>
 * <p>
 * As a solution to this problem, I propose a simple set of rules and
 * requirements that dictate how version numbers are assigned and incremented.
 * These rules are based on but not necessarily limited to pre-existing
 * widespread common practices in use in both closed and open-source software.
 * For this system to work, you first need to declare a public API. This may
 * consist of documentation or be enforced by the code itself. Regardless, it is
 * important that this API be clear and precise. Once you identify your public
 * API, you communicate changes to it with specific increments to your version
 * number. Consider a version format of X.Y.Z (Major.Minor.Patch). Bug fixes not
 * affecting the API increment the patch version, backwards compatible API
 * additions/changes increment the minor version, and backwards incompatible API
 * changes increment the major version.
 * </p>
 * <p>
 * I call this system "Semantic Versioning." Under this scheme, version numbers
 * and the way they change convey meaning about the underlying code and what has
 * been modified from one version to the next.
 * </p>
 * <h2>Semantic Versioning Specification (SemVer)</h2>
 * <p>
 * The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT", "SHOULD",
 * "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this document are to be
 * interpreted as described in RFC 2119.
 * </p>
 * <ol>
 * <li>Software using Semantic Versioning MUST declare a public API. This API
 * could be declared in the code itself or exist strictly in documentation.
 * However it is done, it should be precise and comprehensive.</li>
 * 
 * <li>A normal version number MUST take the form X.Y.Z where X, Y, and Z are
 * non-negative integers, and MUST NOT contain leading zeroes. X is the major
 * version, Y is the minor version, and Z is the patch version. Each element
 * MUST increase numerically. For instance: 1.9.0 -&gt; 1.10.0 -&gt; 1.11.0.</li>
 * 
 * <li>Once a versioned package has been released, the contents of that version
 * MUST NOT be modified. Any modifications MUST be released as a new version.</li>
 * 
 * <li>Major version zero (0.y.z) is for initial development. Anything may
 * change at any time. The public API should not be considered stable..</li>
 * 
 * <li>Version 1.0.0 defines the public API. The way in which the version number
 * is incremented after this release is dependent on this public API and how it
 * changes..</li>
 * 
 * <li>Patch version Z (x.y.Z | x &gt; 0) MUST be incremented if only backwards
 * compatible bug fixes are introduced. A bug fix is defined as an internal
 * change that fixes incorrect behavior..</li>
 * 
 * <li>Minor version Y (x.Y.z | x &gt; 0) MUST be incremented if new, backwards
 * compatible functionality is introduced to the public API. It MUST be
 * incremented if any public API functionality is marked as deprecated. It MAY
 * be incremented if substantial new functionality or improvements are
 * introduced within the private code. It MAY include patch level changes. Patch
 * version MUST be reset to 0 when minor version is incremented..</li>
 * 
 * <li>Major version X (X.y.z | X &gt; 0) MUST be incremented if any backwards
 * incompatible changes are introduced to the public API. It MAY include minor
 * and patch level changes. Patch and minor version MUST be reset to 0 when
 * major version is incremented..</li>
 * 
 * <li>A pre-release version MAY be denoted by appending a hyphen and a series
 * of dot separated identifiers immediately following the patch version.
 * Identifiers MUST comprise only ASCII alphanumerics and hyphen [0-9A-Za-z-].
 * Identifiers MUST NOT be empty. Numeric identifiers MUST NOT include leading
 * zeroes. Pre-release versions have a lower precedence than the associated
 * normal version. A pre-release version indicates that the version is unstable
 * and might not satisfy the intended compatibility requirements as denoted by
 * its associated normal version. Examples: 1.0.0-alpha, 1.0.0-alpha.1,
 * 1.0.0-0.3.7, 1.0.0-x.7.z.92..</li>
 * 
 * <li>Build metadata MAY be denoted by appending a plus sign and a series of
 * dot separated identifiers immediately following the patch or pre-release
 * version. Identifiers MUST comprise only ASCII alphanumerics and hyphen
 * [0-9A-Za-z-]. Identifiers MUST NOT be empty. Build metadata SHOULD be ignored
 * when determining version precedence. Thus two versions that differ only in
 * the build metadata, have the same precedence. Examples: 1.0.0-alpha+001,
 * 1.0.0+20130313144700, 1.0.0-beta+exp.sha.5114f85..</li>
 * 
 * <li>Precedence refers to how versions are compared to each other when
 * ordered. Precedence MUST be calculated by separating the version into major,
 * minor, patch and pre-release identifiers in that order (Build metadata does
 * not figure into precedence). Precedence is determined by the first difference
 * when comparing each of these identifiers from left to right as follows:
 * Major, minor, and patch versions are always compared numerically. Example:
 * 1.0.0 &gt; 2.0.0 &gt; 2.1.0 &gt; 2.1.1. When major, minor, and patch are
 * equal, a pre-release version has lower precedence than a normal version.
 * Example: 1.0.0-alpha &gt; 1.0.0. Precedence for two pre-release versions with
 * the same major, minor, and patch version MUST be determined by comparing each
 * dot separated identifier from left to right until a difference is found as
 * follows: identifiers consisting of only digits are compared numerically and
 * identifiers with letters or hyphens are compared lexically in ASCII sort
 * order. Numeric identifiers always have lower precedence than non-numeric
 * identifiers. A larger set of pre-release fields has a higher precedence than
 * a smaller set, if all of the preceding identifiers are equal. Example:
 * 1.0.0-alpha &gt; 1.0.0-alpha.1 &gt; 1.0.0-alpha.beta &gt; 1.0.0-beta &gt;
 * 1.0.0-beta.2 &gt; 1.0.0-beta.11 &gt; 1.0.0-rc.1 v 1.0.0..</li>
 * </ol>
 * <h2>Why Use Semantic Versioning?</h2>
 * <p>
 * This is not a new or revolutionary idea. In fact, you probably do something
 * close to this already. The problem is that "close" isn't good enough. Without
 * compliance to some sort of formal specification, version numbers are
 * essentially useless for dependency management. By giving a name and clear
 * definition to the above ideas, it becomes easy to communicate your intentions
 * to the users of your software. Once these intentions are clear, flexible (but
 * not too flexible) dependency specifications can finally be made.
 * </p>
 * <p>
 * A simple example will demonstrate how Semantic Versioning can make dependency
 * hell a thing of the past. Consider a library called "Firetruck." It requires
 * a Semantically Versioned package named "Ladder." At the time that Firetruck
 * is created, Ladder is at version 3.1.0. Since Firetruck uses some
 * functionality that was first introduced in 3.1.0, you can safely specify the
 * Ladder dependency as greater than or equal to 3.1.0 but less than 4.0.0. Now,
 * when Ladder version 3.1.1 and 3.2.0 become available, you can release them to
 * your package management system and know that they will be compatible with
 * existing dependent software.
 * </p>
 * <p>
 * As a responsible developer you will, of course, want to verify that any
 * package upgrades function as advertised. The real world is a messy place;
 * there's nothing we can do about that but be vigilant. What you can do is let
 * Semantic Versioning provide you with a sane way to release and upgrade
 * packages without having to roll new versions of dependent packages, saving
 * you time and hassle.
 * </p>
 * <p>
 * If all of this sounds desirable, all you need to do to start using Semantic
 * Versioning is to declare that you are doing so and then follow the rules.
 * Link to this website from your README so others know the rules and can
 * benefit from them.
 * </p>
 * <h2>FAQ</h2> <h3>How should I deal with revisions in the 0.y.z initial
 * development phase?</h3>
 * <p>
 * The simplest thing to do is start your initial development release at 0.1.0
 * and then increment the minor version for each subsequent release.
 * </p>
 * <h3>How do I know when to release 1.0.0?</h3>
 * <p>
 * If your software is being used in production, it should probably already be
 * 1.0.0. If you have a stable API on which users have come to depend, you
 * should be 1.0.0. If you're worrying a lot about backwards compatibility, you
 * should probably already be 1.0.0.
 * </p>
 * <h3>Doesn't this discourage rapid development and fast iteration?</h3>
 * <p>
 * Major version zero is all about rapid development. If you're changing the API
 * every day you should either still be in version 0.y.z or on a separate
 * development branch working on the next major version.
 * </p>
 * <h3>If even the tiniest backwards incompatible changes to the public API
 * require a major version bump, won't I end up at version 42.0.0 very rapidly?</h3>
 * <p>
 * This is a question of responsible development and foresight. Incompatible
 * changes should not be introduced lightly to software that has a lot of
 * dependent code. The cost that must be incurred to upgrade can be significant.
 * Having to bump major versions to release incompatible changes means you'll
 * think through the impact of your changes, and evaluate the cost/benefit ratio
 * involved. Documenting the entire public API is too much work!
 * </p>
 * <p>
 * It is your responsibility as a professional developer to properly document
 * software that is intended for use by others. Managing software complexity is
 * a hugely important part of keeping a project efficient, and that's hard to do
 * if nobody knows how to use your software, or what methods are safe to call.
 * In the long run, Semantic Versioning, and the insistence on a well defined
 * public API can keep everyone and everything running smoothly.
 * </p>
 * <h3>What do I do if I accidentally release a backwards incompatible change as
 * a minor version?</h3>
 * <p>
 * As soon as you realize that you've broken the Semantic Versioning spec, fix
 * the problem and release a new minor version that corrects the problem and
 * restores backwards compatibility. Even under this circumstance, it is
 * unacceptable to modify versioned releases. If it's appropriate, document the
 * offending version and inform your users of the problem so that they are aware
 * of the offending version.
 * </p>
 * <h3>What should I do if I update my own dependencies without changing the
 * public API?</h3>
 * <p>
 * That would be considered compatible since it does not affect the public API.
 * Software that explicitly depends on the same dependencies as your package
 * should have their own dependency specifications and the author will notice
 * any conflicts. Determining whether the change is a patch level or minor level
 * modification depends on whether you updated your dependencies in order to fix
 * a bug or introduce new functionality. I would usually expect additional code
 * for the latter instance, in which case it's obviously a minor level
 * increment.
 * </p>
 * <h3>What if I inadvertently alter the public API in a way that is not
 * compliant with the version number change (i.e. the code incorrectly
 * introduces a major breaking change in a patch release)</h3>
 * <p>
 * Use your best judgment. If you have a huge audience that will be drastically
 * impacted by changing the behavior back to what the public API intended, then
 * it may be best to perform a major version release, even though the fix could
 * strictly be considered a patch release. Remember, Semantic Versioning is all
 * about conveying meaning by how the version number changes. If these changes
 * are important to your users, use the version number to inform them.
 * </p>
 * <h3>How should I handle deprecating functionality?</h3>
 * <p>
 * Deprecating existing functionality is a normal part of software development
 * and is often required to make forward progress. When you deprecate part of
 * your public API, you should do two things: (1) update your documentation to
 * let users know about the change, (2) issue a new minor release with the
 * deprecation in place. Before you completely remove the functionality in a new
 * major release there should be at least one minor release that contains the
 * deprecation so that users can smoothly transition to the new API.
 * </p>
 * <h3>Does semver have a size limit on the version string?</h3>
 * <p>
 * No, but use good judgment. A 255 character version string is probably
 * overkill, for example. Also, specific systems may impose their own limits on
 * the size of the string.
 * </p>
 * <h2>About</h2>
 * <p>
 * The Semantic Versioning specification is authored by Tom Preston-Werner,
 * inventor of Gravatars and cofounder of GitHub.
 * </p>
 * <p>
 * If you'd like to leave feedback, please open an issue on GitHub.
 * </p>
 * <h2>License</h2>
 * <p>
 * Creative Commons - CC BY 3.0
 * </p>
 * 
 * @author Rick-Rainer Ludwig
 */
public class Version implements Serializable, Comparable<Version> {

    private static final long serialVersionUID = -1244266300301153293L;

    private static final String IDENTIFIER_CHARACTERS = "[0-9A-Za-z-]";

    private static final String NUMERIC_IDENTIFIER = "(0|[1-9]\\d*)";
    private static final String NON_NUMERIC_IDENTIFIER = "\\d*[A-Za-z-]"
	    + IDENTIFIER_CHARACTERS + "*";

    private static final String PRE_RELEASE_IDENTIFIER = "("
	    + NUMERIC_IDENTIFIER + "|" + NON_NUMERIC_IDENTIFIER + ")";

    private static final String BUILD_IDENTIFIER = IDENTIFIER_CHARACTERS + "+";

    private static final String PRE_RELEASE_INFORMATION = PRE_RELEASE_IDENTIFIER
	    + "(\\." + PRE_RELEASE_IDENTIFIER + ")*";

    private static final String BUILD_INFORMATION = BUILD_IDENTIFIER + "(\\."
	    + BUILD_IDENTIFIER + ")*";

    private static final Pattern preReleaseInformationPattern = Pattern
	    .compile(PRE_RELEASE_INFORMATION);
    private static final Pattern buildMetaDataPattern = Pattern
	    .compile(BUILD_INFORMATION);

    private static final String versionRegExp = NUMERIC_IDENTIFIER + "\\."
	    + NUMERIC_IDENTIFIER + "\\." + NUMERIC_IDENTIFIER + "(-("
	    + PRE_RELEASE_INFORMATION + "))?(\\+(" + BUILD_INFORMATION + "))?";

    private static final Pattern versionPattern = Pattern.compile("^"
	    + versionRegExp + "$");

    public static Version valueOf(String versionString) {
	Matcher matcher = versionPattern.matcher(versionString);
	if (!matcher.matches()) {
	    throw new IllegalArgumentException(
		    "The version string must match the pattern '"
			    + versionRegExp + "'.");
	}
	int majorVersion = Integer.parseInt(matcher.group(1));
	int minorVersion = Integer.parseInt(matcher.group(2));
	int patchVersion = Integer.parseInt(matcher.group(3));
	String preReleaseInformation = matcher.group(5);
	String buildMetadata = matcher.group(12);
	return new Version(majorVersion, minorVersion, patchVersion,
		preReleaseInformation, buildMetadata);
    }

    private final int major;
    private final int minor;
    private final int patch;
    private final String preReleaseInformation;
    private final String buildMetadata;

    /**
     * This default constructor is only convenience for JSON serialization.
     */
    public Version() {
	major = -1;
	minor = -1;
	patch = -1;
	preReleaseInformation = null;
	buildMetadata = null;
    }

    public Version(int major, int minor, int patch) {
	this(major, minor, patch, null, null);
    }

    public Version(int major, int minor, int patchVersion,
	    String preReleaseInformation) {
	this(major, minor, patchVersion, preReleaseInformation, null);
    }

    @JsonCreator
    public Version(
	    @JsonProperty("major") int major,
	    @JsonProperty("minor") int minor,
	    @JsonProperty("patch") int patch,
	    @JsonProperty("preReleaseInformation") String preReleaseInformation,
	    @JsonProperty("buildMetadata") String buildMetadata) {
	super();
	this.major = major;
	this.minor = minor;
	this.patch = patch;
	if ((preReleaseInformation == null)
		|| (preReleaseInformation.isEmpty())) {
	    this.preReleaseInformation = null;
	} else {
	    this.preReleaseInformation = preReleaseInformation;
	}
	if ((buildMetadata == null) || (buildMetadata.isEmpty())) {
	    this.buildMetadata = null;
	} else {
	    this.buildMetadata = buildMetadata;
	}
	verify();
    }

    private void verify() {
	if (major < 0) {
	    throw new IllegalArgumentException(
		    "The major version must not be negative.");
	}
	if (minor < 0) {
	    throw new IllegalArgumentException(
		    "The minor version must not be negative.");
	}
	if (patch < 0) {
	    throw new IllegalArgumentException(
		    "The patch version must not be negative.");
	}
	if (preReleaseInformation != null) {
	    if (!preReleaseInformationPattern.matcher(preReleaseInformation)
		    .matches()) {
		throw new IllegalArgumentException(
			"Pre-release information must match the pattern '"
				+ PRE_RELEASE_INFORMATION + "', but was '"
				+ preReleaseInformation + "'.");
	    }
	}
	if (buildMetadata != null) {
	    if (!buildMetaDataPattern.matcher(buildMetadata).matches()) {
		throw new IllegalArgumentException(
			"Build-metadata information must match the pattern '"
				+ PRE_RELEASE_INFORMATION + "', but was '"
				+ buildMetadata + "'.");
	    }
	}
    }

    public int getMajor() {
	return major;
    }

    public int getMinor() {
	return minor;
    }

    public int getPatch() {
	return patch;
    }

    public String getPreReleaseInformation() {
	return preReleaseInformation;
    }

    public String getBuildMetadata() {
	return buildMetadata;
    }

    @JsonIgnore
    public boolean isStable() {
	return major > 0;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((buildMetadata == null) ? 0 : buildMetadata.hashCode());
	result = prime * result + major;
	result = prime * result + minor;
	result = prime * result + patch;
	result = prime
		* result
		+ ((preReleaseInformation == null) ? 0 : preReleaseInformation
			.hashCode());
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
	Version other = (Version) obj;
	if (buildMetadata == null) {
	    if (other.buildMetadata != null)
		return false;
	} else if (!buildMetadata.equals(other.buildMetadata))
	    return false;
	if (major != other.major)
	    return false;
	if (minor != other.minor)
	    return false;
	if (patch != other.patch)
	    return false;
	if (preReleaseInformation == null) {
	    if (other.preReleaseInformation != null)
		return false;
	} else if (!preReleaseInformation.equals(other.preReleaseInformation))
	    return false;
	return true;
    }

    @Override
    public int compareTo(Version o) {
	if (major != o.major) {
	    return Integer.compare(major, o.major);
	}
	if (minor != o.minor) {
	    return Integer.compare(minor, o.minor);
	}
	if (patch != o.patch) {
	    return Integer.compare(patch, o.patch);
	}
	if (preReleaseInformation == null) {
	    if (o.preReleaseInformation == null) {
		return 0;
	    } else {
		return 1;
	    }
	} else {
	    if (o.preReleaseInformation == null) {
		return -1;
	    }
	    return comparePreReleaseInformation(o);
	}
    }

    private int comparePreReleaseInformation(Version o) {
	// We split the strings into identifiers
	String[] myIdentifiers = preReleaseInformation.split("\\.");
	String[] otherIdentifiers = o.preReleaseInformation.split("\\.");
	// Now we check each identifier position one after another...
	for (int i = 0; i < Math.min(myIdentifiers.length,
		otherIdentifiers.length); i++) {
	    String my = myIdentifiers[i];
	    String other = otherIdentifiers[i];
	    Integer myDigits = convertToDigits(my);
	    Integer otherDigits = convertToDigits(other);
	    if (myDigits == null) {
		if (otherDigits != null) {
		    return 1;
		} else {
		    int result = my.compareTo(other);
		    if (result != 0) {
			return result;
		    }
		}
	    } else {
		if (otherDigits == null) {
		    return -1;
		} else {
		    int result = myDigits.compareTo(otherDigits);
		    if (result != 0) {
			return result;
		    }
		}
	    }
	}
	// Everything is equal, now only the length of the identifiers can
	// decide...
	if (myIdentifiers.length == otherIdentifiers.length) {
	    return 0;
	} else if (myIdentifiers.length > otherIdentifiers.length) {
	    return 1;
	} else {
	    return -1;
	}
    }

    private Integer convertToDigits(String s) {
	try {
	    return Integer.valueOf(s);
	} catch (NumberFormatException e) {
	    return null;
	}
    }

    @Override
    public String toString() {
	StringBuffer buffer = new StringBuffer();
	buffer.append(major);
	buffer.append('.');
	buffer.append(minor);
	buffer.append('.');
	buffer.append(patch);
	if (preReleaseInformation != null) {
	    buffer.append('-');
	    buffer.append(preReleaseInformation);
	}
	if (buildMetadata != null) {
	    buffer.append('+');
	    buffer.append(buildMetadata);
	}
	return buffer.toString();
    }

    @Override
    protected Version clone() {
	return new Version(major, minor, patch, preReleaseInformation,
		buildMetadata);
    }
}
