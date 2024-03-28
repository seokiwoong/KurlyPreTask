plugins {
    alias(libs.plugins.kurly.android.library)
    alias(libs.plugins.kurly.android.hilt)
}

android {
    namespace = "com.kurly.pretask.core.mockserver"

    defaultConfig {
        testInstrumentationRunner = "com.kurly.android.mockserver.CustomTestRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation( "com.squareup.okhttp3:okhttp:4.12.0")
    implementation( "com.google.code.gson:gson:2.10.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    ksp(libs.hilt.compiler)
    kspTest(libs.hilt.compiler)
    testImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.hilt.android.testing)
}