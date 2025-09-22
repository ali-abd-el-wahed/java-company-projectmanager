
import java.util.TreeSet;
import java.io.*;


public class Task implements Serializable, Comparable<Task> {
	 private static final long serialVersionUID = 1L;
	private static int next = 1 ;
	private int id ;
	private String name;
    private TreeSet<Processus> process;
    private double cost ;
    private String status ;
    private int duree ;
    
    public Task() {
        this.id = next++;
        this.name = "";
        this.process = new TreeSet<>(); // Initialiser TreeSet vide
        this.cost = 0.0;
        this.status = "";
        this.duree = 0;
    }

    
    public Task(String n , double co , String st , int d ) {
        
    	id = next ++ ;
    	name = n;
        process = new TreeSet<>();
        cost = co ;
        status = st ;
        duree = d ;
    
    }

    public void addProcessus(Processus process) {
        this.process.add(process);
    }


    
    
    public double calculTaskCost() {
        double totalCost = 0.0;
        for (Processus process : process) {
            totalCost += process.calculProcessusCost();
        }
        return totalCost;
    }
    public TreeSet<Processus> getProcesses() {
        return process;
    }

  
    public String getName() { return name ;}
    public double getCost() { return cost ;}
    public String getStatus() { return status ;}
    public int getDuree() { return duree;}
 
    
    public void setName(String n) { name = n ;}
    public void setCost(double c) { cost = c ;}
    public void setStatus(String s) { status = s ;}
    public void setDuree(int d) { duree = d ;}


    @Override
    public String toString() {
    	return  "  ID :  "+ id+ "     "+"  NAME :  "+  name + "     " +" COST :  "+  cost +" $ " + "     "+" DURATION :  "+  duree +" hour "+ "     "+" STATUS :  "+  status +"\n" ;}

    @Override
    public int compareTo(Task other) {
        return this.name.compareTo(other.name);
    }
}