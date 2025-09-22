
import java.util.TreeSet;
import java.io.*;

public class Processus implements Serializable, Comparable<Processus>  {
	 private static final long serialVersionUID = 1L;
	private static int next = 1 ;
	private int id ;
	private String name;
    private TreeSet<RessourceMaterielle> mat;
    private TreeSet<RessourceHumaine> emp;
    private TreeSet<RessourceDivers> div;
    private double cost ;
    private String status ;
    private int duree ;
    
    public Processus() {
        this.id = next++;
        this.name = "";
        this.mat = new TreeSet<>(); // Initialiser TreeSet vide
        this.emp = new TreeSet<>(); // Initialiser TreeSet vide
        this.div = new TreeSet<>(); // Initialiser TreeSet vide
        this.cost = 0.0;
        this.status = "";
        this.duree = 0;
    }

    public Processus(String n ,double co , String st , int d) {
       
    	id = next ++ ;
    	name = n;
        mat = new TreeSet<>();
        emp = new TreeSet<>();
        div = new TreeSet<>();
        cost = co ;
        status = st ;
        duree = d ;
    }

    public void addResource(RessourceMaterielle resource) {
        mat.add(resource);
    }

    public void addResource(RessourceHumaine resource) {
        emp.add(resource);
    }

    public void addResource(RessourceDivers resource) {
        div.add(resource);
    }


    public double calculProcessusCost() {
        double totalCost = 0.0;
        for (RessourceMaterielle rm : mat) {
            totalCost += rm.calculCost();
        }
        for (RessourceHumaine rh : emp) {
            totalCost += rh.calculCost();
        }
        for (RessourceDivers rd : div) {
            totalCost += rd.calculCost();
        }
        return totalCost;
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
    public int compareTo(Processus other) {
        return this.name.compareTo(other.name);
    }
}