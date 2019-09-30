import Android.minSdkVersion
import Android.targetSdkVersion
import Implementation.converterGson
import Implementation.converterMoshi
import Implementation.converterScalars
import Implementation.dagger
import Implementation.daggerAndroid
import Implementation.daggerAndroidProcessor
import Implementation.daggerAndroidSupport
import Implementation.daggerCompiler
import Implementation.kotlinStdlibJdk8
import Implementation.loggingInterceptor
import Implementation.mockWebserver
import Implementation.retrofit
import Implementation.retrofit2Rxjava2Adapter
import Implementation.roomCompiler
import Implementation.roomRuntime
import Implementation.roomRxjava2
import Implementation.rxJava
import TestImplementation.espressoCore
import TestImplementation.jUnit
import TestImplementation.mockitoCore
import TestImplementation.mockitoInline
import TestImplementation.mockitoKotlin
import TestImplementation.roomTesting
import TestImplementation.runner

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
