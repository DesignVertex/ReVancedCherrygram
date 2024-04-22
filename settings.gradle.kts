include("dummy")

rootProject.name = "revanced-experiments"

buildCache {
    local {
        isEnabled = !System.getenv().containsKey("CI")
    }
}
