# Simple Pacman

## System Requirements
>A working *Java environment*, [which can be set up here](https://www.oracle.com/technetwork/java/javase/downloads/index.html) <br/>
>*JavaFX* is also installed with this package

## Installation and Usage
1. Visit [the code repository](https://github.com/quickaccount/simple_pacman) <br/>
2. Download all files in the master-branch as a ZIP <br/>
3. Extract all files in the zipped folder, ensuring that no new folder is created <br/>
>Example) extract to C:\Users\YourUsername\Downloads\ **not** C:\Users\YourUsername\Downloads\simple_pacman-master
4. Locate the <br/>>simple_pacman-master/src/images <br/>and <br/>>simple_pacman-master/src/sounds <br/>folders. Move all files in both of these folders to the src folder. All images and sound files should now be in the src folder <br/>
5. Open a command prompt, change to proper directory: cd Downloads/simple_pacman-master/src <br/>
6. Compile: javac GameDisplay.java <br/>
7. Run: java GameDisplay <br/>
8. Start the game with 'N', or load a previous save with 'L' <br/>
9. Use 'WASD' keys to move and play the game. 'P' can be used to save all progress

## JUnit Testing of Avatar Class
1. Follow *Installation and Usage* steps 1 - 5 <br/>
2. Compile the Avatar class: javac Avatar.java <br/>
3. Compile JUnit: <br/>
>Windows: javac -cp .;junit-4.12.jar;hamcrest-core-1.3.jar *.java <br/>
>Other: javac -cp .:junit-4.12.jar:hamcrest-core-1.3.jar *.java <br/>
4. Run JUnit: <br/>
>Windows: java -cp .;junit-4.12.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore AvatarTest <br/>
>Other : java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore AvatarTest <br/>

## Version History
**v1.0** <br/> 
Text-based completed <br/><br/>
**v2.0** <br/>
Text-based refined, AI improved <br/>
GUI-based nearly finished <br/><br/>
**v3.0** <br/>
GUI-based: save feature, main-menu and end-game conditions added. Improved AI

## Authors
Daniel Stamper <br/>
Sydney Kwok <br/>
Marco Arias <br/>
Neil Sarmiento <br/>
Richard Gingrich
