import Versions.appcompatVersion
import Versions.constraintlayoutVersion
import Versions.coreKtxVersion
import Versions.dagger_version
import Versions.espresso_version
import Versions.gradle_version
import Versions.junit_version
import Versions.kotlin_version
import Versions.legacy_support_version
import Versions.mockito_kotlin_version
import Versions.mockito_version
import Versions.okhttp_version
import Versions.retrofit2_rxjava2_adapter_version
import Versions.retrofit_version
import Versions.room_version
import Versions.runner_version
import Versions.rxjava_version

object Versions {
    const val compile_sdk = 29
    const val buildToolsVersion = "29.0.2"
    internal const val gradle_version = "3.5.0"

    internal const val kotlin_version = "1.3.50"
    internal const val legacy_support_version = "1.0.0"
    internal const val dagger_version = "2.16"
    internal const val okhttp_version = "3.11.0"

    internal const val appcompatVersion = "1.1.0"
    internal const val coreKtxVersion = "1.1.0"
    internal const val constraintlayoutVersion = "1.1.3"

    // Retrofit
    internal const val retrofit_version = "2.4.0"
    internal const val retrofit2_rxjava2_adapter_version = "1.0.0"

    // Rx version
    internal const val rxjava_version = "2.2.3"
    internal const val rxandroid_version = "2.1.0"

    // Room version
    internal const val room_version = "2.0.0"

    //Unit Testing
    internal const val junit_version = "4.12"
    internal const val mockito_version = "2.18.3"
    internal const val mockito_kotlin_version = "2.0.0"

    //Acceptance Testing
    internal const val runner_version = "1.1.1-alpha01"
    internal const val espresso_version = "3.1.1-alpha01"
}

object Projects {
    const val app = ":app"
    const val data = ":data"
    const val domain = ":domain"
}

object Dependencies {
    const val legacySupportV4 = "androidx.legacy:legacy-support-v4:$legacy_support_version"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    const val gradle = "com.android.tools.build:gradle:$gradle_version"
    const val kotlinStdlibJdk8 =
        "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"
    const val rxJava = "io.reactivex.rxjava2:rxjava:$rxjava_version"
    const val javaxInject = "javax.inject:javax.inject:1@jar"
    const val dagger = "com.google.dagger:dagger:$dagger_version"
    const val daggerAndroid = "com.google.dagger:dagger-android:$dagger_version"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:$dagger_version"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:$dagger_version"
    const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:$dagger_version"
    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofit_version"

    const val converterGson = "com.squareup.retrofit2:converter-gson:$retrofit_version"
    const val converterMoshi = "com.squareup.retrofit2:converter-moshi:$retrofit_version"
    const val retrofit2Rxjava2Adapter = "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:$retrofit2_rxjava2_adapter_version"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$okhttp_version"
    const val converterScalars = "com.squareup.retrofit2:converter-scalars:$retrofit_version"
    const val mockWebserver = "com.squareup.okhttp3:mockwebserver:$okhttp_version"
    const val roomRuntime = "androidx.room:room-runtime:$room_version"
    const val roomCompiler = "androidx.room:room-compiler:$room_version"
    const val roomRxjava2 = "androidx.room:room-rxjava2:$room_version"
    const val appcompat = "androidx.appcompat:appcompat:$appcompatVersion"
    const val coreKtx = "androidx.core:core-ktx:$coreKtxVersion"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:$constraintlayoutVersion"

    const val jUnit = "junit:junit:$junit_version"
    const val mockitoCore = "org.mockito:mockito-core:$mockito_version"
    const val mockitoKotlin =
        "com.nhaarman.mockitokotlin2:mockito-kotlin:$mockito_kotlin_version"
    const val roomTesting = "androidx.room:room-testing:$room_version"
    const val mockitoInline = "org.mockito:mockito-core:$mockito_version"
    const val runner = "androidx.test:runner:$runner_version"
    const val espressoCore = "androidx.test.espresso:espresso-core:$espresso_version"
}

object Plugins {
    const val javaLibrary = "java-library"
    const val kotlinKapt = "kotlin-kapt"
    const val kotlin = "kotlin"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val application = "com.android.application"
}

object Android {
    const val minSdkVersion = 19
    const val targetSdkVersion = 29
    const val applicationId = "com.louis.cleanarch"
    const val versionCode = 1
    const val versionName = "1.0.1"
}

object AndroidJUnit {
    const val runner = "androidx.test.runner.AndroidJUnitRunner"
}

object BuildType {
    const val debug = "debug"
    const val release = "release"

    const val minifyRelease = false
    const val proguardRelease = "proguard-rules.pro"

    const val minifyDebug = false
    const val proguardDebug = "proguard-rules.pro"
}