plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.googleService)
    alias(libs.plugins.secretGradlePlugin)
    alias(libs.plugins.dagger.hilt)
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")

}

android {
    namespace = "com.smitcoderx.mathongoassignment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.smitcoderx.mathongoassignment"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Fragment
    implementation(libs.androidx.fragment)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // Navigation
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.navigation.ui)

    // Hilt
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)

    // Room DB
    implementation(libs.androidx.room)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    // Dagger-Hilt
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)

    // RecyclerView
    implementation(libs.recyclerView)

    // ViewModel + LiveData
    implementation(libs.viewModel)
    implementation(libs.liveData)
    kapt(libs.lifecycle.compiler)

    // Coil
    implementation(libs.android.coil)

    // Credential Manager
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.service)
    implementation(libs.service.auth)
    implementation(libs.androidx.credentials.googleId)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    // SDP + SSP
    implementation(libs.intuit.sdp)
    implementation(libs.intuit.ssp)
}

kapt {
    correctErrorTypes = true
}
