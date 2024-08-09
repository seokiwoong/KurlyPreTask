plugins {
    alias(libs.plugins.kt.android.library)
    alias(libs.plugins.kt.android.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.kt.naviagent.core.network"

    buildFeatures {
        buildConfig = true
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    defaultConfig {
        testInstrumentationRunner = "com.kt.naviagent.network.CustomTestRunner"
    }
}

dependencies {
    api(libs.kotlinx.datetime)

    implementation(libs.coil.kt)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    ksp(libs.hilt.compiler)
    kspTest(libs.hilt.compiler)

    testImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.hilt.android.testing)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    testImplementation(libs.kotlinx.coroutines.test)
}