/**
Locations are stored as (x, y) coordinates char grid[y][x]
char values are used to denote objects
'.' = coin
',' = no coin
'X' = wall
'A' = Avatar
'E' = Enemy
The initial game state is made by parsing a text file
*/
import java.util.Scanner;
import java.io.*;

public class Locations {

    private int NUM_COL = 23;
    private int NUM_ROWS = 17;
    private char[][] grid = new char[NUM_COL][NUM_ROWS];
    public Locations() {
        // The name of the file containing the display template.
        String fileName = "default_display.txt";

        // Line Reference
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader template = new FileReader(fileName);

            BufferedReader bTemplate = new BufferedReader(template);
            int i = 0;
            while((line = bTemplate.readLine()) != null) {
                    for(int k=0; k<NUM_COL; k++){
                        grid[k][i] = line.charAt(k);
                    }
                    i++;
            }

            // Close default_display.txt
            bTemplate.close();
        }
        // Error checking
        catch(FileNotFoundException ex) {
            System.out.println(
                "Cannot open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }
}

