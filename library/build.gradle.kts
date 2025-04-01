plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("maven-publish")
}

android {
    namespace = "com.composeglassmorphism.library"
    compileSdk = 35

    defaultConfig {
        minSdk = 28
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

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

    externalNativeBuild {
        cmake {
            path ("src/main/cpp/CMakeLists.txt")
        }
    }
    buildFeatures {
        compose = true
    }


}


afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "io.github.deiivid"
                artifactId = "composeglassmorphism"
                version = "1.0.0"

                from(components["release"]) // <- aquí petaba si no estaba en afterEvaluate

                pom {
                    name.set("ComposeGlass")
                    description.set("A Jetpack Compose library for applying Glassmorphism blur effects")
                    url.set("https://github.com/Deiivid/Glassmorphism-Compose")

                    licenses {
                        license {
                            name.set("Apache License 2.0")
                            url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }

                    developers {
                        developer {
                            id.set("deiivid")
                            name.set("David Navarro")
                            email.set("davidnavarrom3@gmail.com")
                        }
                    }

                    scm {
                        connection.set("scm:git:git://github.com/Deiivid/Glassmorphism-Compose.git")
                        developerConnection.set("scm:git:ssh://github.com:Deiivid/Glassmorphism-Compose.git")
                        url.set("https://github.com/Deiivid/Glassmorphism-Compose")
                    }
                }
            }
        }

        repositories {
            maven {
                name = "sonatype"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = findProperty("mavenCentralUsername") as String
                    password = findProperty("mavenCentralPassword") as String
                }
            }
        }
    }
}
val libVersion = "1.0.0"
val artifactId = "composeglassmorphism"


tasks.register<Zip>("createMavenZip") {
    group = "publishing"
    description = "Genera un ZIP con el .aar, .pom y firmas para subir manualmente a Maven Central"

    archiveFileName.set("$artifactId-$libVersion.zip")
    destinationDirectory.set(layout.buildDirectory.dir("release-zip"))

    // Ruta del AAR renombrado
    val aarDir = layout.buildDirectory.dir("outputs/aar").get().asFile
    val aarFile = aarDir.listFiles()?.firstOrNull { it.name.endsWith("-release.aar") }
        ?: throw GradleException("No se encontró el AAR release en ${aarDir.path}")

    // Renombrar y añadir el .aar
    from(aarFile.parentFile) {
        include(aarFile.name)
        rename(aarFile.name, "$artifactId-$libVersion.aar")
    }

    // Incluir .pom y firmas desde publications/release
    val publicationDir = layout.buildDirectory.dir("publications/release").get().asFile
    val expectedPom = File(publicationDir, "$artifactId-$libVersion.pom")
    if (!expectedPom.exists()) {
        throw GradleException("No se encontró el archivo .pom generado en ${publicationDir.path}")
    }

    from(publicationDir) {
        include("$artifactId-$libVersion.pom")
        include("$artifactId-$libVersion.pom.asc")
        include("$artifactId-$libVersion.aar.asc")
        include("*.md5")
        include("*.sha1")
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}