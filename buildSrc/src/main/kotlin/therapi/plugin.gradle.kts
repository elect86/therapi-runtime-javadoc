package therapi

import java.util.Date

plugins {
    id("com.github.johnrengelman.shadow")
    id("com.jfrog.bintray")
    `java-library`
    `maven-publish`
}

group = "com.github.therapi"
version = "0.12.0"

repositories {
    jcenter()
}

dependencies {
    implementation("com.eclipsesource.minimal-json:minimal-json:0.9.5")
    testImplementation("junit:junit:4.12")
}



java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
    withJavadocJar()
    withSourcesJar()
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        mergeServiceFiles()
        relocate("com.eclipsesource", "com.github.therapi.runtimejavadoc.repack.com.eclipsesource")
    }

    //
    //    task install {
    //        dependsOn(publishToMavenLocal)
    //    }

    bintrayUpload {
        doFirst {
            if ("SNAPSHOT" in version.toString())
                throw RuntimeException("Must not upload snapshots to bintray (current version is $version) -- create and tag a release version first!")
        }
    }

    val gitProjectName = name

    bintray {
        if (project.hasProperty("bintrayUsername"))
            user = project.properties["bintrayUsername"].toString()

        if (project.hasProperty("bintrayApiKey"))
            key = project.properties["bintrayApiKey"].toString()

        setPublications("mavenJava") // When uploading Maven-based publication files

        dryRun = false  //false //Whether to run this as dry-run, without deploying
        publish = false //true //If version should be auto published after an upload

        pkg = PackageConfig().apply {
            repo = "maven"
            //  userOrg = 'myorg' //An optional organization name when the repo belongs to one of the user's orgs
            name = project.name
            desc = project.description
            websiteUrl = "https://github.com/dnault/$gitProjectName"
            issueTrackerUrl = "https://github.com/dnault/$gitProjectName/issues"
            vcsUrl = "https://github.com/dnault/$gitProjectName.git"
            setLicenses("Apache-2.0")
            setLabels("runtime", "javadoc")
            publicDownloadNumbers = false //true
            //attributes= ['a': ['ay1', 'ay2'], 'b': ['bee'], c: 'cee'] //Optional package-level attributes
            //Optional version descriptor
            version = VersionConfig().apply {
                val version = project.version.toString()
                name = version //'1.3-Final' //Bintray logical version name
                //desc = 'optional, version-specific description'
                released = Date().toString()
                //'optional, date of the version release' //2 possible values: date in the format of 'yyyy-MM-dd'T'HH:mm:ss.SSSZZ' OR a java.util.Date instance
                vcsTag = version //'1.3.0'
                //            attributes = ['gradle-plugin': 'com.use.less:com.use.less.gradle:gradle-useless-plugin'] //Optional version-level attributes
                gpg = GpgConfig().apply {
                    //              sign = true //Determines whether to GPG sign the files. The default is false
                    //            passphrase = 'passphrase' //Optional. The passphrase for GPG signing'
                }
                mavenCentralSync = MavenCentralSyncConfig().apply {
                    sync = false
                    //true //Optional (true by default). Determines whether to sync the version to Maven Central.
                    user = "userToken" //OSS user token
                    password = "paasword" //OSS user password
                    close = "0"
                    //'1' //Optional property. By default the staging repository is closed and artifacts are released to Maven Central. You can optionally turn this behaviour off (by puting 0 as value) and release the version manually.
                }
            }
        }
    }
}
