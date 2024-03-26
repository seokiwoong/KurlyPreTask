plugins {
    alias(libs.plugins.kurly.android.library)
    alias(libs.plugins.kurly.android.hilt)
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.kurly.pretask.core.datastore"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

}


protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}


androidComponents.beforeVariants {
    android.sourceSets.getByName(it.name) {
        java.srcDir(layout.buildDirectory.get().asFile.resolve("generated/source/proto/${it.name}/java"))
        kotlin.srcDir(layout.buildDirectory.get().asFile.resolve("generated/source/proto/${it.name}/kotlin"))
    }
}


dependencies {
    api(libs.androidx.dataStore.core)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.dataStore.preferences)
    implementation(libs.protobuf.kotlin.lite)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)

}