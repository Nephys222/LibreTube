plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlinx-serialization")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.ksp)
}

android {
    compileSdk = 34

    defaultConfig {
        applicationId = "com.github.libretube"
        minSdk = 21
        targetSdk = 34
        versionCode = 54
        versionName = "0.24.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        resValue("string", "app_name", "LibreTube")

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
            arg("exportSchema", "true")
        }
    }

    viewBinding {
        enable = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            resValue("string", "app_name", "LibreTube Debug")
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    // Comment this block if issues occur while generating the baseline profile
    splits {
        abi {
            isEnable = true
            reset()
            include("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
            isUniversalApk = true
        }
    }

    packaging {
        jniLibs.excludes.add("lib/armeabi-v7a/*_neon.so")
    }

    tasks.register("testClasses")

    lint {
        abortOnError = false
        checkReleaseBuilds = false
    }

    buildFeatures {
        buildConfig = true
    }

    namespace = "com.github.libretube"
}

dependencies {
    // debugImplementation libs.square.leakcanary
    /* Android Core */
    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.preference)
    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.collection)
    implementation(libs.androidx.media)
    implementation(libs.androidx.swiperefreshlayout)

    /* Android Lifecycle */
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.service)

    /* Design */
    implementation(libs.material)

    /* ExoPlayer */
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.exoplayer.hls)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.session)
    implementation(libs.androidx.media3.datasource.cronet) {
        exclude(group = "com.google.android.gms")
    }

    /* Retrofit and Kotlinx Serialization */
    implementation(libs.square.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.retrofit)

    /* NewPipe Extractor */
    implementation(libs.newpipeextractor)

    /* Cronet and Coil */
    coreLibraryDesugaring(libs.desugaring)
    implementation(libs.cronet.embedded)
    implementation(libs.cronet.okhttp)
    implementation(libs.coil)

    /* Room */
    ksp(libs.room.compiler)
    implementation(libs.room)

    /* Baseline profile generation */
    implementation(libs.androidx.profileinstaller)
    baselineProfile(project(":baselineprofile"))

    /* AndroidX Paging */
    implementation(libs.androidx.paging)

    /* Testing */
    testImplementation(libs.junit)
}
