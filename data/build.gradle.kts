import Dependencies.converterGson
import Dependencies.converterMoshi
import Dependencies.converterScalars
import Dependencies.dagger
import Dependencies.daggerAndroid
import Dependencies.daggerAndroidProcessor
import Dependencies.daggerAndroidSupport
import Dependencies.daggerCompiler
import Dependencies.kotlinStdlibJdk8
import Dependencies.loggingInterceptor
import Dependencies.mockWebserver
import Dependencies.retrofit
import Dependencies.retrofit2Rxjava2Adapter
import Dependencies.roomCompiler
import Dependencies.roomRuntime
import Dependencies.roomRxjava2
import Dependencies.rxJava
import Dependencies.espressoCore
import Dependencies.jUnit
import Dependencies.mockitoCore
import Dependencies.mockitoInline
import Dependencies.mockitoKotlin
import Dependencies.roomTesting
import Dependencies.runner

plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinKapt)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinAndroidExtensions)
}

android {
    compileSdkVersion(Android.targetSdkVersion)
    flavorDimensions("default")

    defaultConfig {
        minSdkVersion(Android.minSdkVersion)
        targetSdkVersion(Android.targetSdkVersion)
        testInstrumentationRunner = AndroidJUnit.runner
        //consumerProguardFiles = BuildType.consumerProguard
    }

    buildTypes {
        getByName(BuildType.debug) {
            isMinifyEnabled = BuildType.minifyDebug
            proguardFiles(BuildType.proguardDebug)
            isTestCoverageEnabled = true

            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3\"")
            buildConfigField("int", "DATABASE_VERSION", "0")
            buildConfigField("String", "DATABASE_NAME", "\"CleanArchDB\"")
            buildConfigField("String", "API_KEY_V3", "\"faf261888325f3e5598b206da781bfdc\"")
        }

        getByName(BuildType.release) {
            isMinifyEnabled = BuildType.minifyRelease
            proguardFiles(BuildType.proguardRelease)

            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3\"")
            buildConfigField("int", "DATABASE_VERSION", "0")
            buildConfigField("String", "DATABASE_NAME", "\"CleanArchDB\"")
            buildConfigField("String", "API_KEY_V3", "\"faf261888325f3e5598b206da781bfdc\"")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(Projects.domain))
    implementation(kotlinStdlibJdk8)

    // Dagger
    implementation(dagger)
    implementation(daggerAndroid)
    implementation(daggerAndroidSupport)
    kapt(daggerCompiler)
    kapt(daggerAndroidProcessor)

    // Rx
    implementation(rxJava)

    // Retrofit
    implementation(retrofit)
    implementation(converterGson)
    implementation(converterMoshi)
    implementation(retrofit2Rxjava2Adapter)
    implementation(loggingInterceptor)
    implementation(converterScalars)
    implementation(mockWebserver)

    // Room
    implementation(roomRuntime)
    kapt(roomCompiler)
    implementation(roomRxjava2)
    testImplementation(roomTesting)

    // Test
    testImplementation(jUnit)
    testImplementation(mockitoCore)
    testImplementation(mockitoInline)
    testImplementation(mockitoKotlin)
    androidTestImplementation(runner) {
        exclude(group = "com.google.auto.value", module = "auto-value-annotations")
    }
    androidTestImplementation(espressoCore) {
        exclude(group = "com.google.auto.value", module = "auto-value-annotations")
    }
}
