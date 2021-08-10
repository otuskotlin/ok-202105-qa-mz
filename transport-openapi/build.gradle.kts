plugins {
    kotlin("jvm")
    id("org.openapi.generator")
}

openApiValidate {
    inputSpec.set("$rootDir/specs/client-api.yaml")
    recommend.set(true)
}

openApiGenerate {
    val openapiGroup = "${rootProject.group}.openapi"

    generatorName.set("kotlin")
//    outputDir.set("$rootDir")
    inputSpec.set("$rootDir/specs/client-api.yaml")

    packageName.set(openapiGroup)
    apiPackage.set("$openapiGroup.api")
    modelPackage.set("$openapiGroup.models")

    validateSpec.set(true)
    verbose.set(true)

//    configOptions.set(mapOf(
//    ))
}

dependencies {
    implementation(kotlin("stdlib"))
}

tasks {
    compileKotlin {
        dependsOn(openApiGenerate)
    }
}