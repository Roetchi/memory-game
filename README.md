# Memory Matching Game (Java, Swing)

## Overview

My Memory Matching Game is a simple GUI-based card matching game written in Java.  
I made it as an individual project for the **B202 Advanced Programming** module to demonstrate the use of **Object-Oriented Programming (OOP)**, **event-driven programming**, and **Java Swing** for graphical user interfaces.

The goal of the game is to find all matching pairs of cards in the fewest number of moves and in the shortest possible time.

## Features

- 🃏 **Emoji cards** – each card hides an emoji symbol until it is flipped.
- 🎚️ **Three difficulty levels**
  - Easy: 2 × 2 grid  
  - Medium: 4 × 4 grid  
  - Hard: 6 × 6 grid
- 🖥️ **Graphical User Interface** built using Java Swing
- ⏱️ **Timer** that counts how many seconds the game has been running for
- 🔢 **Move counter** that counts the number of attempts made
- ✅ **Pairs found counter** that counts the number of successful matches made
- 🔒 **Click locking** while two cards are being compared, it blocks the player from breaking the game by clicking too fast
- 🎉 **Win dialog** that shows total moves and time when all pairs are found

## Technologies Used

- **Language:** Java
- **Libraries/Frameworks:**  
  - Java Swing (`JFrame`, `JPanel`, `JButton`, `JLabel`, `JOptionPane`)  
  - `javax.swing.Timer` for the game timer
- **Data structures:** 2D arrays (`Card[][]`) and `ArrayList<Integer>` with `Collections.shuffle()` for random card distribution

## How to Run

### 1. Clone or download my project

My project can be downloaded through GitHub or copied.

git clone https://github.com/roetchi/memory-game.git
