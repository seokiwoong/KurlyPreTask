plugins {
    alias(libs.plugins.kt.android.library)
    alias(libs.plugins.kt.android.library.compose)
}

android {
    defaultConfig {

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    namespace = "com.kt.naviagent.core.designsystem"

}

dependencies {
    api(projects.core.domain)

    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.material)
    implementation(libs.coil.kt.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.core)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}