# 📻 Caribbean Radio Player

A Kotlin Multiplatform radio streaming app modeled after the [Rust-based radio-player](https://github.com/radio-player).

Stream live Caribbean radio stations including Reggae, Soca, Bollywood, Dancehall, and more — all from a beautiful Material Design 3 Compose UI.

> **Downloaded from [Releases](../../releases)?** See [GETTING_STARTED.md](GETTING_STARTED.md) for instructions on running the pre-built packages.

## Architecture

This project uses Kotlin Multiplatform with Compose UI, targeting Web (Wasm) and Server (Ktor), built with the [Kotlin Toolchain](https://kotlin-toolchain.org/latest/).

### Project Structure

- **[/core](./core)** — Shared data model: `RadioStation` class and the list of 30 Caribbean radio stations.
- **[/app/shared](./app/shared)** — Shared Compose UI and business logic:
  - `RadioViewModel.kt` — ViewModel managing playback state (current station, play/pause, volume).
  - `StationList.kt` — Grid of station cards with loading/error/empty/content states.
  - `AudioPlayer.kt` — Player controls with play/pause button and volume slider.
  - `App.kt` — Main screen with header, station list, and audio player.
- **[/app/webApp](./app/webApp)** — Web application entry point, compiled to WebAssembly.
- **[/server](./server)** — Ktor server with REST API:
  - `GET /api/stations` — Returns the station list as JSON.
  - `GET /api/stream?url=...` — Proxies radio stream audio.

### Features (modeled after the Rust app)

| Feature | Rust App | Kotlin App |
|---|---|---|
| Station list | 30 Caribbean stations | 30 Caribbean stations |
| UI framework | Dioxus (Rust React-like) | Jetpack Compose Multiplatform |
| Backend | Axum | Ktor |
| Audio playback | Web Audio API | State management (UI-ready) |
| Volume control | Range slider | Material Slider |
| Spectrum visualizer | Canvas-based | Planned |
| Stream proxy | Axum endpoint | Ktor endpoint |

## Building

The `kotlin` (macOS/Linux) and `kotlin.bat` (Windows) scripts in the project root are self-bootstrapping wrappers for the Kotlin Toolchain.

```bash
# Build the whole project
./kotlin build
```

## Running

```bash
# Run the web app (served on localhost:8080)
./kotlin run -m webApp

# Run the Ktor server (API on localhost:8080)
./kotlin run -m server
```

## Testing

```bash
./kotlin test
```

## Packaging

Build distributable packages for the server and web app:

```bash
# Package the server as a self-contained executable JAR
./kotlin package -m server -f executable-jar

# The server JAR is placed at:
#   build/tasks/_server_executableJarJvm/server-jvm-executable.jar

# Run the packaged server
java -jar build/tasks/_server_executableJarJvm/server-jvm-executable.jar
```

The web app is built with `./kotlin build` and the deployable files are located in:
```
build/tasks/_webApp_buildWasmJsAppWasmJsDebug/
```
Copy the **entire** contents of this directory (including the `vendors/` folder) to any static web server (nginx, Apache, etc.) to serve the web app. The `vendors/` directory contains runtime dependencies required by the Kotlin/Wasm import map loader — do not omit it.

## Releasing

This project uses **Git tags** for versioning. To create a release:

### 1. Bump the version

Choose a version number following [Semantic Versioning](https://semver.org/) (e.g., `v1.0.0`, `v1.1.0`, `v2.0.0`).

### 2. Tag and push

```bash
# Create and push the version tag
git tag -a v1.0.0 -m "Release v1.0.0"
git push origin main --tags
```

### 3. GitHub Actions creates the release

Pushing a tag matching `v*` automatically triggers the **Release** workflow, which:

1. Builds the project
2. Runs all tests
3. Packages the server as an executable JAR
4. Packages the web app as a ZIP archive
5. Creates a GitHub Release with both packages attached as downloadable assets

The release will appear at `https://github.com/<owner>/<repo>/releases` with two downloadables:

- **`server-jvm-executable.jar`** — Self-contained Ktor server (run with `java -jar`)
- **`web-app.zip`** — Static web app files (deploy to any web server)

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html), [Kotlin Toolchain](https://kotlin-toolchain.org/latest/), [Compose Multiplatform](https://kotlinlang.org/compose-multiplatform/), [Kotlin/Wasm](https://kotl.in/wasm/), [Ktor](https://ktor.io/).
