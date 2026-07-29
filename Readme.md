# 📋 DailyTaskManager

A modern Android Task Management application built using **Kotlin**, **Jetpack Compose**, **MVVM**, **Hilt**, **Room**, and **Coroutines** following Google's recommended architecture and best practices.

> 🚧 This project is currently under active development.

---

## 📱 Features (Implemented)

- ✅ Modern UI with Jetpack Compose
- ✅ MVVM Architecture
- ✅ Dependency Injection using Hilt
- ✅ Local Database using Room
- ✅ Type Converters for custom data types
- ✅ DAO for CRUD operations
- ✅ Repository Layer
- ✅ ViewModel
- ✅ Task List Screen (UI with dummy data)
- ✅ Add Task Screen (UI)
- ✅ Navigation setup (In Progress)

---

## 🏗️ Architecture

The project follows **MVVM (Model–View–ViewModel)** architecture.

```
Presentation (Compose UI)
        │
        ▼
ViewModel
        │
        ▼
Repository
        │
        ▼
Room Database
        │
        ▼
SQLite
```

---

## 📂 Project Structure

```
app
│
├── data
│   ├── local
│   │   ├── converter
│   │   ├── dao
│   │   ├── database
│   │   └── entity
│   │
│   └── repository
│
├── di
│
├── presentation
│   ├── screen
│   ├── navigation
│   └── viewmodel
│
├── ui
│
└── app
```

---

## 🛠 Tech Stack

| Technology | Purpose |
|------------|----------|
| Kotlin | Programming Language |
| Jetpack Compose | Declarative UI |
| MVVM | Architecture Pattern |
| Hilt | Dependency Injection |
| Room | Local Database |
| Kotlin Coroutines | Asynchronous Programming |
| Flow | Reactive Data Stream |
| Navigation Compose | Screen Navigation |
| Material 3 | UI Components |

---

## 📦 Libraries Used

- Jetpack Compose
- Material 3
- Hilt
- Room
- Kotlin Coroutines
- Flow
- Navigation Compose

---

## 🗄 Database Schema

### Task Entity

| Field | Type |
|-------|------|
| id | Long |
| title | String |
| description | String? |
| priority | Priority |
| category | String? |
| dueDate | Long? |
| isCompleted | Boolean |
| createdAt | Long |
| updatedAt | Long |

---

## 📌 Current Progress

### Completed

- Project setup
- Hilt configuration
- Room setup
- Entity
- DAO
- Type Converters
- Database
- Repository
- ViewModel
- Task List UI
- Add Task UI

### In Progress

- Navigation Compose
- Insert Task functionality

### Planned

- Edit Task
- Delete Task
- Mark Task Complete
- Search Tasks
- Filter Tasks
- Sort Tasks
- Dark Mode
- DataStore Preferences
- Unit Testing
- UI Testing

---

## 🚀 Getting Started

### Clone the repository

```bash
git clone https://github.com/your-username/DailyTaskManager.git
```

### Open in Android Studio

- Android Studio Narwhal Feature Drop or newer
- Sync Gradle
- Run the app

---

## Minimum Requirements

| Requirement | Version |
|-------------|----------|
| Android Studio | Narwhal Feature Drop |
| Kotlin | 2.0.21 |
| AGP | 8.12.3 |
| Min SDK | 26 |
| Target SDK | 36 |
| Compile SDK | 36 |
| JDK | 17 |

---

## Learning Goals

This project is being developed to gain hands-on experience with:

- Clean Architecture principles
- MVVM Architecture
- Dependency Injection using Hilt
- Local persistence with Room
- Reactive programming using Flow
- Kotlin Coroutines
- State management in Jetpack Compose
- Modern Android development best practices

---

## Screenshots

> Screenshots will be added as development progresses.

---

## License

This project is intended for learning and portfolio purposes.

---

## Author

**Deepak Kumar**

Android Developer

- Kotlin
- Jetpack Compose
- MVVM
- Hilt
- Room
- Coroutines 