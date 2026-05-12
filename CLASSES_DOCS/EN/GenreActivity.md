# Class Description: GenreActivity

## 1. General Information
*   **Class Name:** `GenreActivity`
*   **Type:** Activity
*   **Assigning a class in an application:** This is the entry point (Launcher) of the application. Its job is to let the user select which type of music they want to listen to before moving to the main list.
*   **Interaction with other components:**
    *   Starts `MainActivity` using an `Intent`.
    *   Passes the selected genre as an "extra" string to the next screen.

## 2. Variables (Class Fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `rbAll`, `rbJazz`, `rbNews` | `RadioButton` | Selection buttons for genres. | `initComponents()`, click listener |
| `bReady` | `Button` | The "Start" button to confirm selection. | `onCreate()` |
| `genre` | `String` | Stores the text value of the chosen genre. | click listener |

## 3. Classroom Methods
### Method name: `onCreate`
*   **Type:** `protected`
*   **What it does:** Sets up the screen, initializes the UI components, and defines what happens when the "Start" button is clicked.
*   **Logic:** When the button is clicked, it checks which `RadioButton` is selected, updates the `genre` variable, and sends it to `MainActivity`.

## 4. Lifecycle
*   **`onCreate()`**: The starting point where the layout `activity_genre` is loaded.

## 5. UI Interaction
*   **Elements:** `RadioGroup` with `RadioButton` options and a `Button`.
*   **Events:** Listens for a click on the `bReady` button.

## 6. General Logic
1. The app starts here.
2. The user sees three options: All, Jazz, or News.
3. The user picks one and clicks "Start".
4. The app "packs" the choice into a message (Intent) and opens the main screen.

## 7. Simplified Explanation
Imagine `GenreActivity` is the **Reception Desk** at a concert hall. Before you go inside to the stage (MainActivity), the receptionist asks you: "What kind of music do you like?". You tell them, they give you a pass, and then you enter the hall.
