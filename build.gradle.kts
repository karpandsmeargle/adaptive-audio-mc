plugins {
    id("com.diffplug.spotless") version "7.+"
}

spotless {
    format("misc") {
        target(".gitattributes", ".gitignore")

        trimTrailingWhitespace()
        leadingTabsToSpaces(4)
        endWithNewline()
    }
    format("markdown") {
        target("README.md")

        trimTrailingWhitespace()
        endWithNewline()
    }
    kotlinGradle {
        trimTrailingWhitespace()
        leadingTabsToSpaces(4)
        endWithNewline()
    }
}
