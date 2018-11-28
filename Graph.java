package Graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/*
* Joshua Alexander
* Graph.java
* COSC 336
* Homework #4
* Q1 & Q2: BFS/DFS and Diameter of Graph
 */
public class Graph {

    private ArrayList<Node> verticeList;

    // Constructor
    public Graph() {
        verticeList = new ArrayList<Node>();
    }

    // Add node to graph
    public void addNode(Node n) {
        verticeList.add(n);
    }

    // Returns node with with value of given integer
    public Node getNode(int num) {
        Node temp = null;

        for (int i = 0; i < verticeList.size(); i++) {
            if (verticeList.get(i).getData() == num) {
                temp = verticeList.get(i);
            }
        }

        return temp;
    }

    // Returns true if graph is empty (for input codition)
    public boolean isEmpty() {
        if (verticeList.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    // Clears the graph
    public void clearGraph() {
        verticeList.clear();
    }

    // Clears the visited proprty of the nodes
    public void clearVisited() {
        verticeList.forEach((Node) -> {
            Node.setVisited(false);
        });
    }

    // Returns true if graph contains node with given value, otherwise returns false.
    public boolean contains(int key) {
        for (int i = 0; i < verticeList.size(); i++) {
            if (verticeList.get(i).getData() == key) {
                return true;
            }
        }
        return false;
    }

    // Prints the all the nodes of the graph and their adjacent nodes.
    public void printGraph() {
        for (int i = 0; i < verticeList.size(); i++) {
            System.out.printf("\n%-8s", "Node: ");
            System.out.printf("%2d", verticeList.get(i).getData());
            System.out.print("   Adjacent: ");
            for (int j = 0; j < verticeList.get(i).getAdjacentList().size(); j++) {
                System.out.print(verticeList.get(i).getAdjacentList().get(j).getData() + ", ");
            }
        }
    }

    // Finds diameter of graph by running bfs on a start node, then again on the last node.
    public int findDiameter(int start) {
        Queue<Node> queue = new LinkedList<Node>();
        int last = bfs(start), diameter = 0;
        clearVisited();
        Node root = getNode(last), current, next;

        queue.add(root);
        root.setVisited(true);
        System.out.print("\nStart: " + root.getData());

        while (!queue.isEmpty()) {
            current = queue.remove();

            while ((next = current.getUnvisited()) != null) {
                if (!getNode(start).isVisited()) {
                    diameter++;
                }
                System.out.print(", " + next.getData());
                next.setVisited(true);
                queue.add(next);
            }
        }
        return diameter;
    }

    public void dfs(int start) {
        clearVisited();
        Stack<Node> stack = new Stack<>();
        Node root = getNode(start), next, tempNode = null;
        stack.push(root);

        System.out.print("Start: " + root.getData());

        while (!stack.isEmpty()) {
            tempNode = stack.peek();

            if ((next = tempNode.getUnvisited()) != null) {
                next.setParent(tempNode);
                next.setVisited(true);
                System.out.print(", " + next.getData());
                stack.push(next);
            } else {
                if (tempNode.getData() == start) {
                    System.out.print("*");
                    return;
                }
                System.out.print(", " + tempNode.getParent().getData() + "*");
                stack.pop();
            }
        }
    }

    public int bfs(int start) {
        clearVisited();
        Queue<Node> queue = new LinkedList<Node>();
        Node root = getNode(start), current, next;
        int last = 0;

        // adds start to queue
        queue.add(root);
        root.setVisited(true);
        System.out.print("Start: " + root.getData());

        while (!queue.isEmpty()) {
            current = queue.remove(); // removes node

            // gets unvisited of current, while it doesnt equal null continue
            while ((next = current.getUnvisited()) != null) {
                System.out.print(", " + next.getData());
                next.setVisited(true);
                queue.add(next); // adds to queue
                last = next.getData(); // marks last (for diameter method)
            }
        }
        return last;
    }
}

class Node implements Comparable<Node> {

    private int data;
    private Node unvisited, parent;
    private ArrayList<Node> adjacentList;
    private boolean visited;

    // constructor
    public Node(int num) {
        data = num;
        visited = false;
        parent = null;
        adjacentList = new ArrayList<Node>();
    }

    public int getData() {
        return data;
    }

    public ArrayList<Node> getAdjacentList() {
        return adjacentList;
    }

    public Node getUnvisited() {
        findNextUnvisited();
        return unvisited;
    }

    public Node getParent() {
        return parent;
    }

    public void setAdjacentList(PriorityQueue<Node> tempList) {
        adjacentList = new ArrayList<Node>();
        adjacentList.addAll(tempList);
        adjacentList.sort(null);
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int compareTo(Node x) {
        if (data == x.getData()) {
            return 0;
        } else if (data > x.getData()) {
            return -1;
        } else {
            return 1;
        }
    }

    public boolean isVisited() {
        return visited;
    }

    // find next smallest unvisited node
    public void findNextUnvisited() {
        unvisited = null;

        for (int i = 0; i < adjacentList.size(); i++) {
            if (!adjacentList.get(i).isVisited()) {
                unvisited = adjacentList.get(i);
            }
        }
    }
}
