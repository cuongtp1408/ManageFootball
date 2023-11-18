buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath ("com.android.tools.build:gradle:7.1.3")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.45")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }


}

plugins {
    id ("com.android.application") version "7.3.0" apply false
    id ("com.android.library") version "7.3.0" apply false
    id ("org.jetbrains.kotlin.android") version "1.7.20" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id  ("org.jetbrains.kotlin.kapt") version "1.6.10" apply false
}

tasks.register("clean").configure {
    delete(rootProject.buildDir)
}