📱 CrikStats — Dynamic Feature Module + Hilt Assignment

This project was built as part of the Android Developer Assignment to demonstrate:

Dynamic Feature Module (DFM)

Dagger Hilt dependency injection

MVVM + Repository

On-demand module download using Play Core

Shared DI graph between base module and feature module

Simple UI using Jetpack Compose

The app implements the given concept CrikStats, where player statistics are loaded from a mock API and shown only after downloading the dynamic feature module.

📌 Project Structure
CrikStats/
 ├── app/                # Base module
 └── feature_player/     # Dynamic Feature Module (on-demand)

1️⃣ Dynamic Feature Module Setup
✔ Project Registration (settings.gradle.kts)

Both modules are included:

include(":app")
include(":feature_player")

✔ Base App Configuration (app/build.gradle.kts)

The base module declares the dynamic module:

android {
    dynamicFeatures += ":feature_player"
}


Google Play Core dependency:

dependencies {
    implementation(libs.play.core)
}

✔ Dynamic Feature Configuration (feature_player/build.gradle.kts)

The module uses:

plugins {
    id("com.android.dynamic-feature")
    id("org.jetbrains.kotlin.android")
}


And depends on the base app:

implementation(project(":app"))

✔ AndroidManifest (feature-player)
<dist:module
    dist:onDemand="true"
    dist:title="Player Feature"
    xmlns:dist="http://schemas.android.com/apk/distribution">
</dist:module>


This ensures the module is NOT packaged in the base APK and must be downloaded on demand.

✔ Module Download Logic (inside app module)

The base module uses SplitInstallManager:

private val manager = SplitInstallManagerFactory.create(context)

val request = SplitInstallRequest
    .newBuilder()
    .addModule("feature_player")
    .build()

manager.startInstall(request)


A SplitInstallStateUpdatedListener updates UI states:

DOWNLOADING

INSTALLING

INSTALLED

FAILED

Once installed, a button appears:

"View Player Stats"

Tapping it launches the dynamic feature’s activity.

2️⃣ Hilt Dependency Sharing (Base → Dynamic Feature)

Dynamic modules cannot use @AndroidEntryPoint directly.
So Hilt’s @EntryPoint API is used.

✔ Step 1 — Define an EntryPoint (in app module)
@EntryPoint
@InstallIn(SingletonComponent::class)
interface FeatureModuleDependencies {
    fun mockPlayerRepository(): MockPlayerRepository
}


MockPlayerRepository is provided in a Hilt module in the base app.

✔ Step 2 — Access Dependency in Feature Module

Inside PlayerStatsActivity:

val deps = EntryPointAccessors.fromApplication(
    applicationContext,
    FeatureModuleDependencies::class.java
)

val viewModelFactory = PlayerStatsViewModelFactory(
    deps.mockPlayerRepository()
)

✔ Step 3 — Inject into ViewModel
class PlayerStatsViewModelFactory(
    private val repo: MockPlayerRepository
) : ViewModelProvider.Factory


This allows the dynamic module to access base module DI without Hilt annotations.

3️⃣ User Flow (As Required in PDF)

Launch app → Home Screen

Tap “Download Player Stats Module”

DFM download begins (UI shows progress)

After installation → show “View Player Stats”

Navigates to PlayerStatsActivity inside dynamic module

Displays mock player info:

Name: Virat Kohli

Matches: 253

Average: 57.8

"Back" returns to Home

4️⃣ Architecture Overview
✔ MVVM

HomeViewModel → handles module install logic

PlayerStatsViewModel → loads mock stats

✔ Repository Pattern

MockPlayerRepository mimics API results using a suspend function.

You do not need a real API; mock data satisfies assignment requirements.

5️⃣ UI Layer

Jetpack Compose (simple screens)

HomeScreen

Player Stats Screen (inside dynamic module)

No complex UI required.

6️⃣ How to Test Module Download (Exact as Assignment Requires)

Because Dynamic Feature Modules only work in Play-distributed bundles:

✔ Step 1 — Generate Signed AAB

via:
Build → Build Bundles / APKs → Build Bundle(s)

✔ Step 2 — Upload to Play Console

Under Internal App Sharing

✔ Step 3 — Install on Physical Device

Using the generated testing link.

✔ Step 4 — Test Flow

App opens → module not installed

Tap “Download Module”

Observe download/installation progress

After install, tap “View Player Stats”

Dynamic feature activity opens → data loads → success

✅ Deliverables Included

✔ Base app module
✔ Dynamic feature module
✔ Hilt DI integration
✔ EntryPoint API usage
✔ On-demand module download
✔ MVVM + Repository
✔ README.md (you are reading it)
