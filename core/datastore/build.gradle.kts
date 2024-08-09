plugins {
    alias(libs.plugins.kt.android.library)
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.kt.naviagent.core.datastore"

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
    implementation(libs.androidx.dataStore.preferences)
    implementation(libs.protobuf.kotlin.lite)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)

}