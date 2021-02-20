import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.2"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.21"
	kotlin("plugin.spring") version "1.4.21"
	groovy
}

group = "com.mackokodzi"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
	implementation("io.ktor:ktor-client-apache:1.5.0")
	implementation("io.ktor:ktor-client-core:1.5.0")
	implementation("io.ktor:ktor-client-jackson:1.5.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.xebialabs.restito:restito:0.9.3")
	testImplementation("io.rest-assured:rest-assured:4.2.0")
	testImplementation("io.rest-assured:xml-path:4.2.0")
	testImplementation("io.rest-assured:json-path:4.2.0")
	testImplementation("io.rest-assured:kotlin-extensions:4.2.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

sourceSets {
	create("integration") {
		withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
			kotlin.srcDir("src/integration/kotlin")
			resources.srcDir("src/integration/resources")
			compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
			runtimeClasspath += output + compileClasspath + sourceSets["test"].runtimeClasspath
		}
	}
}

val integration = task<Test>("integration") {
	description = "Runs the integration tests"
	group = "verification"
	testClassesDirs = sourceSets["integration"].output.classesDirs
	classpath = sourceSets["integration"].runtimeClasspath
	mustRunAfter(tasks["test"])
}

tasks.check {
	dependsOn(integration)
}
