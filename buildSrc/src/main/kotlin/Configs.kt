object Versions {
    const val jvmTarget = "1.8"
    const val buildTools = "29.0.2"
    internal const val gradle = "3.5.0"
    internal const val navigationSafe = "2.1.0-alpha05"
    internal const val googleService = "4.3.0"
    internal const val fabric = "1.31.0"
    internal const val material = "1.1.0-alpha02"
    internal const val lifecycle = "2.0.0"
    internal const val junitExt = "1.1.0"
    internal const val testRules = "1.1.1"
    internal const val kotlin = "1.3.50"
    internal const val legacySupport = "1.0.0"
    internal const val dagger = "2.16"
    internal const val appcompat = "1.1.0"
    internal const val coreKtx = "1.1.0"
    internal const val constraintLayout = "1.1.3"

    // Retrofit
    internal const val retrofit = "2.4.0"
    internal const val retrofit2RxJava2Adapter = "1.0.0"
    internal const val okHttp = "3.11.0"

    // Rx version
    internal const val rxJava = "2.2.3"
    internal const val rxAndroid = "2.1.0"

    // Room version
    internal const val room = "2.0.0"

    //Unit Testing
    internal const val junit = "4.12"
    internal const val mockito = "2.18.3"
    internal const val mockitoKotlin = "2.0.0"

    //Acceptance Testing
    internal const val runner = "1.1.1-alpha01"
    internal const val espresso = "3.1.1-alpha01"
}

object Modules {
    const val app = ":app"
    const val data = ":data"
    const val domain = ":domain"
    const val shared = ":shared"
}

object Dependencies {

    const val kotlinStdlibJdk8 =
        "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val javaxInject = "javax.inject:javax.inject:1@jar"
    const val testRules = "androidx.test:rules:${Versions.testRules}"
    const val runner = "androidx.test:runner:${Versions.runner}"

    // Rx
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"

    // Dagger
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val daggerAndroidProcessor =
        "com.google.dagger:dagger-android-processor:${Versions.dagger}"

    // Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val converterMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val retrofit2Rxjava2Adapter =
        "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:${Versions.retrofit2RxJava2Adapter}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val converterScalars = "com.squareup.retrofit2:converter-scalars:${Versions.retrofit}"
    const val mockWebserver = "com.squareup.okhttp3:mockwebserver:${Versions.okHttp}"

    // Room DB
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomRxjava2 = "androidx.room:room-rxjava2:${Versions.room}"
    const val roomTesting = "androidx.room:room-testing:${Versions.room}"

    // Android library
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
    const val lifecycleReactivestreamsKtx =
        "androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.lifecycle}"
    const val coreTesting = "androidx.arch.core:core-testing:${Versions.lifecycle}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val legacySupportV4 = "androidx.legacy:legacy-support-v4:${Versions.legacySupport}"
    const val legacySupportCoreUtils = "androidx.legacy:legacy-support-core-utils:${Versions.legacySupport}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.legacySupport}"

    // Junit
    const val jUnit = "junit:junit:${Versions.junit}"
    const val junitExt = "androidx.test.ext:junit:${Versions.junitExt}"
    const val truthExt = "androidx.test.ext:truth:${Versions.junitExt}"

    // Mockito
    const val mockitoCore = "org.mockito:mockito-core:${Versions.mockito}"
    const val mockitoKotlin =
        "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
    const val mockitoInline = "org.mockito:mockito-core:${Versions.mockito}"

    // Espresso
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
    const val espressoIntents = "androidx.test.espresso:espresso-intents:${Versions.espresso}"
}

object GradlePlugins {
    const val android = "com.android.application"
    const val kotlin = "kotlin"
    const val kotlinAndroid = "android"
    const val kotlinExt = "android.extensions"
    const val kotlinApt = "kapt"
    const val javaLib = "java-library"
    const val androidLib = "com.android.library"
    const val navigationSafe = "androidx.navigation.safeargs"
    const val navigationSafeKotlin = "androidx.navigation.safeargs.kotlin"
    const val fabric = "io.fabric"
    const val playService = "com.google.gms.google-services"
}

object Android {
    // Manifest version
    private const val versionMajor = 1
    private const val versionMinor = 0
    private const val versionPatch = 0

    const val minSdkVersion = 19
    const val targetSdkVersion = 29
    const val applicationId = "com.louis.cleanarchdemo"
    const val versionCode = versionMajor * 10000 + versionMinor * 100 + versionPatch
    const val versionName = "$versionMajor.$versionMinor.$versionPatch"
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

object BuildPlugins {
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val navigationSafe =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationSafe}"
    const val googleService = "com.google.gms:google-services:${Versions.googleService}"
    const val fabric = "io.fabric.tools:gradle:${Versions.fabric}"
}

object Flavors {
    const val develop = "develop"
    const val staging = "staging"
    const val production = "production"
}

object Commons {
    const val appName = "CleanArchDemo"
    const val dev = "dev"
    const val devPrefix = ".dev"
    const val devName = "CleanArchDemo-DEV"
    const val prod = "prod"
    const val prodPrefix = ".prod"
    const val prodName = "CleanArchDemo"
}

object Url {
    const val fabric = "https://maven.fabric.io/public"
    const val sonatype = "https://oss.sonatype.org/content/repositories/snapshots"
    const val jitpack = "https://jitpack.io"
}