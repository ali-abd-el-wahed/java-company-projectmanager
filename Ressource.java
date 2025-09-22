import java.io.*;

public abstract class Ressource implements Serializable {
    private static final long serialVersionUID = 1L;
	String name ;
	
	public Ressource (String name ) {
		this.name = name ;
	}
	
	
	public String getName() {  return name ; }
	
	public void setName(String n) {  name = n ; }
	
	public String toString() {return "Name : " + name ;}
	
	public abstract  double calculCost();
}
