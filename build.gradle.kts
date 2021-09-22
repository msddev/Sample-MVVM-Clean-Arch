buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Config.ClassPaths.androidGradle)
        classpath(Config.ClassPaths.kotlinGradle)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")
    }
}


allprojects {
    repositories {
        mavenCentral()
        google()
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}
