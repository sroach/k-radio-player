# 📻 Caribbean Radio Player

A Kotlin Multiplatform radio streaming app modeled after the [Rust-based radio-player](https://github.com/radio-player).

Stream live Caribbean radio stations including Reggae, Soca, Bollywood, Dancehall, and more — all from a beautiful Material Design 3 Compose UI.

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

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html), [Kotlin Toolchain](https://kotlin-toolchain.org/latest/), [Compose Multiplatform](https://kotlinlang.org/compose-multiplatform/), [Kotlin/Wasm](https://kotl.in/wasm/), [Ktor](https://ktor.io/).
