plugins {
    id(GradlePlugins.androidLib)
    kotlin(GradlePlugins.kotlinAndroid)
    kotlin(GradlePlugins.kotlinApt)
    kotlin(GradlePlugins.kotlinExt)
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(Modules.domain))
    implementation(Dependencies.kotlinStdlibJdk8)

    // Dagger
    implementation(Dependencies.dagger)
    implementation(Dependencies.daggerAndroid)
    implementation(Dependencies.daggerAndroidSupport)
    kapt(Dependencies.daggerCompiler)
    kapt(Dependencies.daggerAndroidProcessor)

    // Rx
    implementation(Dependencies.rxAndroid)
    implementation(Dependencies.rxJava)

    // Retrofit
    implementation(Dependencies.retrofit)
    implementation(Dependencies.converterGson)
    implementation(Dependencies.converterMoshi)
    implementation(Dependencies.retrofit2Rxjava2Adapter)
    implementation(Dependencies.loggingInterceptor)
    implementation(Dependencies.converterScalars)
    implementation(Dependencies.mockWebserver)

    // Room
    implementation(Dependencies.roomRuntime)
    kapt(Dependencies.roomCompiler)
    implementation(Dependencies.roomRxjava2)
    testImplementation(Dependencies.roomTesting)

    // Test
    testImplementation(Dependencies.jUnit)
    testImplementation(Dependencies.mockitoCore)
    testImplementation(Dependencies.mockitoInline)
    testImplementation(Dependencies.mockitoKotlin)
    androidTestImplementation(Dependencies.runner) {
        exclude(group = "com.google.auto.value", module = "auto-value-annotations")
    }
    androidTestImplementation(Dependencies.espressoCore) {
        exclude(group = "com.google.auto.value", module = "auto-value-annotations")
    }
}
