# Class Description: RadioStation

## 1. General Information
*   **Class Name:** `RadioStation`
*   **Type:** Normal Class (POJO - Plain Old Java Object)
*   **Assigning a class in an application:** This is a data model class. It acts as a container to store information about a specific radio station.
*   **Interaction with other components:**
    *   Used by `MainActivity` to store and pass around station data.
    *   Created during JSON parsing in `fetchRadioStations`.

## 2. Variables (Class Fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `name` | `String` | The official name of the radio station. | Constructor, `getName()` |
| `streamUrl` | `String` | The direct link to the audio stream. | Constructor, `getStreamUrl()` |
| `bitrate` | `String` | Quality of the stream (e.g., "128"). | Constructor |
| `language` | `String` | Language spoken on the station. | Constructor |
| `homepage` | `String` | Website link for the station. | Constructor |

## 3. Classroom Methods
### Method name: `RadioStation` (Constructor)
*   **Method Type:** `public`
*   **Return value:** New instance of the class.
*   **What does the method do:** Assigns values to all the internal fields when a new station object is created.
*   **When called:** Inside `MainActivity` whenever a new station is discovered in the API response.

### Method names: `getName`, `getStreamUrl`, etc. (Getters)
*   **Method Type:** `public`
*   **Return value:** The value of the specific field (e.g., `String`).
*   **What does the method do:** Returns the private data stored in the object to other classes.

## 4. General Logic
This class doesn't "do" much on its own. It is like a file folder. It just sits there holding information so that other classes can find it easily.

## 5. Simplified Explanation
Imagine `RadioStation` is a **Business Card**. On the card, you have the station's name, its "phone number" (link), and its website. Instead of carrying loose scraps of paper, the app puts all this info on one neat card so it can find it later.
