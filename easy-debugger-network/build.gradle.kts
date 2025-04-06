plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.maven)
}

// Because the components are created only during the afterEvaluate phase, you must
// configure your publications using the afterEvaluate() lifecycle method.
afterEvaluate {
    publishing {
        publications {
            // Creates a Maven publication called "release".
            create<MavenPublication>("release") {
                // Applies the component for the release build variant.
                from(components["release"])

                // You can then customize attributes of the publication as shown below.
                groupId = "com.github.LTMezzari"
            }
        }
    }
}

android {
    val targetVersion = rootProject.ext.get("targetVersion") as Int
    val minVersion = rootProject.ext.get("minVersion") as Int
    val jvmVersion = rootProject.ext.get("jvmVersion") as JavaVersion
    val kotlinJvmVersion = rootProject.ext.get("kotlinJvmVersion") as String

    namespace = "mezzari.torres.lucas.easy_debugger_network"
    compileSdkVersion(targetVersion)

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        minSdk = minVersion
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
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

    //Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.expresso)

    //Retrofit
    implementation(libs.retrofit)

    //My Libraries
    implementation(libs.network)

    //Project
    implementation(project(":core"))
    implementation(project(":easy-debugger"))
}