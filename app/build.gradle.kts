plugins {
   id("com.android.application")
   id("org.jetbrains.kotlin.android")
   id("kotlin-parcelize")
   id("com.google.devtools.ksp")
}

android {
   namespace = "com.example.filmtestapp"
   compileSdk = 34

   defaultConfig {
      applicationId = "com.example.filmtestapp"
      minSdk = 23
      targetSdk = 34
      versionCode = 1
      versionName = "1.0"

      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
      vectorDrawables { useSupportLibrary = true }
   }

   buildFeatures {
      viewBinding = true
   }

   buildTypes {
      release {
         isMinifyEnabled = false
         proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
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

   composeOptions {
      kotlinCompilerExtensionVersion = "1.4.3"
   }
   packaging {
      resources {
         excludes += "/META-INF/{AL2.0,LGPL2.1}"
      }
   }
}

val pagingVersion = "3.2.1"
val coilVersion = "2.4.0"
val retrofitVersion = "2.9.0"
val gsonVersion = "2.9.0"
val okhhtpVersion = "5.0.0-alpha.2"
val koinVersion = "3.5.0"
val roomVersion = "2.5.2"
val compilerVersion = "1.1.1"

dependencies {
   implementation("androidx.core:core-ktx:1.12.0")
   implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
   implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
   implementation("com.google.android.material:material:1.10.0")
   implementation("androidx.constraintlayout:constraintlayout:2.1.4")
   implementation("androidx.appcompat:appcompat:1.6.1")
   testImplementation("junit:junit:4.13.2")
   androidTestImplementation("androidx.test.ext:junit:1.1.5")
   androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

   //pagination
   implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")

   //coil
   implementation("io.coil-kt:coil:$coilVersion")

   //retrofit
   implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
   implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

   //gson
   implementation("com.google.code.gson:gson:$gsonVersion")

   //okhttp3
   implementation("com.squareup.okhttp3:okhttp:$okhhtpVersion")
   implementation("com.squareup.okhttp3:logging-interceptor:$okhhtpVersion")

   //koin
   implementation("io.insert-koin:koin-android:$koinVersion")

   //room
   implementation("androidx.room:room-ktx:$roomVersion")
   implementation("androidx.room:room-runtime:$roomVersion")
   ksp("android.arch.persistence.room:compiler:$compilerVersion")
   ksp("androidx.room:room-compiler:$roomVersion")
}