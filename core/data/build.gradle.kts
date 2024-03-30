plugins {
    alias(libs.plugins.kurly.android.library)
    alias(libs.plugins.kurly.android.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.kurly.pretask.core.data"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    api(projects.core.network)
    api(projects.core.datastore)
    api(libs.retrofit.core)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.paging.runtime.ktx)

    implementation(libs.protobuf.kotlin.lite)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}