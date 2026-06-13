plugins {
    id("org.jetbrains.kotlin.jvm")
}



dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.paging.common)
    testImplementation(libs.junit)
}