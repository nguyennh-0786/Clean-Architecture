plugins {
    id(GradlePlugins.android)
    kotlin(GradlePlugins.kotlinAndroid)
    kotlin(GradlePlugins.kotlinExt)
}
android {
    compileSdkVersion(Android.targetSdkVersion)
    buildToolsVersion(Versions.buildTools)


    defaultConfig {
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
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.kotlinStdlibJdk8)
    implementation(Dependencies.coreKtx)

    // Android library
    implementation(Dependencies.appcompat)
    implementation(Dependencies.legacySupportCoreUtils)
    implementation(Dependencies.material)
    implementation(Dependencies.recyclerview)

    testImplementation(Dependencies.jUnit)
    testImplementation(Dependencies.runner)
    androidTestImplementation(Dependencies.espressoCore)
}
