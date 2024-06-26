plugins {
    alias(libs.plugins.kurly.android.library)
    alias(libs.plugins.kurly.android.library.compose)
    alias(libs.plugins.kurly.android.hilt)

}

android {
    namespace = "com.kurly.feature.main"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.designsystem)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging.runtime.ktx)

    testImplementation(libs.androidx.paging.common.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}