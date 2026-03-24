# 🎓 Your Day — Android/APK Edition
 
![Your Day Banner](https://img.shields.io/badge/Status-Under_Construction-orange?style=for-the-badge) ![Version](https://img.shields.io/badge/Version-1.0.0-blue?style=for-the-badge) ![Android](https://img.shields.io/badge/Android-APK-green?style=for-the-badge)
 
> [!IMPORTANT]
> **This repository is currently Under Construction.** 🏗️
> We are migrating the core logic to a native Android experience. Some features may be unstable or in-progress.
 
✨ **Repository:** [https://github.com/Marshmellow31/APK-Your-Day](https://github.com/Marshmellow31/APK-Your-Day)

 
Your Day is a premium, beautifully designed **Android/APK** version built exclusively for students. It acts as a comprehensive academic dashboard to track your degree progress, manage subjects and tasks, reflect in a daily diary, and receive actual push notifications for upcoming deadlines—all running natively on your phone.


---

## 🌟 Core Features

### 🏰 1. Smart Dashboard & Premium UI `[NATIVE]`
- **Dynamic Header:** High-end SaaS aesthetic with time-based greetings, gradient shimmer animations, and sophisticated ambient glow.
- **BTech Journey Tracker:** degree progress bar integrated into the home screen.
- **Quick Add & Overview:** Instantly add tasks or view productivity summary stats.
- **Streak & Milestones:** `[UNDER CONSTRUCTION]` Habit tracking and study streaks logic is being ported from PWA.

### 📚 2. Subject & Topic Management `[PARTIAL]`
- **Subjects:** `[NATIVE]` Full integration with Firestore and Room for offline subject cards.
- **Topics:** `[UNDER CONSTRUCTION]` Topic management is currently a placeholder; logic for grouping tasks by topic is in development.
- **Premium SaaS Design:** Beautiful, minimalist subject cards with custom gradient color coding.

### 🧠 3. Smart Scheduler & Task Manager `[PARTIAL]`
- **Task Management:** `[NATIVE]` Full CRUD operations with local-first sync (`TaskRepository`).
- **Auto-Scheduling Algorithm:** `[UNDER CONSTRUCTION]` The greedy scheduling algorithm is currently a placeholder. Tasks are not yet auto-allocated to study blocks.
- **Micro-interactions:** Elegant swipe-to-complete animations and UI micro-interactions.

### 🎯 4. Personal Development `[NATIVE]`
- **Goal Tracking:** Establish long-term objectives and track progress natively with completion percentages.
- **Daily Action Plans:** `[UNDER CONSTRUCTION]` Automatic goal-to-task conversion logic is in progress.

### 📅 5. Academic Calendar `[PWA ONLY]`
- **Semester Visualizer:** Currently only available in the PWA version. Native Android screen and Indian Festival integration are missing.

### 📔 6. Daily Diary & Expense Log `[PWA ONLY]`
- **Mood & Expense Tracking:** These features have not yet been ported to the native Android codebase.

### 🔔 7. Native Push Notifications `[NATIVE]`
- Receive actual, native lock-screen notifications via **Firebase Cloud Messaging (FCM)**.
- *Bypasses paid Firebase plans entirely* by utilizing a completely free **Vercel Serverless API + Cron Job** architecture.

### 📱 8. Native Android & PWA Support
- **Native APK:** Blazing fast experience built with Jetpack Compose.
- **Installable PWA:** The legacy version remains fully functional for web/iOS users via Vite.
---

## ⚙️ How It Works (Architecture)

Your Day is engineered to be blazing fast, serverless, and highly secure.

#### **Native Android Flow (Kotlin + Compose)**
The Android app utilizes a **Clean Architecture** with a **Local-First** data pattern.
1. **Local Storage (Room):** All data is cached locally for instant UI responsiveness and offline support.
2. **Remote Sync (Firestore):** A `Repository` layer manages real-time synchronization between the local SQLite database and Cloud Firestore.
3. **Dependency Injection (Hilt):** Ensures a modular and testable codebase.

#### **Legacy PWA Flow (Vite + Vanilla JS)**
The original PWA uses modular Vanilla JavaScript and **Vite** for a tiny bundle size. Accessing the web version triggers the `sw.js` Service Worker for offline caching.

#### **Database & Security (Firebase)**
Both versions share a unified **Cloud Firestore** backend. Advanced **Security Rules** ensure that user data is isolated based on `Auth Token`, making it mathematically impossible for unauthorized access.

#### **Push Notification Engine**
To avoid costly server bills, notifications are handled by:
1. **App:** Saves an FCM device token to Firestore.
2. **Cron Scheduler:** A free external cron service pings the Vercel API.
3. **Vercel API:** Scans the database for overdue tasks and sends payloads via Google's FCM servers.

---

## 💻 Tech Stack

| Layer | Native Android `[NEW]` | PWA (Legacy) |
| :--- | :--- | :--- |
| **Language** | Kotlin | Vanilla JS (ES Modules) |
| **Framework** | Jetpack Compose | Vite ⚡ |
| **Styling** | Compose Material3 | Vanilla CSS (Premium Dark) |
| **DI / State** | Hilt & Flows | Simple State Stores |
| **Local DB** | Room (SQLite) | IndexedDB (Cache) |
| **Auth / Sync** | Firebase (Auth & Firestore) | Firebase (Auth & Firestore) |
| **API / Cron** | Vercel Serverless (Node.js) | Vercel Serverless (Node.js) |

---

## 🚀 Quick Start Guide

### 📱 1. Android Native Setup
1. **Prerequisites:** Install [Android Studio](https://developer.android.com/studio) (Ladybug or newer) and JDK 17+.
2. **Clone & Open:** Open the `/android` folder in Android Studio.
3. **Firebase:** 
   - Add your `google-services.json` to `/android/app/`.
   - Update `AuthDataSource.kt` if using custom Google Sign-In IDs.
4. **Build:** Sync Gradle and run on a physical device or emulator.

### 🌐 2. PWA / Web Setup
1. **Prerequisites:** Node.js v18+.
2. **Install:** `npm install` in the root directory.
3. **Firebase:** Open `/public/firebase-config.js` and paste your web config.
4. **Dev:** `npm run dev` to start the Vite server.

### ☁️ 3. Backend & Notifications
- Uses **Vercel Functions** for the push engine.
- Deploy via `npm run deploy` from the root.
- Set `FIREBASE_SERVICE_ACCOUNT` and `CRON_SECRET` in Vercel environment variables.

---
*Built with ❤️ to make studying just a little bit easier.* 
