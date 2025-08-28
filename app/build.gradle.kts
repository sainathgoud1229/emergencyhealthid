plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.emergencyhealthid"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.emergencyhealthid"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {



    // Keep the versions from your libs.versions.toml if you are using it
    // For core-ktx, you have version "1.16.0" in libs.versions.toml but "1.12.0" here.
    // I'll use the one from libs.versions.toml for consistency, assuming you want to manage versions there.
    implementation(libs.androidx.core.ktx) // This refers to androidx-core-ktx in your TOML file

    // For appcompat, you have 1.6.1 in both places, which is fine.
    // If you add it to libs.versions.toml, you can reference it like libs.androidx.appcompat
    implementation("androidx.appcompat:appcompat:1.6.1")

    // For material, you have 1.11.0 here. Consider adding it to libs.versions.toml
    implementation("com.google.android.material:material:1.11.0")

    // For constraintlayout, you have 2.1.4 in both places.
    // If you add it to libs.versions.toml, you can reference it like libs.androidx.constraintlayout
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // ✅ ZXing QR Code library
    // You have 'com.google.zxing:core:3.5.1' here. Consider adding it to libs.versions.toml
    implementation("com.google.zxing:core:3.5.1")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0") // Consider adding to libs.versions.toml

    // Test dependencies
    // For junit, you have version "4.13.2" in libs.versions.toml and here.
    testImplementation(libs.junit) // This refers to junit in your TOML file

    // For androidx.test.ext:junit, you have "1.3.0" in libs.versions.toml but "1.1.5" here.
    // I'll use the one from libs.versions.toml for consistency.
    androidTestImplementation(libs.androidx.junit) // This refers to androidx-junit in your TOML file

    // For espresso-core, you have "3.7.0" in libs.versions.toml but "3.5.1" here.
    // I'll use the one from libs.versions.toml for consistency.
    androidTestImplementation(libs.androidx.espresso.core) // This refers to androidx-espresso-core in your TOML file
}
