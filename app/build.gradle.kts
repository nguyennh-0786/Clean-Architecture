plugins {
    id(Plugins.application)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinAndroidExtensions)
}

android {
    compileSdkVersion(Versions.compile_sdk)
    buildToolsVersion(Versions.buildToolsVersion)

    defaultConfig {
        applicationId = Android.applicationId
        minSdkVersion(Android.minSdkVersion)
        targetSdkVersion(Android.targetSdkVersion)
        versionCode = Android.versionCode
        versionName = Android.versionName
        testInstrumentationRunner = AndroidJUnit.runner
    }
    buildTypes {
        getByName(BuildType.release) {
            isMinifyEnabled = BuildType.minifyRelease
            proguardFiles(BuildType.proguardRelease)
        }

        getByName(BuildType.debug) {
            isMinifyEnabled = BuildType.minifyDebug
            proguardFiles(BuildType.proguardDebug)
            isTestCoverageEnabled = true
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Implementation.kotlinStdlibJdk8)
    implementation(Implementation.appcompat)
    implementation(Implementation.coreKtx)
    implementation(Implementation.constraintlayout)
    testImplementation(TestImplementation.jUnit)
    androidTestImplementation(TestImplementation.runner)
    androidTestImplementation(TestImplementation.espressoCore)
}
