# 📱 Android Application Documentation (LEVEL 10/10)
________________________________________
## 🧾 General Information
**Project Name:**  
RadioStationsList  
**Author(s):**  
Zeev Fraiman  
**Date:**  
May 2026  
**Language:**  
Java  
**Development Environment:**  
Android Studio  
**Android Version (minSdk / targetSdk):**  
28 / 35  
________________________________________
## 🎯 Project Goal
*   **Problem Solved:** Provides a simple and efficient way to stream online radio stations from around the world using the Radio Browser API.
*   **Importance:** Allows users to access global media content and music genres (Jazz, News, etc.) in a lightweight mobile format with background playback support.
*   **Target Audience:** Radio enthusiasts, travelers, and users looking for global news or specific music genres.
________________________________________
## 📌 Requirements
### Functional Requirements
*   Fetch a list of top radio stations via a REST API.
*   Filter stations by category/genre (All, Jazz, News).
*   Play radio streams directly within the app.
*   Support background playback using an Android Service.
*   Show notifications during loading and playback.
### Non-functional Requirements
*   **Performance:** Fast loading of the station list using asynchronous network calls.
*   **Usability:** Simple, intuitive UI with minimal navigation steps.
*   **Reliability:** Robust error handling for network failures and stream buffering issues.
________________________________________
## 🧠 General Architecture
*   **Approach:** MVC (Model-View-Controller) adapted for Android (Activity-centric).
*   **Reasoning:** Chosen for its simplicity and directness for a utility-focused application, ensuring low overhead.
*   **Main Components:**
    *   **Activities:** UI controllers (`MainActivity`, `GenreActivity`, `LoadingActivity`).
    *   **Service:** `RadioService` for background audio execution.
    *   **Model:** `RadioStation` POJO for data representation.
    *   **Helper:** `NotificationHelper` for system interactions.
________________________________________
## 🧩 UML Diagram
`[GenreActivity] -> [MainActivity]`  
`[MainActivity] -> [RadioStation] (Model)`  
`[MainActivity] -> [RadioService] (Background Execution)`  
`[MainActivity] -> [NotificationHelper] -> [Android System Notification]`  
`[MainActivity] -> [LoadingActivity] (Status UI)`  
________________________________________
## 🧩 Detailed Class Description
### 📌 Class: MainActivity
*   **Role:** Main functional hub.
*   **Responsibility:** Displays the list of stations, handles user interactions, initiates API calls, and controls local playback.
*   **Key Methods:**
    *   `onCreate()`: Initializes UI and fetches data.
    *   `fetchRadioStations()`: Performs async OkHttp request to the Radio Browser API.
    *   `playRadio()`: Manages local MediaPlayer instance and UI state.
### 📌 Class: RadioService
*   **Role:** Background execution component.
*   **Responsibility:** Maintains audio playback even when the UI is not in the foreground.
*   **Key Methods:**
    *   `onStartCommand()`: Receives the stream URL and begins playback.
    *   `onDestroy()`: Releases resources when the service is stopped.
________________________________________
## 🔄 Application Workflow
1.  **Launch:** User selects a genre in `GenreActivity`.
2.  **Fetch:** `MainActivity` requests JSON data from the API.
3.  **Select:** User clicks a station in the `ListView`.
4.  **Buffer:** `LoadingActivity` and a high-priority Notification appear while the stream prepares.
5.  **Listen:** Audio starts; user can switch to background service mode or stop playback.
________________________________________
## 🎨 UI/UX Analysis
*   **Design Choice:** Linear layout with clear, large buttons and a standard list for familiarity.
*   **Principles:**
    *   *Simplicity:* Minimum number of clicks to reach content.
    *   *Logic:* Flow from genre selection to station list.
    *   *Accessibility:* High contrast and large text sizes (35sp for genre title).
________________________________________
## ⚙️ Threading
*   **Mechanisms Used:**
    *   `Thread` (for the loading timer).
    *   `OkHttp Async Callbacks` (for network requests).
    *   `runOnUiThread()` (for updating UI from background threads).
*   **Reasoning:** Ensures the main UI thread is never blocked during network or buffering operations.
*   **Prevention:**
    *   *ANR:* All I/O is off-loaded from the main thread.
    *   *Leaks:* MediaPlayer and Service resources are explicitly released in `onDestroy()`.
________________________________________
## 💾 Data Management
*   **Storage:** In-memory `ArrayList` objects for runtime efficiency.
*   **Reasoning:** The list of top stations is dynamic and changes frequently; persistent storage is not required.
________________________________________
## 🌐 Networking
*   **Library:** OkHttp 3.
*   **Mechanism:** Asynchronous requests with JSON parsing via `org.json`.
*   **Error Handling:** Toasts are displayed for "Error loading list" or "Data processing error".
________________________________________
## 🔐 Security
*   **Status:** Basic level. Uses standard Android permissions (`INTERNET`, `POST_NOTIFICATIONS`). No sensitive user data is collected.
________________________________________
## 🧪 Testing
*   **Unit Tests:** `ExampleUnitTest` (basic logic).
*   **UI Tests:** `ExampleInstrumentedTest` (context and package verification).
________________________________________
## 🐞 Error Handling
*   **Scenarios:** Network timeout, invalid stream URLs, null intents in Service.
*   **Response:** User-friendly Toasts and automatic UI state resets.
________________________________________
## 🚀 Scalability
*   **Future Growth:** Can be expanded to include user favorites (Room database), search functionality, and more complex filters (country, bitrate).
________________________________________
## 📊 Project Self-Assessment
| Criterion | Score (1–10) |
| :--- | :--- |
| Architecture | 8 |
| Code Quality | 9 |
| UI/UX | 8 |
| Reliability | 9 |
| **Overall Level** | **8.5** |
________________________________________
## 🏁 Conclusion
*   **Best Part:** Seamless integration of background services with high-priority notifications.
*   **Challenge:** Managing `MediaPlayer` states across Activity and Service lifecycles.
*   **Skills Gained:** Asynchronous API handling, Android Service management, and multi-activity state synchronization.
