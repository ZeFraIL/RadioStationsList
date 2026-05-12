# Class Description: RadioService

## 1. General Information
*   **Class Name:** `RadioService`
*   **Type:** Service
*   **Assigning a class in an application:** This class is a background component that allows the radio to keep playing even if the user closes the main app screen or switches to another app.
*   **Interaction with other components:**
    *   Started by `MainActivity` via an `Intent` containing the stream URL.
    *   Stopped by `MainActivity` when the user clicks the "Stop Service" button.

## 2. Variables (Class Fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `mediaPlayer` | `MediaPlayer` | Handles the actual audio decoding and playback. | `onStartCommand()`, `onDestroy()` |

## 3. Classroom Methods
### Method name: `onStartCommand`
*   **Method Type:** `public`
*   **Return value:** `int` (Returns `START_STICKY`, which tells Android to restart the service if it gets killed by the system).
*   **Parameters:**
    | Name | Type | Description |
    | :--- | :--- | :--- |
    | `intent` | `Intent` | Contains the stream URL passed from the Activity. |
*   **What does the method do:**
    1.  Extracts the `STREAM_URL` from the intent.
    2.  Checks if a `mediaPlayer` is already running; if so, stops and releases it.
    3.  Initializes a new `MediaPlayer`.
    4.  Sets the audio stream type to `STREAM_MUSIC`.
    5.  Prepares the player asynchronously (`prepareAsync`) so it doesn't block the system.
    6.  Starts playing as soon as it is ready.
*   **When called:** Every time `startService()` is called in `MainActivity`.

## 4. Lifecycle
*   **`onStartCommand()`**: Triggered when the service is started. This is where the work begins.
*   **`onDestroy()`**: Triggered when `stopService()` is called. It ensures the music stops and the player is destroyed to save battery and memory.

## 5. Interaction with other components
*   Communicates with the Android OS to request resources for audio playback.
*   Uses `Intent` to receive the data needed to start the stream.

## 6. General Logic
The service acts as a "silent worker". It doesn't have its own screen. Its only job is to take a link, connect to it, and play audio until it is told to stop.

## 7. Simplified Explanation
Imagine `RadioService` is a **Background Musician**. 
`MainActivity` gives the musician a sheet of music (the URL) and says "Start playing". The musician keeps playing in the background even if you leave the room. He only stops when you come back and specifically tell him "Stop and go home".
