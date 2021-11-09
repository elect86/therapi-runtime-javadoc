package therapi

import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPom

infix fun Project.config(pom: MavenPom) {

    pom.name.set("$group:$name")
    pom.url.set("https://github.com/dnault/$name")
    val gitUrl = "git@github.com:dnault/$name.git"
    pom.scm {
        url.set(gitUrl)
        connection.set("scm:git:$gitUrl")
        developerConnection.set("scm:git:$gitUrl")
    }
    pom.licenses {
        license {
            name.set("Apache License 2.0")
            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
        }
    }
    pom.developers {
        developer {
            name.set("David Nault")
            email.set("dnault@mac.com")
            organization.set("dnault")
            organizationUrl.set("https://github.com/dnault")
        }
    }
}