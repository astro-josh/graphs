package Graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

/*
* Joshua Alexander
* DGraph.java
* COSC 336
* Homework #4
* Q3: Dijkstra's Algorithm
* Assistance from Bobby Smith (Classmate)
* Lambda guidance from mkyong.com and docs.oracle.com/javase/8/docs/api/java/util/Lambada
* Inspiration to use tree set from geeksforgeeks.org/dijkstras
 */
public class DGraph {

    private Map<String, DNode> graph; // map of the node names to the nodes

    // constructor
    public DGraph(ArrayList<Edge> edgeList) {
        graph = new HashMap<>(edgeList.size());

        // find the nodes
        for (Edge e : edgeList) {
            if (!graph.containsKey(e.getStart())) {
                graph.put(e.getStart(), new DNode(e.getStart()));
            }
            if (!graph.containsKey(e.getDest())) {
                graph.put(e.getDest(), new DNode(e.getDest()));
            }
        }

        // set neighbor nodes
        for (Edge e : edgeList) {
            graph.get(e.getStart()).neighbors.put(graph.get(e.getDest()), e.getDistance()); // gets edge, adds dest to neighbor map
            graph.get(e.getStart()).neighbors.put(graph.get(e.getStart()), e.getDistance()); // gets edge, adds origin to neighbor map
        }
    }

    // runs dijkstra using a specified source vertex
    public void dijkstra(String start) {
        int otherDistance;
        DNode startNode, destNode, source = graph.get(start);
        NavigableSet<DNode> nodeSet = new TreeSet<>();

        // create nodes
        for (DNode n : graph.values()) {
            n.setPrevious(n == source ? source : null); // sets the source for each node
            n.setDistance(n == source ? 0 : Integer.MAX_VALUE); // initializes distances to the max value (infinite)
            nodeSet.add(n); // adds the node to the set
        }

        // if the set isnt empty
        while (!nodeSet.isEmpty()) {
            startNode = nodeSet.pollFirst(); // shortest distance node
            if (startNode.getDistance() == Integer.MAX_VALUE) {
                return; // if its infinite then skip
            }
            // check distances for each adjacent nofde
            for (Map.Entry<DNode, Integer> a : startNode.neighbors.entrySet()) { // go through tree set
                destNode = a.getKey(); // current neighbors

                otherDistance = startNode.getDistance() + a.getValue(); // set other distance to the start distance plus the next
                if (otherDistance < destNode.getDistance()) { // shortest path
                    nodeSet.remove(destNode); // remove from set
                    destNode.setDistance(otherDistance); // set new smaller distance
                    destNode.setPrevious(startNode); // make previous the start
                    nodeSet.add(destNode); // add the destination node to tree set
                }
            }
        }
    }

    // prints path from the start to the end node
    public void printPath(String end) {
        graph.get(end).printPath();
    }
}

// node class
class DNode implements Comparable<DNode> {

    private String data;
    private int distance = Integer.MAX_VALUE; // make distances infinity
    private DNode previous = null;
    public Map<DNode, Integer> neighbors = new HashMap<>();

    public DNode(String s) {
        data = s;
    }

    // setters and getters
    public void setDistance(int dist) {
        distance = dist;
    }

    public void setPrevious(DNode prev) {
        previous = prev;
    }

    public String getData() {
        return data;
    }

    public int getDistance() {
        return distance;
    }

    public DNode getPrevious() {
        return previous;
    }

    // prints tjhe path of nodes
    public void printPath() {
        if (this == previous) {
            System.out.printf("%s", data);
        } else if (previous == null) {
            System.out.printf("%s(unreached)", data);
        } else {
            this.previous.printPath();
            System.out.printf(" >> %s Total: %d", data, distance);
        }
    }

    // compare distance of currentnode and x node
    public int compareTo(DNode x) {
        if (distance == x.distance) {
            return data.compareTo(x.data);
        } else {
            return Integer.compare(distance, x.distance);
        }
    }
}

// edge class
class Edge {

    private String start, dest;
    private int distance;

    public Edge(String s, String d, int dist) {
        start = s;
        dest = d;
        distance = dist;
    }

    public String getStart() {
        return start;
    }

    public String getDest() {
        return dest;
    }

    public int getDistance() {
        return distance;
    }
}
