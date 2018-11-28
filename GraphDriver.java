package Graphs;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
* Joshua Alexander
* GraphDriver.java
* COSC 336
* Homework #4
* Q1 & Q2: BFS/DFS and Diameter of Graph
 */
public class GraphDriver {

    public static void main(String[] args) throws NumberFormatException, FileNotFoundException, IOException {
        int cont = 1, cont2 = 1, start;
        Scanner scan = new Scanner(System.in);
        Graph g = new Graph();

        System.out.println("\tGraph");

        while (cont != 0) {
            g.clearGraph(); // clears graph
            input(g); // inputs from file to graph

            if (!g.isEmpty()) {
                g.printGraph(); // prints graph

                while (cont2 != 0) {
                    System.out.print("\n\nEnter int of start Node: ");
                    start = scan.nextInt(); // get start node value

                    if (g.contains(start)) {
                        cont2 = 0;

                        // runs bfs from start
                        System.out.println("\nBFS on Node " + start + ":");
                        g.bfs(start);

                        // runs dfs from start
                        System.out.println("\n\nDFS on Node  " + start + ":");
                        g.dfs(start);

                        // find diameter of graph
                        System.out.println("\n\nFinding Diameter: ");
                        System.out.println("\nDiameter of Graph: " + g.findDiameter(start));
                    } else {
                        System.out.println("\nStart int not found.");
                        System.out.print("Enter any integer to choose another start value or 0 to select new file: ");
                        cont2 = scan.nextInt();
                    }
                }
            }
            System.out.print("\nEnter any integer to select input file or 0 to exit: ");
            cont = scan.nextInt();
        }
    }

    // Opens a text file and inserts integers to graph
    public static void input(Graph tempGraph) throws IOException, FileNotFoundException, NumberFormatException {
        BufferedReader br;
        DataInputStream in;
        FileInputStream fis;
        JFileChooser openFile = new JFileChooser();
        FileNameExtensionFilter txtfilter = new FileNameExtensionFilter("Text Files", "txt", "text");

        openFile.setFileFilter(txtfilter);
        int returnVal = openFile.showOpenDialog(openFile);

        // If selected approve
        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            try {
                String line;
                Node tempNode, node;
                String[] lineArray;
                PriorityQueue<Node> tempList = new PriorityQueue<>();
                fis = new FileInputStream(openFile.getSelectedFile());
                in = new DataInputStream(fis);
                br = new BufferedReader(new InputStreamReader(in));

                // Add int to list while line is not empty
                while ((line = br.readLine()) != null) {
                    lineArray = line.split(" ");

                    // Creates node from first number of line
                    if (!tempGraph.contains(Integer.parseInt(lineArray[0]))) {
                        node = new Node(Integer.parseInt(lineArray[0]));
                        tempGraph.addNode(node);
                    }

                    // Creates adjacent nodes if they dont exist and adds them to tempList, if it does exist gets reference of existing
                    for (int i = 1; i < lineArray.length; i++) {
                        if (!tempGraph.contains(Integer.parseInt(lineArray[i]))) {
                            tempNode = new Node(Integer.parseInt(lineArray[i]));
                            tempGraph.addNode(tempNode);
                        } else {
                            tempNode = tempGraph.getNode(Integer.parseInt(lineArray[i]));
                        }

                        tempList.add(tempNode);
                    }

                    // sets the list of adjacent nodes for the first node of the line
                    tempGraph.getNode(Integer.parseInt(lineArray[0])).setAdjacentList(tempList);

                    // clears temp list for next input
                    tempList.clear();
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
    }
}
