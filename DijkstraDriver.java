package Graphs;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
* Joshua Alexander
* DijkstraDriver.java
* COSC 336
* Homework #4
* Q3: Dijkstra's Algorithm
 */
public class DijkstraDriver {

    public static void main(String[] args) throws NumberFormatException, FileNotFoundException, IOException {
        String start, dest;
        ArrayList<Edge> edgeList = new ArrayList<>();
        Scanner scan = new Scanner(System.in);

        int x = 0;
        int y = x++ + 1;
        System.out.println(y);
        System.out.println(x);
        System.out.println("\tDijkstra's Algorithm\n");

        edgeList.addAll(input());
        DGraph g = new DGraph(edgeList);

        System.out.print("Enter start node: ");
        start = scan.next().toUpperCase();

        System.out.print("Enter destination node: ");
        dest = scan.next().toUpperCase();

        g.dijkstra(start); // run algorithm
        g.printPath(dest); // prints the resulting path from start to destination, should be the smallest path between them
    }

    public static ArrayList<Edge> input() throws IOException, FileNotFoundException, NumberFormatException {
        BufferedReader br;
        DataInputStream in;
        FileInputStream fis;
        JFileChooser openFile = new JFileChooser();
        ArrayList<Edge> tempList = new ArrayList<>();
        FileNameExtensionFilter txtfilter = new FileNameExtensionFilter("Text Files", "txt", "text");

        openFile.setFileFilter(txtfilter);
        int returnVal = openFile.showOpenDialog(openFile);

        // If selected approve
        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            try {
                String line;
                String[] lineArray;
                fis = new FileInputStream(openFile.getSelectedFile());
                in = new DataInputStream(fis);
                br = new BufferedReader(new InputStreamReader(in));
                tempList.clear();

                while ((line = br.readLine()) != null) {
                    lineArray = line.split(" "); // divides line in text file up with spaces, puts values into array

                    // creates new edge with indexes 0, 1, 2, of line array
                    Edge e = new Edge(lineArray[0].toUpperCase(), lineArray[1].toUpperCase(), Integer.parseInt(lineArray[2]));
                    tempList.add(e);
                }
                br.close();
            } catch (FileNotFoundException ex) {
                System.out.println("File not found.");
            } catch (IOException ex) {
                System.out.println("Error reading file.");
            } catch (NumberFormatException ex) {
                System.out.println("Text file does not contain all integers.");
            }

        } else {
            System.out.println("No File Chosen.");
        }

        return tempList; // return list of edges
    }
}
