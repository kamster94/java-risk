package risk;

/**
 * Klasa, zawieraj�ca zmienne przechowuj�ce informacje o graczu. Tworzone s� dwie instancje tego typu i jeden wska�nik
 * @author kamster1
 *
 */
public class Player {
	
	//parametry gracza
	public int money; 
	public int terrains;
	public int army;
	public int id;
	public int remainingMoves;
	public int moneyGain;
	
	public String name;
	
	/**
	 * inicjalizacja gracza
	 * @param id id gracza, na jego podstawie tworzy te� nazw�
	 */
	public Player(int id){
		this.id = id;
		name = "Gracz " + id;
		money = 5;
		terrains = 0;
		army = 0;
		remainingMoves = 3;
		moneyGain = 0;
	}
}
