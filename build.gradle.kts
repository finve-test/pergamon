plugins {
    java
    id("io.quarkus")
}

import java.io.ByteArrayOutputStream

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

sourceSets {
    val integration by creating {
	    java.srcDir("src/integration-test/java")
    	resources.srcDir("src/integration-test/resources")
        compileClasspath += sourceSets["main"].output + sourceSets["test"].output
        runtimeClasspath += sourceSets["main"].output + sourceSets["test"].output
    }
}

configurations {
    val integrationImplementation by getting {
        extendsFrom(configurations.testImplementation.get())
    }
    val integrationRuntimeOnly by getting {
        extendsFrom(configurations.testRuntimeOnly.get())
    }
}

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-rest-jackson")
    implementation("io.quarkus:quarkus-hibernate-orm-panache")
    implementation("io.quarkus:quarkus-jdbc-postgresql")

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
	testRuntimeOnly("org.postgresql:postgresql")	

    "integrationImplementation"("io.quarkus:quarkus-junit5")
	"integrationImplementation"("org.testcontainers:testcontainers:1.19.8")
	"integrationImplementation"("org.testcontainers:junit-jupiter:1.19.8")
	"integrationImplementation"("org.testcontainers:postgresql:1.19.8")
	"integrationImplementation"("io.rest-assured:rest-assured:5.5.0")
}

group = "dev.serdroid"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

val integrationTest by tasks.creating(Test::class) {
    description = "Runs the integration tests"
    group = "verification"
	// start podman.service before integrationTest
	// systemctl start --user podman.service
    val outputStream = ByteArrayOutputStream()
    project.exec {
    	commandLine("id", "-u")
        standardOutput = outputStream
    }
    val uid = outputStream.toString().trim()
    environment("DOCKER_HOST", "unix:///run/user/$uid/podman/podman.sock")
    
    testClassesDirs = sourceSets["integration"].output.classesDirs
    classpath = sourceSets["integration"].runtimeClasspath
    mustRunAfter(tasks["test"])
}
