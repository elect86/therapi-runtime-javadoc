import therapi.config

plugins {
    therapi.plugin
}

dependencies {
    implementation(files("${System.getProperties()["java.home"]}/../lib/tools.jar"))
    shadow(project(":therapi-runtime-javadoc"))

    testImplementation(project(":therapi-runtime-javadoc"))
    testImplementation("com.google.testing.compile:compile-testing:0.11")
}

tasks.withType<JavaCompile> {
    // disable annotation processing
    options.compilerArgs = options.compilerArgs + "-proc:none"
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            project.shadow.component(this)

            pom.withXml {
                asNode().appendNode("description", project.description)

                project config pom
//                asNode().children().last() + project.ext.pomConfig

                // Workaround for https://github.com/johnrengelman/shadow/issues/334
                asNode().appendNode("dependencies").appendNode("dependency").apply {
                        appendNode("groupId", project.group)
                        appendNode("artifactId", "therapi-runtime-javadoc")
                        appendNode("version", project.version)
                        appendNode("scope", "compile")
                    }
            }
        }
    }
}