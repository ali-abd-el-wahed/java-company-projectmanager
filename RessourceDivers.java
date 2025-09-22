import java.io.*;

public class RessourceDivers extends Ressource implements Serializable, Comparable<RessourceDivers> {
	 private static final long serialVersionUID = 1L;
	private static int next =1;
	private int id ;
	private String type;
    private int quantity;
    private double unitPrice;
    
    
    public RessourceDivers() {
    	super("");
        this.id = next++; // Assigner un nouvel ID unique
        this.type = "";  // Initialiser à une chaîne vide par défaut
   // Initialiser à une chaîne vide par défaut
        this.quantity = 0; // Initialiser le nombre d'heures travaillées à 0
        this.unitPrice = 0.0; // Initialiser le prix de l'heure à 0.0
    }
    public RessourceDivers(String n, String t, int qnt, double up ) {
        
    	super(n);
    	id = next ++ ;
    	type = t;
        quantity = qnt;
        unitPrice = up;
        
    }

    @Override
    public double calculCost() {
        return quantity * unitPrice;
    }
	
	//getters
		public String getType() { return type ; }
		public int getQuantity() { return quantity ; }
		public double getUP() { return unitPrice ; }
	
		//setters
		public void setType(String t) {  type = t ; }
		public void setQuantity(int qnt) {  quantity= qnt; }
		public void setUnitPrice(double up) {  unitPrice = up ; }

	    public String toString() {
	     return super.toString()+" "+type+" "+quantity+" "+unitPrice+ "\n";
	    
    }
	    
	    @Override
	    public int compareTo(RessourceDivers rd) {
	        
	        return this.getName().compareTo(rd.getName());
	    }
	    
}
