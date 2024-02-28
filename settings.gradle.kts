pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "GithubUsers"
include(":data", ":domain", ":presentation", ":app")

project(":data").projectDir = file("modules_application/data")
project(":domain").projectDir = file("modules_application/domain")
project(":presentation").projectDir = file("modules_application/presentation")
project(":app").projectDir = file("modules_application/app")