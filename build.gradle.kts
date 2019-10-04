// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()

        maven {
            url = uri(Url.jitpack)
        }

    }
    dependencies {
        classpath(BuildPlugins.gradle)
        classpath(BuildPlugins.kotlinGradlePlugin)
        // classpath(BuildPlugins.navigationSafe)
        // classpath(BuildPlugins.googleService)
        // classpath(BuildPlugins.fabric)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }

    configurations.all {
        resolutionStrategy.force(Dependencies.legacySupportV4)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
