import com.android.build.gradle.api.ApplicationVariant
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

plugins {
    id(GradlePlugins.android)
    kotlin(GradlePlugins.kotlinAndroid)
    kotlin(GradlePlugins.kotlinApt)
    kotlin(GradlePlugins.kotlinExt)
}

//apply {
//plugin(GradlePlugins.navigationSafeKotlin)
//plugin(GradlePlugins.fabric)
//plugin(GradlePlugins.playService)
//from("../ktlint.gradle")
//from("jacoco.gradle")
//}

android {
    compileSdkVersion(Android.targetSdkVersion)
    buildToolsVersion(Versions.buildTools)

    defaultConfig {
        applicationId = Android.applicationId
        minSdkVersion(Android.minSdkVersion)
        targetSdkVersion(Android.targetSdkVersion)

        versionCode = Android.versionCode
        versionName = Android.versionName

        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = AndroidJUnit.runner
        multiDexEnabled = true

        javaCompileOptions {
            annotationProcessorOptions {
                arguments = mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }
    }

    applicationVariants.all(object : Action<ApplicationVariant> {
        override fun execute(variant: ApplicationVariant) {
            variant.resValue("string", "versionInfo", variant.versionName ?: "")
        }

    })

    signingConfigs {
        getByName(BuildType.debug) {
            storeFile = rootProject.file("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
        create(BuildType.release) {
            storeFile = rootProject.file("release.keystore")
            storePassword = System.getenv("ANDROID_KEYSTORE_PASSWORD")
            keyAlias = System.getenv("ANDROID_KEYSTORE_ALIAS")
            keyPassword = System.getenv("ANDROID_KEYSTORE_PRIVATE_KEY_PASSWORD")
        }
    }

    buildTypes {
        getByName(BuildType.debug) {
            versionNameSuffix = Commons.dev
            applicationIdSuffix = Commons.devPrefix
            manifestPlaceholders = mapOf("applicationName" to Commons.devName)
            signingConfig = signingConfigs.getByName(BuildType.debug)

            isMinifyEnabled = BuildType.minifyDebug
            proguardFiles(BuildType.proguardDebug)
            isTestCoverageEnabled = true

            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3\"")
        }

        getByName(BuildType.release) {
            manifestPlaceholders = mapOf("applicationName" to Commons.devName)
            signingConfig = signingConfigs.getByName(BuildType.release)

            isMinifyEnabled = BuildType.minifyRelease
            isDebuggable = false
            isZipAlignEnabled = true
            proguardFiles(BuildType.proguardRelease)

            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3\"")
        }
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        val options = this as KotlinJvmOptions
        options.jvmTarget = Versions.jvmTarget
    }

    //dataBinding {
    //isEnabled = true
    //}

    androidExtensions {
        isExperimental = true
    }
}

kapt {
    useBuildCache = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(Modules.data))
    implementation(project(Modules.domain))

    implementation(Dependencies.kotlinStdlibJdk8)
    implementation(Dependencies.coreKtx)

    // Android library
    implementation(Dependencies.appcompat)
    implementation(Dependencies.constraintlayout)
    implementation(Dependencies.material)
    implementation(Dependencies.legacySupportV4)
    implementation(Dependencies.lifecycleExtensions)
    kapt(Dependencies.lifecycleCompiler)
    implementation(Dependencies.lifecycleReactivestreamsKtx)
    testImplementation(Dependencies.coreTesting)

    // Dagger
    implementation(Dependencies.dagger)
    implementation(Dependencies.daggerAndroid)
    implementation(Dependencies.daggerAndroidSupport)
    kapt(Dependencies.daggerCompiler)
    kapt(Dependencies.daggerAndroidProcessor)

    // Rx
    implementation(Dependencies.rxAndroid)
    implementation(Dependencies.rxJava)

    // Test
    testImplementation(Dependencies.jUnit)
    testImplementation(Dependencies.mockitoCore)
    testImplementation(Dependencies.mockitoInline)
    testImplementation(Dependencies.mockitoKotlin)
    androidTestImplementation(Dependencies.runner) {
        exclude(group = "com.google.auto.value", module = "auto-value-annotations")
    }
    androidTestImplementation(Dependencies.junitExt) {
        exclude(group = "com.google.auto.value", module = "auto-value-annotations")
    }
    androidTestImplementation(Dependencies.truthExt) {
        exclude(group = "com.google.auto.value", module = "auto-value-annotations")
    }

    androidTestImplementation(Dependencies.espressoCore)
    androidTestImplementation(Dependencies.espressoContrib) {
        exclude(group = "com.google.auto.value", module = "auto-value-annotations")
    }
    androidTestImplementation(Dependencies.espressoIntents) {
        exclude(group = "com.google.auto.value", module = "auto-value-annotations")
    }
    androidTestImplementation(Dependencies.testRules) {
        exclude(group = "com.google.auto.value", module = "auto-value-annotations")
    }
}
