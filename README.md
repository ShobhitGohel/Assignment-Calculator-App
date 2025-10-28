# 🧮 Assignment 8 - Android Calculator App  

## 📱 Overview  
This project is a **simple calculator app** built using **Kotlin** for Android.  
It performs basic arithmetic operations such as addition, subtraction, multiplication, and division, and also supports percentage calculations and decimal inputs.  
The app provides a clean UI with real-time expression previews and result updates.  

---

## ⚙️ Features  
- Perform arithmetic operations: **+, −, ×, ÷**  
- Handle **decimal** inputs correctly  
- Calculate **percentages** easily  
- Display **expression preview** and **final result**  
- Avoid invalid inputs like multiple dots or leading zeros  
- Handle **division by zero** safely (shows a Toast message)  
- Includes **Clear (C)** and **Backspace (⌫)** controls  
- Smooth and intuitive user experience  

---

## 🧩 Tech Stack  
- **Language:** Kotlin  
- **Framework:** Android SDK  
- **UI Layout:** XML (LinearLayout + GridLayout)  
- **IDE:** Android Studio  

---

## 🗂️ Project Structure  

**Main Components:**  
- **MainActivity.kt** — Handles the logic for calculations, input management, operator functions, and display updates.  
- **activity_main.xml** — Defines the user interface including the display area and calculator buttons.  
- **AndroidManifest.xml** — Declares the main activity and application settings.  

---

## 🧠 App Logic  

### 1. Input Handling  
- Numbers (0–9) and dot (`.`) buttons update the current input.  
- Prevents redundant zeros (e.g., “000”) and multiple decimal points.  
- Starts fresh input after calculation if a digit is pressed post “=”.  

### 2. Operator Handling  
- When an operator is pressed, it either stores the first value or performs the pending operation if one exists.  
- Supports chained operations like `5 + 6 - 2 × 3`.  

### 3. Equals (`=`) Operation  
- Computes the result based on current and stored operands.  
- Repeats the last operation on consecutive “=” presses.  

### 4. Percentage (`%`)  
- Converts the current number to its percentage (divides by 100).  

### 5. Clear and Backspace  
- **C** clears the entire expression and resets the state.  
- **⌫** removes the last digit or symbol.  

### 6. Display  
- **Expression Preview:** shows ongoing calculation (e.g., “5 + 2”).  
- **Result View:** displays the current input or final result.  

---

## 💡 Example Calculations  

| Input Sequence | Expression Display | Result |
|----------------|--------------------|--------|
| 5 + 2 = | 5 + 2 | 7 |
| 7 × 3 = | 7 × 3 | 21 |
| 9 ÷ 0 = | — | Toast → “Can’t divide by zero” |
| 50 % | — | 0.5 |

---

## 🧾 Manifest Configuration  
The app defines **MainActivity** as the launcher activity and applies the theme `Theme.Assignment8`.  
Backup and icon configurations are included for Android 12 and above compatibility.  

---

## 🧱 Layout Design  
- **LinearLayout (Vertical):** Used for stacking the display area and button grid.  
- **GridLayout (4x5):** Organizes calculator buttons in a structured, responsive layout.  
- Buttons use default margins and clean spacing for a neat appearance.  

---

## 🚀 How to Run  
1. Open the project in **Android Studio**.  
2. Sync Gradle and ensure Kotlin support is enabled.  
3. Connect an Android device or start an emulator.  
4. Click **Run ▶** to launch the app.  
5. Start performing calculations.  

---

## 👨‍💻 Author  
**Name:** ShobhU  
**Language:** Kotlin  
**Project:** Assignment 8 - Calculator  

---

## 🏁 Summary  
This calculator app showcases a clean implementation of:  
- Proper state management  
- Dynamic UI updates  
- Error handling  
- Modern Android development using Kotlin  

It’s a compact yet complete example of an interactive, responsive, and user-friendly Android calculator.
