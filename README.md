# Calisthenics Coach

A beginner-friendly bodyweight fitness tracker with real-time pose detection.

## Features
- **Push-up Tracker** — Camera + ML Kit pose detection, rep counting, form feedback
- **Squat Tracker** — Knee angle detection, rep counting, posture warnings
- **Plank Timer** — Hold timer with hip position feedback
- **Workout History** — All sessions saved locally with Room DB
- **Beginner Routines** — 3 difficulty levels with guided exercises
- **Progress Dashboard** — Weekly charts, total stats, streak tracking

## Tech Stack
- Kotlin 1.9.20 + Jetpack Compose
- CameraX + ML Kit Pose Detection
- Room Database + MVVM Architecture
- Accompanist Permissions
- GitHub Actions CI/CD

## Build
Push to `main` branch. GitHub Actions builds the APK automatically.
Download from Actions > Artifacts > `app-debug`.
