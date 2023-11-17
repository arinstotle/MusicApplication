// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
//    id("com.google.dagger.hilt.android") version "2.48" apply false
}

buildscript {

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        //noinspection AndroidGradlePluginVersion
        classpath("com.android.tools.build:gradle:7.1.0-beta03")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}