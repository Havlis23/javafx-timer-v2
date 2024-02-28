# Časovac9000

This is a simple countdown timer application built with JavaFX. The application allows you to set a countdown timer in hours, minutes, and seconds. When the countdown reaches zero, a sound is played.

## Features

- Set countdown timer in hours, minutes, and seconds
- Start, pause, and stop the countdown
- Sound notification when the countdown reaches zero
- User-friendly interface with smooth transitions

## Prerequisites

- Java 11 or higher
- JavaFX 11 or higher
- Maven

## Building and Running the Application

1. Clone the repository:

```bash
git clone https://github.com/DavidHavel233/casovac9000.git
```

2. Navigate to the project directory:

```bash
cd casovac9000
```

3. Build the project using Maven:

```bash
mvn clean install
```

4. Run the application:

```bash
java -jar target/casovac9000-1.0-SNAPSHOT.jar
```

## Project Structure

The project follows a standard Maven project structure. The main application code is located in `src/main/java/com/davidhavel/casovac9000`.

- `HelloApplication.java`: This is the main entry point of the application. It sets up the JavaFX application and loads the main view.
- `HelloController.java`: This class handles all the user interactions with the application. It includes methods for starting, pausing, stopping, and resetting the countdown timer.
- `module-info.java`: This file defines the module structure of the application.

## Contributing

Contributions are welcome. Please open an issue or submit a pull request on GitHub.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
