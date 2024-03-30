plugins {
    `kotlin-dsl`
    alias(libs.plugins.jetbrainsKotlinJvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}


gradlePlugin {
    plugins {
        register("AndroidApplicationPlugin") {
            id = "kurly.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("AndroidApplicationComposePlugin") {
            id = "kurly.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("AndroidHiltPlugin") {
            id = "kurly.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("AndroidLibraryPlugin") {
            id = "kurly.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("AndroidLibraryComposePlugin") {
            id = "kurly.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("AndroidFeature") {
            id = "kurly.android.feature"
            implementationClass = "FeatureConventionPlugin"
        }
    }
}