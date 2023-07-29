// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2021T2, Assignment 6
 * Name: Ella Wipatene
 * Username: wipateella
 * ID: 300558005
 */

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import ecs100.*;

public class Town {

    private String name;
    private Set<Town> neighbours = new HashSet<Town>();
    private boolean visited = false; 
    private double latitude;
    private double longitude; 

    public Town(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Set<Town> getNeighbours() {
        return Collections.unmodifiableSet(neighbours);
    }

    public void addNeighbour(Town node) {
        neighbours.add(node);
    }

    public String toString(){
        return name+" ("+neighbours.size()+" connections)";
    }
    
    /**
     * Setters for visited, longitude and latitude
     */
    public void set_visited(boolean n){visited = n;}
    public void set_lat(double lat){this.latitude = lat;}
    public void set_long(double lon){this.longitude = lon;}
    
    /**
     * Getters for visited, longitude and latitude
     */    
    public boolean isVisited(){return visited;} 
    public double get_lat(){return this.latitude;}
    public double get_long(){return this.longitude;}
}
