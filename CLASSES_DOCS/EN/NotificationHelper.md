# Class Description: NotificationHelper

## 1. General Information
*   **Class Name:** `NotificationHelper`
*   **Type:** Helper/Utility Class
*   **Assigning a class in an application:** Responsible for creating and managing Android system notifications. It also plays a sound effect when a notification is shown.
*   **Interaction with other components:**
    *   Created and called by `MainActivity`.

## 2. Variables (Class Fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `CHANNEL_ID` | `String` | Unique ID for the notification category (required by Android 8+). | `createNotificationChannel()` |
| `mediaPlayer` | `MediaPlayer` | Used to play a short "gong" sound. | `playSound()`, `stopSound()` |

## 3. Classroom Methods
### Method name: `createNotificationChannel`
*   **What it does:** Registers a "channel" with the Android system. Modern Android phones require this to know how to handle notifications (priority, vibration, etc.).
*   **When called:** Once, when the `NotificationHelper` is first created.

### Method name: `showLoadingNotification`
*   **What it does:**
    1. Plays a "gong" sound.
    2. Builds a notification with a radio icon and the text "Radio station stream loading...".
    3. Displays it at the top of the user's phone screen.
*   **When called:** When the user selects a station to play.

## 4. Simplified Explanation
Imagine `NotificationHelper` is a **Town Crier**. When something important happens (like music starting to load), the crier rings a bell (plays a sound) and holds up a sign (the notification) so that you know what's going on even if you aren't looking at your phone's screen.
