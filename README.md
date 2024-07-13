# GroceryListApp

GroceryListApp is a simple Android application for creating and managing a grocery list. User needs to enter the quantity and Item name which will be then added to the list

## Features

- Add items with a specified quantity to the grocery list
- Delete items from the grocery list
- Enabling/disabling of the add button based on whether user has added any data or not
- Custom adapter for displaying grocery items (as normal list view didn't look very appealing visually)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Minimum Android Version: Android 7.0 (Nougat) (API level 24)
- Target Android Version: Android 14 (API level 34)
- Compile SDK Version: 34
- Java Version: 1.8
- Android Studio Koala | 2024.1.1
- An Android device or emulator running Android 7.0 (Nougat) or higher

### Installing

1. **Clone the repository:**

    ```bash
    git clone https://github.com/yourusername/GroceryListApp.git
    ```

2. **Open the project in Android Studio:**

    - Open Android Studio
    - Click on `File -> Open`
    - Navigate to the cloned repository and open it

3. **Build the project:**
    - Click on `Build -> Clean Project` to build the project
    - Click on `Build -> Rebuild Project` to build the project

4. **Run the app:**

    - Click on `Run -> Run 'app'` to run the application on an emulator or connected device (Make sure to configure a device using the device manager either an emulator or mobile phone)

## Usage

1. **Add an item:**

    - Enter the item name in the text field
    - Select the quantity from the dropdown spinner
    - Click the `Add Item` button to add the item to the list

2. **Delete an item:**

    - Click the `Delete` button next to the item in the list to remove it

## Code Overview

- **MainActivity:** The main activity of the application that handles the UI components and user interactions.
- **GroceryAdapter:** A custom adapter to manage the display of grocery items in the `ListView`.

### MainActivity

The `MainActivity` initializes the UI components, sets up the adapters, and handles user interactions such as adding and deleting items from the list.

### GroceryAdapter

The `GroceryAdapter` extends `ArrayAdapter<Pair<String, String>>` and provides a custom view for each item in the grocery list. It handles the display of the item name, quantity, and delete button.

## Acknowledgments
- https://developer.android.com/reference/kotlin/android/widget/Adapter
- https://www.geeksforgeeks.org/kotlin-android-tutorial/
- https://www.javatpoint.com/kotlin-tutorial
- https://www.udemy.com/course/kotlin-masterclass-learn-kotlin-from-zero-to-advanced/learn/lecture/32602270?start=75#overview
