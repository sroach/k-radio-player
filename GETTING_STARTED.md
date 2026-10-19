# Getting Started

This guide explains how to run the Caribbean Radio Player from the pre-built packages downloaded from [GitHub Releases](../../releases), without building from source.

## System Requirements

- **Java 17+** — Required to run the server JAR. Check with `java -version`.
- **Modern web browser** — Chrome 120+, Firefox 120+, Safari 17+, or Edge 120+ (for WebAssembly support).

## Downloading

1. Go to the [Releases](../../releases) page.
2. Download the latest version's assets:
   - **`server-jvm-executable.jar`** — The backend API server.
   - **`web-app.zip`** — The static web application files.

---

## Option 1: Run the Web App (Standalone)

The web app is fully self-contained and does not require the server. It streams radio stations directly in your browser.

### 1. Extract the web app

```bash
unzip web-app.zip
```

This creates a `web/` directory containing `index.html` and all required files.

### 2. Serve the files

The web app requires a local HTTP server — you cannot open `index.html` directly from the file system due to browser security restrictions.

**Using Python (recommended):**
```bash
cd web
python3 -m http.server 8080
```

**Using Node.js:**
```bash
npx serve web -p 8080
```

**Using any other static file server** (nginx, Apache, etc.) — point it to the `web/` directory.

### 3. Open in browser

Navigate to `http://localhost:8080` in your browser.

### 4. Play a station

Click any station card to start streaming. Use the player bar at the bottom to pause, resume, or adjust volume.

---

## Option 2: Run the Server (API Only)

The server provides a REST API for the station list and stream proxying. This is useful if you want to integrate the station data into another application.

### 1. Run the server

```bash
java -jar server-jvm-executable.jar
```

The server starts on `http://localhost:8080`.

### 2. Use the API

**Get station list:**
```bash
curl http://localhost:8080/api/stations
```

**Proxy a stream:**
```bash
curl "http://localhost:8080/api/stream?url=https://stream-url.example.com/radio"
```

### 3. Stop the server

Press `Ctrl+C` in the terminal.

---

## Option 3: Run Both Together

If you want to run the full stack locally:

### 1. Start the server on a different port

```bash
java -jar -Dktor.development=true server-jvm-executable.jar
```

The server runs on port 8080 by default. If you need a different port, you can configure it via environment variables or system properties depending on your Ktor setup.

### 2. Serve the web app on another port

```bash
cd web
python3 -m http.server 3000
```

### 3. Open the web app

Navigate to `http://localhost:3000` — the web app will stream stations directly in the browser.

---

## Troubleshooting

### "java: command not found"

Install the JDK. You can download it from:
- [Adoptium (Eclipse Temurin)](https://adoptium.net/)
- [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)

Verify installation: `java -version` should show version 17 or higher.

### Web app shows a loading spinner forever

- Make sure you're serving the files via HTTP, not opening `file://` directly.
- Check the browser console (F12) for WebAssembly errors.
- Ensure your browser supports WebAssembly (Chrome 120+, Firefox 120+, Safari 17+).
- **Missing `vendors/` directory** — If you downloaded a release before v1.0.1, the `vendors/` folder may be missing. Redownload the latest `web-app.zip` from [Releases](../../releases). The `vendors/` directory is required — do not remove it when deploying.

### Radio stations won't play

- Some stations may be temporarily offline — try a different station.
- HTTP-only stations may be blocked if your server uses HTTPS (mixed content policy).
- Check your network connection and firewall settings.

### Port 8080 is already in use

Stop the process using port 8080:
```bash
lsof -ti:8080 | xargs kill
```

Or use a different port for your static file server (e.g., `python3 -m http.server 3000`).

### "Loading module blocked because of a disallowed MIME type"

This error in the browser console means nginx (or your web server) is returning HTML instead of JavaScript for vendor files. Causes:
- **Missing `vendors/` directory** — Redownload the latest release; earlier versions didn't include vendor dependencies.
- **nginx misconfiguration** — Make sure nginx is serving all file types correctly. Add this to your nginx config if needed:
  ```nginx
  location /vendors/ {
      types {
          application/javascript js mjs;
      }
      default_type application/javascript;
  }
  ```

---

## What's Next?

- **Deploy to a server** — Upload the `web/` files to any static hosting (Netlify, Vercel, GitHub Pages, etc.).
- **Contribute** — See [README.md](../../README.md) for building from source and development guidelines.
