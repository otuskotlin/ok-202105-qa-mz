
plugins {
    kotlin("jvm")
    id("org.openapi.generator")
}

openApiValidate {
    inputSpec.set("$rootDir/multiplatform/specs/api.yaml")
    recommend.set(true)
}

openApiGenerate {
    val openapiGroup = "${rootProject.group}.openapi.transport"

    generatorName.set("kotlin")
    generateApiDocumentation.set(true)
//    outputDir.set("$rootDir")
    inputSpec.set("$rootDir/multiplatform/specs/api.yaml")

    packageName.set(openapiGroup)
    apiPackage.set("$openapiGroup.api")
    modelPackage.set("$openapiGroup.models")

    validateSpec.set(true)
    verbose.set(true)

    /** https://openapi-generator.tech/docs/globals/ */
    globalProperties.apply {
        put("models", "")
        put("modelDocs", "true")
    }

    /** https://github.com/OpenAPITools/openapi-generator/blob/master/docs/generators/kotlin.md */
    configOptions.set(mapOf(
        "dateLibrary" to "string",
        "enumPropertyNaming" to "UPPERCASE",
        "serializationLibrary" to "jackson",
        "collectionType" to "list"
    ))
}

sourceSets {
    main {
        java.srcDir("$buildDir/generate-resources/main/src/main/kotlin")
    }
}

dependencies {
    val jacksonVersion: String by project

    implementation(kotlin("stdlib"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")

    testImplementation(kotlin("test-junit"))
}

tasks {
    compileKotlin {
        dependsOn(openApiGenerate)
    }
}