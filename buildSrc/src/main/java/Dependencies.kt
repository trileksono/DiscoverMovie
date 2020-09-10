/**
 * Created by trileksono on 25/08/20
 */
object ApplicationId {
    const val applicationId = "com.tleksono.discovermovie"
}

object Releases {
    const val versionCode = 1
    const val versionName = "1.0"

    const val buildToolsVersion = "29.0.0"
    const val compileSdkVersion = 28
    const val targetSdkVersion = 28
    const val minSdkVersion = 17
}

object Version {
    const val kotlin = "1.3.20"
    const val gradle = "4.0.0"

    // androidx
    const val material = "1.2.0"
    const val constraintLayout = "1.1.3"
    const val appCompat = "1.2.0"
    const val coreKtx = "1.3.1"
    const val fragmentKtx = "1.2.5"
    const val archLifecycle = "2.0.0"
    const val recyclerView = "1.1.0"

    // jetpack
    const val paging = "3.0.0-alpha03"

    //network
    const val retrofit = "2.6.0"
    const val okhttpLogging = "3.11.0"

    //thread
    const val coroutines = "1.3.0-M2"

    //media
    const val glide = "4.11.0"
    const val glidePallete = "2.1.2"

    //data
    const val gson = "2.8.5"

    //di
    const val daggerHilt = "2.28.3-alpha"
    const val hiltCompiler = "1.0.0-alpha02"

    const val cicerone = "5.1.1"

    //testing
    const val jUnit = "4.12"
    const val espresso = "3.1.1"
    const val mockito = "3.0.0"
    const val ext = "1.0.0"

    //plugin
    const val hiltPlugion = "2.28-alpha"
    const val navPlugin = "2.3.0-rc01"
}

object Android {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"
    val ktx = "androidx.core:core-ktx:${Version.coreKtx}"
    val fragmentKtx = "androidx.fragment:fragment-ktx:${Version.fragmentKtx}"
    val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"
    val appCompat = "androidx.appcompat:appcompat:${Version.appCompat}"
    val recyclerView = "androidx.recyclerview:recyclerview:${Version.recyclerView}"
    val design = "com.google.android.material:material:${Version.material}"
}

object Jetpack {
    val paging = "androidx.paging:paging-runtime:${Version.paging}"
}

object Coroutines {
    val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines}"
    val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines}"
    val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutines}"
}

object Network {
    val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
    val gsonConverter = "com.squareup.retrofit2:converter-gson:${Version.retrofit}"
    val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:${Version.okhttpLogging}"
    val mock = "com.squareup.retrofit2:retrofit-mock:${Version.retrofit}"
}

object Glide {
    val glide = "com.github.bumptech.glide:glide:${Version.glide}"
    val glidePallete = "com.github.florent37:glidepalette:${Version.glidePallete}"
    val compiler = "com.github.bumptech.glide:compiler:${Version.glide}"
}

object DI {
    val daggerHilt = "com.google.dagger:hilt-android:${Version.daggerHilt}"
    val daggerHiltCompiler = "com.google.dagger:hilt-android-compiler:${Version.daggerHilt}"
    val hiltCompiler = "androidx.hilt:hilt-compiler:${Version.hiltCompiler}"
    val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:${Version.hiltCompiler}"
}

object Navigation {
    val cicerone = "ru.terrakok.cicerone:cicerone:${Version.cicerone}"
}

object Testing {
    val jUnit = "junit:junit:${Version.jUnit}"
    val espresso = "androidx.test.espresso:espresso-core:${Version.espresso}"
    val mockito = "org.mockito:mockito-inline:${Version.mockito}"

    val extJunit = "androidx.test.ext:junit:${Version.ext}"
}

object Plugin {
    val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}"
    val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Version.hiltPlugion}"
    val gradle = "com.android.tools.build:gradle:${Version.gradle}"
}

