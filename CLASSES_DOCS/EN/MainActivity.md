# Class Description: MainActivity

## 1. General Information
*   **Class Name:** `MainActivity`
*   **Type:** Activity
*   **Assigning a class in an application:** This is the main screen of the application. It is responsible for fetching the list of radio stations from the internet, displaying them to the user, and managing audio playback (either locally or via a background service).
*   **Interaction with other components:**
    *   Receives data (genre) from `GenreActivity` via `Intent`.
    *   Starts `LoadingActivity` to show progress to the user.
    *   Uses `RadioService` for background playback.
    *   Uses `NotificationHelper` to show system notifications.
    *   Uses `RadioStation` as a data model.

## 2. Variables (Class Fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `RADIO_BROWSER_API` | `String` | Constant containing the URL for the radio station API. | `fetchRadioStations()` |
| `listView` | `ListView` | UI element to display the list of stations. | `initComponents()` |
| `radioStations` | `ArrayList<RadioStation>` | List of objects containing detailed data for each station. | `fetchRadioStations()` |
| `stationNames` | `ArrayList<String>` | List of station names to be displayed in the `ListView`. | `initComponents()`, `fetchRadioStations()` |
| `mediaPlayer` | `MediaPlayer` | Android system component for playing audio files/streams. | `playRadio()`, `stopRadio()`, `onDestroy()` |
| `what_genre` | `String` | Stores the genre selected by the user in the previous screen. | `initComponents()`, `fetchRadioStations()` |

## 3. Classroom Methods
### Method name: `fetchRadioStations`
*   **Method Type:** `private`
*   **Return value:** `void` (nothing)
*   **Parameters:** None
*   **What does the method do:**
    1.  Creates an `OkHttpClient` to handle the network request.
    2.  Builds a `Request` using the API URL.
    3.  Asynchronously executes the request.
    4.  On success: Parses the JSON response into a list of `RadioStation` objects.
    5.  Filters stations based on the selected `what_genre`.
    6.  Updates the `ListView` adapter on the main thread using `runOnUiThread`.
*   **When called:** Automatically called during `initComponents()` when the Activity starts.

### Method name: `playRadio`
*   **Method Type:** `private`
*   **Return value:** `void`
*   **Parameters:** 
    * `streamUrl` (String): The URL of the radio stream.
    * `stationName` (String): The name of the station.
*   **What does the method do:**
    1.  Resets the `mediaPlayer` if it's already in use.
    2.  Shows a loading notification and opens the `LoadingActivity`.
    3.  Sets the data source for the `mediaPlayer`.
    4.  Calls `prepareAsync()` to buffer the stream without freezing the UI.
    5.  Once prepared, starts playback and hides the loading UI.
*   **When called:** When a user clicks "Yes" in the station selection dialog.

## 4. Lifecycle
*   **`onCreate()`**: Called when the Activity is first created. It sets the layout (`activity_main`) and calls `initComponents()`.
*   **`onDestroy()`**: Called when the Activity is being destroyed. It ensures that the `mediaPlayer` is released to prevent memory leaks and stop audio.

## 5. Interface Interaction (UI)
*   **Elements:** `ListView` (stations list), `Button` (Stop, Play Service, Stop Service), `TextView` (Genre display).
*   **Relation to code:** Connected using `findViewById(R.id.element_id)`.
*   **Events:** `OnItemClickListener` on the ListView to trigger the selection dialog; `OnClickListener` on buttons to control playback and services.

## 6. Interaction with other components
*   **Intents:** Uses `getIntent()` to read the genre from `GenreActivity`. Starts `RadioService` using `startService(intent)`.
*   **API:** Uses **OkHttp** library to communicate with `radio-browser.info`.

## 7. General Logic
The Activity starts, reads the selected genre, and immediately asks the server for the top 50 radio stations. It filters these stations and shows them in a list. When the user taps a station, they can choose to play it. Playback is handled by a `MediaPlayer`.

## 8. Simplified Explanation
Imagine `MainActivity` is like a **Radio Receiver with a Screen**. 
1. It looks at a "Genre Card" you gave it (from the previous screen).
2. It calls a "Global Radio Station" center via a phone (API) and asks for a list.
3. It writes the names on its screen.
4. When you point at a name, it starts tuning the radio (buffering) and then plays the music. If you leave the room but want the music to stay, it hands the task to a **Background Butler** (Service).
