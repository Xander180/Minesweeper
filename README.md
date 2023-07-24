# Minesweeper
A minesweeper replica played within your computer's console.

Player inputs how many mines they want placed in the field, and player chooses a cell using x and y coordinates, followed by a command (free to free a space and adjacent free spaces, mine to mark a potential mine location)

Program uses a flood-fill algorithm, based on the recursion method (recalling the free space function within itself multiple times to check all 8 directions from the beginning coordinate)

Player wins by finding all the free spaces, or by marking all the mine locations.

Player loses by marking a mine location as free.

To run the program in console, navigate to main program directory within console

Type (or copy/paste):
  java -jar Main.jar
