dsutils
=======

_A Java library of general utility classes_

---

Basically anytime I write a bit of generally useful code (whether having to do with string processing, collections, basic math, etc. etc.) I put it in here; thus, all of my other packages depend on this one.

This project has been accumulating since 2003 or so, and some of the functionality has slowly been replaced over the years by new features in the JDK and by the [Apache Commons](http://commons.apache.org) packages.

Note that the nature of this kind of code is that some of it is found on the Internet and pasted into this package for convenience.  I've tried to keep the attributions, copyright notices, and licenses straight, but I know I need to doublecheck those--so I can't make any guarantee as to non-GPL code and such.

Documentation
-------------

 * [API docs](http://davidsoergel.github.io/dsutils/)

Download
--------

[Maven](http://maven.apache.org/) is by far the easiest way to make use of dsutils.  Just add these to your pom.xml:
```xml
<repositories>
	<repository>
		<id>dev.davidsoergel.com releases</id>
		<url>http://dev.davidsoergel.com/nexus/content/repositories/releases</url>
		<snapshots>
			<enabled>false</enabled>
		</snapshots>
	</repository>
	<repository>
		<id>dev.davidsoergel.com snapshots</id>
		<url>http://dev.davidsoergel.com/nexus/content/repositories/snapshots</url>
		<releases>
			<enabled>false</enabled>
		</releases>
	</repository>
</repositories>

<dependencies>
	<dependency>
		<groupId>com.davidsoergel</groupId>
		<artifactId>dsutils</artifactId>
		<version>1.051</version>
	</dependency>
</dependencies>
```

If you really want just the jar, you can get the [latest release](http://dev.davidsoergel.com/nexus/content/repositories/releases/com/davidsoergel/dsutils/) from the Maven repo; or get the [latest stable build](http://dev.davidsoergel.com/jenkins/job/dsutils/lastStableBuild/com.davidsoergel$dsutils/) from the build server.


