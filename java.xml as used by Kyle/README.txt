Here are some source files from an Android game I built in CSCE 315. I was responsible for building a Java class which handled game state saving/loading. I did this using the java.xml library.

GameFileSystem contains two functions, loadGameState() and readGameState(), which can:
- serialize a GameState object (see GameState.java)
- save it to the SD card
- load it from the SD card
- deserialize it into an identical GameState object

This may be helpful for writing a desktop or mobile app with similar Java+XML functionality.

Kyle Fisher
9/30/2015