import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.kotlin.dsl.withType

class AndroidPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {
            pluginManager.apply{
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<ApplicationExtension> {
                compileSdk = 34
                defaultConfig.apply {
                    targetSdk = 34
                    minSdk = 23
                }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }
                buildFeatures {
                    viewBinding = true
                    buildConfig = true
                }
            }
            tasks.withType<KotlinCompile>().configureEach {
                kotlinOptions {
                    jvmTarget =  JavaVersion.VERSION_17.toString()
                }
            }
        }
    }
}