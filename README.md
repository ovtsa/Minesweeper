# Minesweeper

A clone of the classic Minesweeper game written in Java. 

GUI designed in JavaFX. Desktop application runs in an MVC framework.

## Getting Started

After a successful build, this game comes in a packaged .jar file that can be moved around a filesystem without causing problems. 

### Prerequisites

There are four pieces of software you will need to compile and run this software on your own.

1. A command line program, like cmd or terminal
2. Git
3. JDK (min version undetermined as of 2/21/2020)
4. Apache Ant

### Installing

Open your command line interface and enter the following command.

```
git clone https://github.com/ovtsa/Minesweeper
```

This will create a directory named Minesweeper. Enter the directory with one of the following commands:

1. MacOS/Linux

```
cd ./Minesweeper
```

2. Windows

```
cd .\Minesweeper
```

Now, build the Minesweeper.jar file with this command.

```
ant
```

### Deployment

From the current directory you can easily run the game with the following command:

```
ant run
```

The Minesweeper.jar file in the `dist` directory can also be directly executed.

To recompile the project, run the following two commands:

```
ant clean
ant
```

## Built With
* JavaFX - the library used to create graphics
* Apache Ant - the software used for handling dependencies and building

## Authors

* Nathan Jobe

## License

This project is licensed under the MIT License - see the LICENSE file for details

## Acknowledgments

Special thanks to Sebastian Fuehr (@KrokodileDandy) for testing advice on my first large project
