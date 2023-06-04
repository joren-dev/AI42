# AI42
Enables 42.nl to use their AI with ease.

## Prerequisites
1. Install [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download/), with the following options:
    - Language: Java
    - JDK Version: 11 or above
    - Build System: IntelliJ
2. Install [Git Bash](https://gitforwindows.org/) (for windows), alternatively use: Windows Powershell or CMD.
3. Download [JavaFX](https://openjfx.io/) (for windows) and unpack it into `C:/Java`.
4. Download and install [SceneBuilder](https://gluonhq.com/products/scene-builder/).

## UML Diagram
![UML class diagram example](https://raw.githubusercontent.com/joren-dev/AI42/main/docs/uml/AI42.png)


## GUI Showcase
![GUI preview gif](https://raw.githubusercontent.com/joren-dev/AI42/main/docs/preview/GUI%20Preview.gif)

## Functionality
- Chat Interface: Engage in interactive and dynamic conversations with the www.42.nl AI model.
    - Example: Have real-time conversations with the AI model, receiving dynamic responses based on user input.

- GUI Interface: Provide a graphical user interface for seamless interaction.
    - Example: Offer a user-friendly interface with intuitive controls, allowing users to interact with the AI model easily.

- Login System: Implement a secure login system to authenticate users.
    - Example: Require users to create accounts and log in before accessing the AI's functionality for personalized experiences.

- User Profiles: Allow users to create and manage profiles within the application.
    - Example: Enable users to customize their preferences, save conversation histories, or set individual chat preferences.

- Chat History: Store and display a history of previous conversations.
    - Example: Save and display past chat sessions, allowing users to refer back to previous interactions.

- Customization Options: Offer customization features to personalize the AI experience.
    - Example: Allow users to choose chat themes, adjust font sizes, or modify language settings to suit their preferences.

## Common dev-commands explained
### 1. Make text strong (example)
1. Put this in front of your string: `\033[1m`
2. Put this after your string: `\033[0m`

Example: `System.out.println("\033[1mtekst\033[0m");`
