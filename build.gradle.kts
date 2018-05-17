import org.apache.tools.ant.filters.ReplaceTokens
import org.apache.tools.ant.types.resources.FileResource
import org.gradle.kotlin.dsl.*
import org.springframework.boot.gradle.run.BootRunTask
import java.io.File

plugins {
    java
    idea
    id("org.springframework.boot") version "1.5.3.RELEASE"
}

allprojects {
    group = "cn.binux"
    version = "0.0.1"
    apply {
        plugin("java")
        plugin("idea")
    }

    idea {
        module {
            outputDir = java.sourceSets["main"].java.outputDir
            testOutputDir = java.sourceSets["test"].java.outputDir
        }
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

subprojects {
    apply {
        plugin("application")
        plugin("org.springframework.boot")
        plugin(SubDocker::class.java)
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:Dalston.RELEASE")
        }

        dependencies {
            dependency("com.github.danielwegener:logback-kafka-appender:0.1.0")
            dependency("org.mybatis.spring.boot:mybatis-spring-boot-starter:1.2.0")
            dependency("com.github.pagehelper:pagehelper-spring-boot-starter:1.1.0")
            dependency("com.roncoo:roncoo-spring-boot-starter-druid:1.0.5")
            dependency("org.projectlombok:lombok:1.16.16")
            dependency("org.webjars.npm:apollo-client:1.0.0-rc.2")
            dependency("com.piggsoft:beetl-spring-boot-starter:0.0.3")
            dependency("de.codecentric:spring-boot-admin-server:1.5.0")
            dependency("de.codecentric:spring-boot-admin-server-ui:1.5.0")
            dependency("org.apache.commons:commons-lang3:3.3.2")
            dependency("commons-lang:commons-lang:2.6")
            dependency("com.alibaba:fastjson:1.2.28")
            dependency("net.oschina.zcx7878:fastdfs-client-java:1.27.0.0")
            dependency("io.springfox:springfox-swagger-ui:2.6.1")
            dependency("io.springfox:springfox-swagger2:2.6.1")
            dependency("org.springframework.boot:spring-boot-starter-redis:1.4.7.RELEASE")
            dependency("commons-fileupload:commons-fileupload:1.3.1")
        }
    }

    repositories {
        mavenLocal()
        jcenter()
    }

    dependencies {
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
        compileOnly("org.springframework.boot:spring-boot-configuration-processor")
        testCompile("org.springframework.boot:spring-boot-starter-test")
//        testCompile("commons-logging:commons-logging")
    }

    withConvention(ApplicationPluginConvention::class) {
        applicationDefaultJvmArgs += "-Dfile.encoding=UTF-8"
    }

    tasks {
        withType(ProcessResources::class.java) {
            filesMatching(setOf("**/*.yml", "**/*.properties", "**/*.xml")) {
                filter(mapOf("propertiesResource" to FileResource(rootProject.file("gradle.properties"))), ReplaceTokens::class.java)
            }
        }

        "compileJava"(JavaCompile::class) {
            dependsOn("processResources")
        }

        withType(BootRunTask::class.java) {
            System.getProperties().forEach { t, u ->
                systemProperty(t as String, u)
            }
        }

    }
}
apply { plugin(Docker::class.java) }

description = "xbin-store-cloud"