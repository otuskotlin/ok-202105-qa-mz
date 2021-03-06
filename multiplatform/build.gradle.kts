plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("org.openapi.generator")
}

kotlin {
    js(IR) {
        browser {
            binaries.executable()
        }
    }
    jvm {}

    val kotestVersion: String by project

    sourceSets {
        val serializationVersion: String by project

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation("io.kotest:kotest-assertions-core:$kotestVersion")
                implementation("io.kotest:kotest-property:$kotestVersion")
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
                implementation("io.kotest:kotest-framework-engine:$kotestVersion")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit5"))
                implementation("io.kotest:kotest-runner-junit5:$kotestVersion")
            }
        }
        tasks.withType<Test>().configureEach {
            useJUnitPlatform()
        }
    }
}


openApiValidate {
    inputSpec.set("$projectDir/specs/api.yaml")
    recommend.set(true)
}

openApiGenerate {
    inputSpec.set("$rootDir/multiplatform/specs/api.yaml")

    generatorName.set("kotlin")
    library.set("multiplatform")

    generateApiDocumentation.set(true)
    outputDir.set("$projectDir")
    packageName.set("generated")

    configOptions.set(
        mapOf(
            "dateLibrary" to "string",
            "enumPropertyNaming" to "UPPERCASE",
            "collectionType" to "list",
        )
    )

    globalProperties.apply {
        put("models", "")
        put("modelDocs", "true")
    }
}

tasks {
    build {
        /* Copy transport models and package.json to a folder, to be able install them as a package with yarn. */
        doLast {
            copy {
                from("$buildDir/processedResources/js/main")
                include("package.json")
                into("$buildDir/libs/transportModels")
            }
            copy {
                from("$buildDir/compileSync/main/productionExecutable/kotlin")
                into("$buildDir/libs/transportModels")
            }
        }
        //finalizedBy(":frontend:addTransportModels")
    }
}