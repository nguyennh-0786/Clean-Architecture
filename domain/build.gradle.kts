plugins {
    id(Plugins.javaLibrary)
    id(Plugins.kotlin)
    id(Plugins.kotlinKapt)
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Implementation.kotlinStdlibJdk8)
    // Rx
    implementation(Implementation.rxJava)

    // Dagger
    implementation(Implementation.javaxInject)

    // Test
    testImplementation(TestImplementation.jUnit)
    testImplementation(TestImplementation.mockitoCore)
    testImplementation(TestImplementation.mockitoKotlin)
}
