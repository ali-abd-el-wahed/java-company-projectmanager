
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.util.TreeSet;
import java.io.*;

import javax.swing.JButton;
import javax.swing.JOptionPane;


public class Project   implements Serializable ,Comparable<Project> {
	 private static final long serialVersionUID = 1L;
	private static int next =1 ;
    private int id;
    private String name;
    private TreeSet<Task> task;
    private double cost ; 
    private String status;
    private int duree;
    
    
    // Constructeur par défaut
    public Project() {
        this.id = next++;
        this.name = "";
        this.task = new TreeSet<>(); // Initialiser TreeSet vide
        this.cost = 0.0;
        this.status = "";
        this.duree = 0;
    }

    public Project(String n ,double co , String st , int d) {
        id = next++ ;
        name = n;
        task = new TreeSet<>();
        cost = co ;
        status = st ;
        duree = d ;
    }

    public void addTask(Task task) {
        this.task.add(task);
    }
    
    public String getName() { return name ;}
    public double getCost() { return cost ;}
    public String getStatus() { return status ;}
    public int getDuree() { return duree;}
    
    
    public void setName(String n) { name = n ;}
    public void setCost(double c) { cost = c ;}
    public void setStatus(String s) { status = s ;}
    public void setDuree(int d) { duree = d ;}
    
    public double calculateProjectCost() {
        double totalCost = 0.0;
        for (Task task : task) {
            totalCost += task.calculTaskCost();
        }
        return totalCost;
    }

    public TreeSet<Task> getTasks() {
        return task;
    }

    @Override
    public String toString() {
        return  "  ID :  "+ id+ "     "+"  NAME :  "+  name + "     " +" COST :  "+  cost +" $ " + "     "+" DURATION :  "+  duree +" hour "+ "     "+" STATUS :  "+  status +"\n" ;}
    
    @Override
    public int compareTo(Project other) {
        return this.name.compareTo(other.name);
    }
    
    



}



