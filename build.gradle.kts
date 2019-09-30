// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath(Implementation.gradle)
        classpath(Implementation.kotlinGradlePlugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }

    configurations.all {
        resolutionStrategy.force(Implementation.legacySupportV4)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
