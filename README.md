# Simple Pacman

## System Requirements
>A working *Java environment*, [which can be set up here](https://www.oracle.com/technetwork/java/javase/downloads/index.html) <br/>
>*JavaFX* is also installed with this package

## Installation and Usage
1. Visit [the code repository](https://github.com/quickaccount/simple_pacman) <br/>
2. Download all files in the master-branch as a ZIP <br/>
3. Extract all files in the zipped folder <br/>
4. Find the simple_pacman-master/src/images folder. Move all images in this folder to the src folder. <br/>
5. Open a command prompt, change to proper directory: cd Downloads/simple_pacman-master/src <br/>
6. Compile: javac GameDisplay.java <br/>
7. Run: java GameDisplay <br/>
8. Use 'WASD' keys to move and play the game. 'P' can be used to save all progress

## JUnit Testing of Avatar Class
1. Follow *Installation and Usage* steps 1 - 5 <br/>
2. Compile the AI class: javac AI.java <br/>
3. Compile JUnit: <br/>
>Windows: javac -cp .;junit-4.12.jar;hamcrest-core-1.3.jar *.java <br/>
>Other: javac -cp .:junit-4.12.jar:hamcrest-core-1.3.jar *.java <br/>
4. Run JUnit: <br/>
>Windows: java -cp .;junit-4.12.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore AvatarTest <br/>
>Other: java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore AvatarTest <br/>

## Version History
**v1.0** <br/> 
Text-based completed <br/><br/>
**v2.0** <br/>
Text-based refined, AI improved <br/>
GUI-based nearly finished <br/><br/>
**v3.0** <br/>
GUI-based: save feature and end-game conditions added. Improved AI

## Authors
Daniel Stamper <br/>
Sydney Kwok <br/>
Marco Arias <br/>
Neil Sarmiento <br/>
Richard Gingrich
