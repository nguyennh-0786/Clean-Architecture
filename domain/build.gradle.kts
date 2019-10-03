plugins {
    id(GradlePlugins.javaLib)
    id(GradlePlugins.kotlin)
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.kotlinStdlibJdk8)
    // Rx
    implementation(Dependencies.rxJava)

    // Dagger
    implementation(Dependencies.javaxInject)

    // Test
    testImplementation(Dependencies.jUnit)
    testImplementation(Dependencies.mockitoCore)
    testImplementation(Dependencies.mockitoKotlin)
}
