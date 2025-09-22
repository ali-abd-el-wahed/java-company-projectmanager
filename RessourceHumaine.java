import java.io.*;



public class RessourceHumaine extends Ressource implements Serializable, Comparable<RessourceHumaine> {
	 private static final long serialVersionUID = 1L;
	private static int next = 1 ;
	private int id ;
	private String function;
	private String special;
    private int workedHour;
    private double hourPrice;
    
    public RessourceHumaine() {
        super(""); // Appelle le constructeur parent avec un nom par défaut
        this.id = next++;
        this.function = "";
        this.special = "";
        this.workedHour = 0;
        this.hourPrice = 0.0;
    }
    public RessourceHumaine(String n, String f, String s , int wh, double hp ) {
       
    	
    	
    	super(n);
    	id = next ++ ;
    	function = f;
        special = s ;
        workedHour = wh;
        hourPrice = hp;
        
    }

    @Override
    public double calculCost() {
        return workedHour * hourPrice;
    }
	
	//getters
	public String getFunction() { return function; }
	public String getSpecial() { return special; }
	public double getHP() { return hourPrice ; }
	public int getWH() { return workedHour ; }
	
	//setters
	public void setFunction(String f) {  function = f ; }
	public void setSpecial(String s) {  special = s ; }
	public void setHP(double hp) {  hourPrice = hp; }
	public void setWH(int wh) {  workedHour= wh; }

    public String toString() {
     return super.toString()+" "+function+" "+special +" " +workedHour+" "+hourPrice+ "\n";
    }
    
    @Override
    public int compareTo(RessourceHumaine rh) {
        
        return this.getName().compareTo(rh.getName());
    }
    
}
