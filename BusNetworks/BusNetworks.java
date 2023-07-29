// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2021T2, Assignment 6
 * Name: Ella Wipatene
 * Username: wipateella
 * ID: 300558005
 */

import ecs100.*;
import java.io.*;
import java.util.*;
import java.nio.file.*;

public class BusNetworks {

    /** Map of towns, indexed by their names */
    private Map<String,Town> busNetwork = new HashMap<String,Town>();
    boolean challenge = false; 

    /** CORE
     * Loads a network of towns from a file.
     * Constructs a Set of Town objects in the busNetwork field
     * Each town has a name and a set of neighbouring towns
     * First line of file contains the names of all the towns.
     * Remaining lines have pairs of names of towns that are connected.
     */
    public void loadNetwork(String filename) {
        try {
            busNetwork.clear();
            UI.clearText();
            List<String> lines = Files.readAllLines(Path.of(filename));
            String firstLine = lines.remove(0);
            Scanner sc = new Scanner(firstLine); 
            challenge = false; 
            
            while(sc.hasNext()){
                String town_name = sc.next(); 
                busNetwork.put(town_name, new Town(town_name)); 
            }
            
            for(String line: lines){
                Scanner scan = new Scanner(line); 
                String town_name = scan.next();
                String neighbour = scan.next(); 
                busNetwork.get(town_name).addNeighbour(busNetwork.get(neighbour));
                busNetwork.get(neighbour).addNeighbour(busNetwork.get(town_name)); 
            }

            UI.println("Loaded " + busNetwork.size() + " towns:");

        } catch (IOException e) {throw new RuntimeException("Loading data.txt failed" + e);}
    }

    /**  CORE
     * Print all the towns and their neighbours:
     * Each line starts with the name of the town, followed by
     *  the names of all its immediate neighbours,
     */
    public void printNetwork() {
        UI.println("The current network: \n====================");
        /*# YOUR CODE HERE */
        for(Town t: busNetwork.values()){
            UI.print(t.getName() + " -> ");
            for(Town n: t.getNeighbours()){
                UI.print(n.getName() + " "); 
            }
            UI.println("");
        }

    }

    /** COMPLETION
     * Return a set of all the nodes that are connected to the given node.
     * Traverse the network from this node in the standard way, using a
     * visited set, and then return the visited set
     */
    public HashSet<Town> findAllConnected(Town town, HashSet<Town> visited) {
        /*# YOUR CODE HERE */
        visited.add(town); 
        town.set_visited(true); 
        for(Town n: town.getNeighbours()){
            if(!visited.contains(n)){
                findAllConnected(n, visited); 
            }
        }
        
        return visited; 
    }

    /**  COMPLETION
     * Print all the towns that are reachable through the network from
     * the town with the given name.
     * Note, do not include the town itself in the list.
     */
    public void printReachable(String name){
        Town town = busNetwork.get(name);
        if (town==null){
            UI.println(name+" is not a recognised town");
        }
        else {
            UI.println("\nFrom "+town.getName()+" you can get to:");
            /*# YOUR CODE HERE */
            Set<Town> connected = findAllConnected(town, new HashSet<Town>()); 
            for(Town t: connected){
                UI.println(t.getName()); 
                t.set_visited(false); 
            }
        }

    }

    /**  COMPLETION
     * Print all the connected sets of towns in the busNetwork
     * Each line of the output should be the names of the towns in a connected set
     * Works through busNetwork, using findAllConnected on each town that hasn't
     * yet been printed out.
     */
    public void printConnectedGroups() {
        UI.println("Groups of Connected Towns: \n================");
        int groupNum = 1;
        ArrayList<HashSet<Town>> groups = new ArrayList<HashSet<Town>>(); 
        /*# YOUR CODE HERE */
        for(Town t: busNetwork.values()){
            if(!t.isVisited()){
                groups.add(findAllConnected(t, new HashSet<Town>()));
            }
        }
        
        for(HashSet<Town> set: groups){
            UI.print("Group " + groupNum + ": ");
            groupNum++; 
            for(Town t: set){
                UI.print(t.getName() + " ");
                t.set_visited(false); 
            }
            UI.println("");
        }
    }
    
    /**
     * Load in the challenge bus route file
     */
    public void loadChallenge(){
        try {
            busNetwork.clear();
            UI.clearText();
            String filename = UIFileChooser.open("Choose data file to process");
            List<String> lines = Files.readAllLines(Path.of(filename));
            challenge = true; 
            
            String firstLine = lines.remove(0);
            Scanner sc = new Scanner(firstLine); 
            int num_towns = sc.nextInt(); 
            int index = 0; 
            
            // Record the longitude and lat corrds 
            for(int i = 1; i <= num_towns; i++){
                Scanner scan = new Scanner(lines.get(i)); 
                String town_name = scan.next(); 
                double lat = scan.nextDouble();
                double lon = scan.nextDouble();  
                
                busNetwork.put(town_name, new Town(town_name)); 
                busNetwork.get(town_name).set_lat(lat);
                busNetwork.get(town_name).set_long(lon);
                index = i; 
            }
            
            // Record the connections
            while(index < lines.size()){
                Scanner scan = new Scanner(lines.get(index)); 
                String town_name = scan.next();
                String neighbour = scan.next(); 
                busNetwork.get(town_name).addNeighbour(busNetwork.get(neighbour));
                busNetwork.get(neighbour).addNeighbour(busNetwork.get(town_name));
                index++; 
            }
 
            UI.println("Loaded " + busNetwork.size() + " towns:");
        } catch (IOException e) {throw new RuntimeException("Loading data.txt failed" + e);}
    }
    
    /**  
     * Same as the core printing, but also prints out the longitude at lat
     */
    public void printChallenge() {
        if(challenge){
            UI.println("The current network: \n====================");
            /*# YOUR CODE HERE */
            for(Town t: busNetwork.values()){
                UI.print(t.getName() + " -lat: " + t.get_lat() + " -long: " + t.get_long() + " -> ");
                for(Town n: t.getNeighbours()){
                    UI.print(n.getName() + " "); 
                }
                UI.println("");
            }
        }
    }

    /**
     * Set up the GUI (buttons and mouse)
     */
    public void setupGUI() {
        UI.addButton("Load", ()->{loadNetwork(UIFileChooser.open());});
        UI.addButton("Load Challenge", this::loadChallenge);
        UI.addButton("Print Network", this::printNetwork);
        UI.addTextField("Reachable from", this::printReachable);
        UI.addButton("All Connected Groups", this::printConnectedGroups);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setWindowSize(1100, 500);
        UI.setDivider(1.0);
        loadNetwork("data-small.txt");
    }

    // Main
    public static void main(String[] arguments) {
        BusNetworks bnw = new BusNetworks();
        bnw.setupGUI();
    }

}
