# Class Description: LoadingActivity

## 1. General Information
*   **Class Name:** `LoadingActivity`
*   **Type:** Activity
*   **Assigning a class in an application:** This is a secondary screen that appears while the radio stream is being prepared (buffered). It prevents the user from being confused by silence.
*   **Interaction with other components:**
    *   Started by `MainActivity` when a station is chosen.
    *   Closed either by a timer (3 seconds) or by a specific command from `MainActivity`.

## 2. General Logic
1. When a user clicks "Play", this screen pops up.
2. It shows a spinning progress bar and a "Loading" message.
3. It starts a background `Thread` that waits for 3 seconds and then automatically closes the screen.
4. It also has a special mode: if `MainActivity` sends an instruction to "close", it finishes immediately.

## 3. Classroom Methods
### Method name: `onCreate`
*   **What it does:** Sets the layout and starts a background thread.
*   **The Thread:** It uses `Thread.sleep(3000)` to wait. Then it uses `runOnUiThread` to call `finish()`, because you cannot close an Activity from a background thread directly.

## 4. Simplified Explanation
Imagine `LoadingActivity` is a **Waiting Room**. When you ask the doctor (the Radio) to see you, you are asked to sit in the waiting room for a moment. You see a sign saying "Please wait". Once the doctor is ready, the door opens and you go in (the music starts).
