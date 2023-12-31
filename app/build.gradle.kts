import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.nimble"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.nimble"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField("String","API_SECRET",  "\"${properties.getProperty("API_SECRET")}\"")
        buildConfigField("String","API_CLIENT",  "\"${properties.getProperty("API_CLIENT")}\"")

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
        compose = true
        viewBinding = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.5"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


}


dependencies {

    implementation ("androidx.compose.material:material:1.0.1")
    implementation ("androidx.compose.compiler:compiler:1.4.5")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.ar:core:1.40.0")
    implementation("androidx.test.ext:junit-ktx:1.1.5")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation ("androidx.compose.runtime:runtime-livedata:1.5.4")

    // NetWorking dependencies
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")

    // Test
    testImplementation ("junit:junit:4.+")
    testImplementation ("io.mockk:mockk:1.12.4")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation ("io.mockk:mockk:1.12.4")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:3.2.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

    implementation("org.junit.jupiter:junit-jupiter-params:5.8.0")
    testImplementation(kotlin("test"))

    // dots dependency
    implementation("com.tbuonomo:dotsindicator:4.2")

    // Images
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.22")
}