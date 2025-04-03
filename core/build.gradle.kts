plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    val targetVersion = rootProject.ext.get("targetVersion") as Int
    val minVersion = rootProject.ext.get("minVersion") as Int
    val jvmVersion = rootProject.ext.get("jvmVersion") as JavaVersion
    val kotlinJvmVersion = rootProject.ext.get("kotlinJvmVersion") as String
    val fileProviderAuthorities = "mezzari.torres.lucas.easy_debugger.library.provider"

    namespace = "mezzari.torres.lucas.core"
    compileSdkVersion(targetVersion)
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    defaultConfig {
        minSdk = minVersion
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "fileProviderAuthorities", "\"$fileProviderAuthorities\"")
        addManifestPlaceholders(
            mapOf(
                "fileProviderAuthorities" to fileProviderAuthorities
            )
        )
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
        sourceCompatibility = jvmVersion
        targetCompatibility = jvmVersion
    }
    kotlinOptions {
        jvmTarget = kotlinJvmVersion
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.kotlin.jdk)

    //Android
    implementation(libs.app.compat)
    implementation(libs.material)

    //Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.expresso)
}