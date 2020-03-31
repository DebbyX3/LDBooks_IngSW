
# LD Books - A sample Java software to simulate an online library

This is the final project for the 'Software Engineering' course at the University of Verona, during the second year of bachelor's degree in Computer Science.
It simulates an online library in Java: you can view the catalog, make orders, register and log in, view your profile, setting your data and view the weekly charts.

## Prerequisites

To try out the software, you'll need to set up:
- Java JDK 12 or newer
- JavaFX
- An SQLite driver library

### Java SDK 
See [here](https://jdk.java.net) to download the latest Java SDK version (the open source one).
Then use your Java IDE of choice, we used IntelliJ IDEA. Usually an IDE autodetects a Java SDK Package. 
If that's not the case, the IDE will tell you that a Java SDK is not found, and you should redirect it to the right path (usually the root folder of the Java SDK, called 'openjdk-xx').

### JavaFX
To set up *JavaFX*, you'll need to download the official SDK [here](https://gluonhq.com/products/javafx/).
Then, follow [this](https://openjfx.io/openjfx-docs/#install-javafx) guide to set up your IDE and adding the library to your classpath.

We used IntelliJ IDEA, if this is your IDE of choice, see [this](https://www.jetbrains.com/help/idea/javafx.html) guide.
More precisely, in IntelliJ IDEA you have to do the following steps:
- In the imported project (see the _installing_ paragraph to know how to import the project), go to File -> Project Structure... (ctrl + alt + shift + s)
- Go to the "modules" tab
- Click "+" and add the JavaFX lib folder. 
*Please, be careful: you don't have to add the root Java FX folder, but the 'lib' folder that you can find inside the root.* 
For example, if the Java FX folder is called 'javafx-sdk-11.0.0', you need to insert the following path:

```
precedent path\javafx-sdk-11.0.0\lib
```

Then, you need to add the VM option:
- Go to Run -> Edit Configurations...
- Select Application -> Main -> Configuration tab. If the 'main' application is not there, you need to add a new one using '+'. 
- In the 'VM option' write the following: 

```
debug javafx
--module-path
"PATH TO JAVAFX LIB FOLDER"
--add-modules=javafx.controls,javafx.fxml
```

Where in "PATH TO JAVAFX LIB FOLDER" you need to add the JavaFX path discussed before.

### SQLite driver
- Download the latest version of SQLite driver for Java [here](https://bitbucket.org/xerial/sqlite-jdbc/downloads/).
- Add the path to the driver in the project's classpath.

More precisely, for IntelliJ IDEA:
- In the imported project (see the _installing_ paragraph to know how to import the project), go to File -> Project Structure... (ctrl + alt + shift + s)
- Go to the "modules" tab
- Click "+" and add the SQLIte driver. 

### Installing

Clone or download the repository, then import it in your IDE of choice (the root folder for the project is LDBooks).
The main class is 'Main.java', you may need it to setup a new configuration.

There are already at least two users ready to be used and tested:
- Guest user - email: giuliarossi@gmail.com - password: qwerty
- Manager/Root user - email: giorgiogialli@gmail.com - password: giorgio

The following gif shows an order example:
![Demo](gif demo.gif)

## Built With

* [Scene Builder](https://gluonhq.com/products/scene-builder/) - The software used to design JavaFX interfaces through fxml
* [Apache ANT](https://ant.apache.org) - The default builder for Java

## Authors

* **Deborah Pintani** - [DebbyX3](https://github.com/DebbyX3)
* **Luca Marzari** - [LM095](https://github.com/LM095)

## Acknowledgments
*
