plugins {
    alias(libs.plugins.kurly.android.library)
    alias(libs.plugins.kurly.android.library.compose)
}

android {
    defaultConfig {

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    namespace = "com.kurly.pretask.core.designsystem"

}

dependencies {
    api(projects.core.domain)

    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.runtime)
    implementation(libs.coil.kt.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.core)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}