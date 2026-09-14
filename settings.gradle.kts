dependencyResolutionManagement {
    repositoriesMode.set(org.gradle.api.initialization.RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "SmartPantryManager"
include(":app")
