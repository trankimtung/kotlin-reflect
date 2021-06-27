import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    signing
    `maven-publish`
}

group = "com.trankimtung.kotlin"
version = "0.0.1.A1"

sourceSets.main {
    java.srcDirs("src/main/java", "src/main/kotlin")
}

sourceSets.test {
    java.srcDirs("src/test/java", "src/test/kotlin")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=all")
        jvmTarget = "11"
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    withJavadocJar()
    withSourcesJar()
}

tasks.getByName<Jar>("jar") {
    classifier = ""
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}

tasks.withType<GenerateModuleMetadata> {
    enabled = false
}

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = property("artifact.id") as String
            from(components["java"])
            pom {
                name.set(property("project.name") as String)
                description.set(property("project.desc") as String)
                url.set(property("project.url") as String)
                licenses {
                    license {
                        name.set(property("license") as String)
                        url.set(property("license.url") as String)
                    }
                }
                developers {
                    developer {
                        id.set(property("author.id") as String)
                        name.set(property("author.name") as String)
                        email.set(property("author.email") as String)
                    }
                }
                scm {
                    connection.set(property("scm.connection") as String)
                    developerConnection.set(property("scm.developer.connection") as String)
                    url.set(property("scm.url") as String)
                }
            }
        }
    }
    repositories {
        maven {
            val releasesRepoUrl = uri(property("releases.repo.url") as String)
            val snapshotsRepoUrl = uri(property("snapshots.repo.url") as String)
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials {
                username = property("sonatype.username") as? String?
                password = property("sonatype.password") as? String?
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}

repositories {
    mavenCentral()
}

dependencies {
    api(kotlin("reflect"))
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
    testImplementation("org.mockito:mockito-core:3.9.0")
    testImplementation("org.mockito:mockito-junit-jupiter:3.9.0")
    testImplementation("org.assertj:assertj-core:3.19.0")
    testImplementation("org.hamcrest:hamcrest:2.2")
}
