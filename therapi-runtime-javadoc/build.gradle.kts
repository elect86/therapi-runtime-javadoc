import therapi.config

plugins {
    therapi.plugin
}


publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            project.shadow.component(this)

            pom.withXml {
                asNode().appendNode("description", project.description)
            }
//            project config pom
        }
    }
}
