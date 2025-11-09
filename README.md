## Virtual Pet Simulation Game

This project is a virtual pet system built using object-oriented programming principles in Java. It models various types of pets — regular animals, dragons, and robots — each with unique traits, stats, and behaviors.

### Overview

The system simulates the lifecycle and interactions of virtual pets. Each pet has attributes such as:

* Name, species, and age
* Health and happiness stats
* Evolution stages
* Special abilities or conditions (for example, dragons breathe fire and robots have batteries)

You can feed, play with, and monitor pets as they evolve or react to different actions.

### Core Components

* **`PetSpecies`** – Defines a species’ name, habitat, lifespan, and evolution stages.
* **`VirtualPet`** – The main class that tracks a pet’s stats (health, happiness, age) and handles actions like feeding and playing.
* **`DragonPet`** – A fantasy pet built on `VirtualPet`, featuring unique dragon traits like breath type and dragon category.
* **`RobotPet`** – A technology-based pet that includes battery levels, charging logic, and system state.

### Features

* Immutable species data for consistency
* Pet evolution triggered by happiness and health levels
* Battery management system for robotic pets
* Demonstrates Java OOP concepts such as encapsulation, composition, immutability, and class interaction
