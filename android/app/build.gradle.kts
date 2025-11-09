plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.apollographql.apollo") version "4.1.1"
}

android {
    namespace = "com.shaheenghiassy.jack"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.shaheenghiassy.jack"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
        unitTests.all {
            it.jvmArgs("-XX:+EnableDynamicAgentLoading") // remove warning for Java Agents modifying the JVM (this is necessary for the MockK library)
        }
    }
}

dependencies {
    implementation(libs.apollographql.apollo.runtime)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.apollo.runtime)
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.junit)
    testImplementation("org.robolectric:robolectric:4.13")
    testImplementation("androidx.compose.ui:ui-test-junit4:1.7.5")
    testImplementation("androidx.compose.ui:ui-test-manifest:1.7.5")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("io.mockk:mockk:1.13.13")
    testImplementation("io.mockk:mockk-android:1.13.13")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

apollo {
    service("service") {
        packageName.set("com.ghiassy.jack")
        introspection {
            endpointUrl.set("https://jack.ghiassy.com/graphql")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
    }

}
