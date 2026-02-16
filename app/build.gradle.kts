import java.util.Properties
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.parcelize)
    alias(libs.plugins.kotlin.serialization)

}

subprojects {
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "androidx.core" && requested.name.startsWith("core")) {
                useVersion("1.16.0")
                because("Avoid androidx.core 1.17.0 which requires AGP 8.9.1+")
            }
        }
    }
}

configurations.all {
    resolutionStrategy {
        force("androidx.core:core:1.16.0")
        force("androidx.core:core-ktx:1.16.0")
    }
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { localProperties.load(it) }
}

val baseUrlDev: String = localProperties.getProperty("base_url_dev", "")
val apiKeyDev: String = localProperties.getProperty("api_key_dev", "")
val baseUrlProd: String = localProperties.getProperty("base_url_prod", "")
val apiKeyProd: String = localProperties.getProperty("api_key_prod", "")

android {
    compileSdk = 36


    packagingOptions {
        resources {
            // Exclude the duplicate file
            excludes += "META-INF/versions/9/OSGI-INF/MANIFEST.MF"
        }
    }

    defaultConfig {
        applicationId = "com.samuelseptiano.mvvmroom"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    namespace = "com.samuelseptiano.mvvmroom"

    flavorDimensions += "version"
    productFlavors {
        create("develop") {
            dimension = "version"
            buildConfigField("String", "BASE_URL", "\"$baseUrlDev\"")
            buildConfigField("String", "API_KEY", "\"$apiKeyDev\"")

        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", "\"$baseUrlProd\"")
            buildConfigField("String", "API_KEY", "\"$apiKeyProd\"")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.ui)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Additional libraries
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.multidex)
    implementation(libs.material)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.gson)

    // Image Loading
    implementation(libs.landscapist.coil)
    implementation(libs.landscapist.placeholder)
    implementation(libs.landscapist.animation)

    // Dependency Injection
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    // Logger
    implementation(libs.timber)

    // Room database
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    annotationProcessor(libs.room.compiler)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)

    testImplementation(libs.mockk)
    testImplementation(libs.junit)

    // --- SQLCipher & SQLite ---
    implementation(libs.sqlcipher)
    implementation(libs.sqlite)

    // --- AndroidX Security ---
    implementation(libs.security.crypto)


    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)

    ksp(libs.room.compiler)

    implementation(libs.paging.runtime)
    implementation(libs.coil.compose)
    implementation(libs.compose.material.icons.extended)

}