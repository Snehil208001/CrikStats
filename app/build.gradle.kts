plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // --- ADD BELOW ---
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.example.crikstatsassignment"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.crikstatsassignment"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"



        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // --- FIX 1 ---
    // 'buildTypes' and '{' MUST be on the same line.
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )


        }
    }

    // --- FIX 2 ---
    // 'compileOptions' must NOT have an equals sign.
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // --- FIX 3 ---
    // 'kotlinOptions' must NOT have an equals sign.
    kotlinOptions {
        jvmTarget = "11"
    }

    // --- FIX 4 ---
    // 'buildFeatures' must NOT have an equals sign.
    buildFeatures {
        compose = true
    }

    // This is correct (it needs the equals sign)
    dynamicFeatures.add(":feature-player")
}

// --- FIX 5 ---
// 'dependencies' must NOT have an equals sign.
dependencies {

    implementation(libs.androidx.core.ktx) // This is line 57, it was never the problem

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // --- ADD BELOW ---
    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.navigation.compose)

    // Play Core API for DFM
    implementation(libs.play.core)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    // Retrofit & Moshi (for mock API)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.moshi)
}