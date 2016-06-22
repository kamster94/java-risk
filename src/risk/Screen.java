package risk;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Random;

/**
 * Klasa panelu gry, g��wnego pola rozgrywki
 * @author kamster
 *
 */
@SuppressWarnings("serial")
public class Screen extends JPanel implements Runnable{

	public Thread thread = new Thread(this);
	Frame frame;
	
	//ilo�� p�l
	public static final int numX = 15;
	public static final int numY = 10;

	public static int selected; //id wybranego terytorium
	public static boolean transport; //tryb transportu
	public static boolean preparations; //tryb przygotowania
	public static boolean gameover; //tryb ko�ca gry
	
	public static int turn; //tura i gracze
	public Player player1;
	public Player player2;
	public static Player activePlayer; //nie inicjowany, przypisywani gracze 1 i 2
	public Level level; //mapa
	
	//przyciski
	public static JButton transportButton;
	public static JButton recruitButton;
	public static JButton turnButton;
	public static JButton startConfirm;
	
	/**
	 * Konstruktor tworz�cy panel w podanym oknie
	 * @param frame okno programu
	 */
	public Screen(Frame frame){
		this.frame = frame;
		setLayout(null);
		
		transportButton = new JButton("Transportuj");
		transportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("[debug] Transport army button pressed");
				//prze��czanie trybu transportu
				if (transport == false) {
					transportButton.setText("Anuluj");
					recruitButton.setEnabled(false);
					turnButton.setEnabled(false);
					transport = true;
				}
				else {
					transportButton.setText("Transportuj");
					recruitButton.setEnabled(true);
					turnButton.setEnabled(true);
					transport = false;
				}
				repaint();
			}
		});
		transportButton.setBounds(571, 523, 112, 23);
		add(transportButton);
		
		recruitButton = new JButton("Rekrutuj");
		recruitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//rekrutacja
				recruitArmy();
			}
		});
		recruitButton.setBounds(571, 557, 112, 23);
		add(recruitButton);
		
		turnButton = new JButton("Zako�cz tur�");
		turnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//koniec tury
				endTurn();
			}
		});
		turnButton.setBounds(571, 591, 112, 23);
		add(turnButton);
		
		startConfirm = new JButton("Potwierd�");
		startConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (gameover == true) frame.dispose();
				//potwierdzenie lokacji startowej
				Level.army[selected] = 1;
				Level.ownership[selected] = activePlayer.id;
				activePlayer.army = 1;
				activePlayer.moneyGain = Level.type[selected];
				activePlayer.terrains = 1;
				turn++;
				switchPlayers();
				if (turn == 1){
					preparations = false;
				}
				selected = 0;
				startConfirm.setEnabled(false);
				repaint();
			}
		});
		startConfirm.setBounds(393, 591, 128, 23);
		add(startConfirm);

		thread.start();
	}
	
	/**
	 * Rysowanie element�w okna
	 */
	public void paintComponent(Graphics g){
		//System.out.println("[debug]GUI is being redrawn");
		super.paintComponent(g);
		g.clearRect(0, 0, frame.getWidth(), frame.getHeight()); //czyszczenie warstwy
		
		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, frame.getWidth(), frame.getHeight()); //rysowanie t�a ekranu

		//rysowanie gui
		GUI.paintBackground(g, 0, 0); //t�o mapy
		GUI.paintGrid(g, 0, 0); //grid
		GUI.paintTerrainInfo(g, 25, 570); //info o polu
		GUI.paintTerrain(g, level, 0, 0); //terytoria
		if (preparations == true) GUI.paintPreparations(g, 250, 520); //informacja o przygotowaniu
		else if (gameover == true) GUI.paintGameOver(g, 250, 520);
		else { //zwyk�y tryb
			GUI.paintText(g, 250, 520); //statystyki gracza
			GUI.paintLog(g, 25, 530); //tury
			
		}
		if (transport == true) GUI.paintAdjected(g, selected); //jezeli w trybie transportu to mo�liwe ruchy
		GUI.paintSelectedTerrain(g, selected-1); //wybrane pole
		GUI.paintArmy(g); //cyferki armii
		/*if (dice == true)*/ 
	}
	
	/**
	 * aktualizacja wybranego terytorium
	 * @param x wspo�rz�dna x
	 * @param y wsp�rz�dna y
	 */
	public void updateSelected(int x, int y){ 
		int iterator = 1;
		for (int i = 0; i < 50*numY; i += 50){ //zewn�trzna p�tla po liniach
			for (int j = 0; j < 50*numX; j += 50){ //wewn�trzna po kolumnach
				if ((x > j + 1) && (x < j + 51) && (y > i + 1) && (y < i + 51)) { //je�eli klikni�cie nast�pi�o w obszarze, aktualizuj
					selected = iterator;
					return;
				}
				else iterator++; //w przeciwnym wypadku kontynuuj
			}
		}
	}
	
	/**
	 * rekrutacja jednostek
	 */
	private void recruitArmy(){
		//System.out.println("[debug] Recruit army button pressed");
		//wywo�anie slidera i przypisanie otrzymanej warto�ci
		int recruited = GUI.createPopUp(frame, activePlayer.money, 2);
		activePlayer.money -= recruited;
		activePlayer.army += recruited;
		Level.army[selected] += recruited;
		selected = 0;
		//ustawienie przycisk�w
		transportButton.setEnabled(false);
		recruitButton.setEnabled(false);
		this.repaint();
	}
	
	/**
	 * sprawdzenie, na kt�re pole nast�pi przeniesienie
	 * @param x pozycja x myszy
	 * @param y pozycja y myszy
	 */
	public void transportArmy(int x, int y){
		this.repaint();
		int selectedY = (selected-1) / 15;
		selectedY++; //numer linii
		int selectedX = selected - (selectedY-1) * 15; //numer kolumny
		selectedX = selectedX * 50 - 49; //policzenie origin point�w
		selectedY = selectedY * 50 - 49;
		//wyliczenie, gdzie nast�pi przeniesienie
		if (x>selectedX && x<selectedX+49 && y>selectedY-50 && y<selectedY-1) doTransport(selected-15,activePlayer.id,GUI.createPopUp(frame, Level.army[selected], 1)); //g�ra
		else if (x>selectedX-50 && x<selectedX-1 && y>selectedY && y<selectedY+49) doTransport(selected-1,activePlayer.id,GUI.createPopUp(frame, Level.army[selected], 1)); //lewo
		else if (x>selectedX+50 && x<selectedX+100 && y>selectedY && y<selectedY+49) doTransport(selected+1,activePlayer.id,GUI.createPopUp(frame, Level.army[selected], 1)); //prawo
		else if (x>selectedX && x<selectedX+49 && y>selectedY+50 && y<selectedY+100 && y<500) doTransport(selected+15,activePlayer.id,GUI.createPopUp(frame, Level.army[selected], 1)); //d�
	}
	
	/**
	 * faktyczne przeniesienie armii
	 * @param id id wskazanego pola
	 * @param player gracz kt�ry dokonuje przeniesienia
	 * @param army armia, jak� wybra�
	 */
	private void doTransport(int id, int player, int army){
		//obsluga terytorium zaj�tego przez wroga
		if(Level.ownership[id] != activePlayer.id && Level.ownership[id] != 0) { 
			if (army > 0) attackPlayer(id, player, army);
		}
		else {
			//wykonanie przeniesienia i policzenie parametr�w terenu poprzedniego i nowego
			if (army > 0) { //je�eli faktycznie nast�pi�o przeniesienie, dodaj armi� i zmie� prawa w�asno�ci
				Level.ownership[id] = player;
				//je�eli zaj�to nowy teren zaktualizuj info w parametrach gracza
				if (Level.army[id] == 0) {
					activePlayer.terrains++;
					activePlayer.moneyGain += Level.type[id];
				}
				Level.army[selected] -= army;
				Level.army[id] += army;
				activePlayer.remainingMoves--;
			}
			
			releaseEmptyTerrain();
			
			//wy��czenie trybu przenoszenia
			transport = false;
			selected = 0;
			//ustawienie normalnego trybu przycisk�w
			if(activePlayer.remainingMoves == 0) transportButton.setEnabled(false);
			else transportButton.setEnabled(true);
			turnButton.setEnabled(true);
			transportButton.setText("Transportuj");
			this.repaint();
		}
	}
	
	/**
	 * Je�eli przeniesiono ca�� armi�, zwolnij poprzedni teren
	 */
	private void releaseEmptyTerrain(){
		if (Level.army[selected] == 0) { 
			Level.ownership[selected] = 0;
			activePlayer.moneyGain -= Level.type[selected];
			activePlayer.terrains--;
		}
	}
	
	/**
	 * Obs�uga ataku na zaj�te terytorium, polega na wielokrotnym rzucie ko��mi sze�cio�ciennymi
	 * @param id id terytorium spornego
	 * @param player id gracza atakuj�cego
	 * @param army armia napastnika
	 */
	private void attackPlayer(int id, int player, int army){
		//tymczasowe warto�ci dla armii, b�d� si� zmienia�
		int armyPresent = Level.army[id];
		int newArmy = army;
		//czy wy�oniono zwyci�zc�
		boolean determined = false;
		//tablice kostek
		int[] diceAttack = new int[3];
		int[] diceDefense = new int[2];
		//losowa liczba
		Random random;
		//wynik cz�stkowy
		int result;
		//p�tla b�dzie si� wykonywa�, dop�ki nie zostanie wy�oniony zwyci�zca - tzn. kt�ra� armia nie zostanie unicestwiona
		while(determined == false){
			//zerowanie wyniku cz�stkowego
			result = 0;
			//generowanie rzut�w kostk�
			random = new Random();
			for(int i = 0; i < diceAttack.length; ++i)
			   diceAttack[i] = 1 + random.nextInt(6);
			for(int i = 0; i < diceDefense.length; ++i)
			   diceDefense[i] = 1 + random.nextInt(6);
			//sortowanie i por�wnywanie, atakuj�cy ma o jeden rzut wi�cej, zawsze brane s� najlepsze
			Arrays.sort(diceAttack);
			Arrays.sort(diceDefense);
			if (diceAttack[1] > diceDefense[0]) result++;
			else if (diceAttack[1] < diceDefense[0]) result--;
			if (diceAttack[2] > diceDefense[1]) result++;
			else if (diceAttack[2] < diceDefense[1]) result--;
			//wynik cz�stkowy waha si� od -2 do 2, je�eli jest 0 nie zachodz� zmiany, wychylenie (dowolne) powoduje zniszczenie jednej jednostki
			if (result > 0) armyPresent--;
			else if (result < 0) newArmy--;
			//zako�czenie, je�eli jedna z armii przestaje istnie�
			if (armyPresent == 0) determined = true;
			else if (newArmy == 0) determined = true;
		}
		//je�eli wygra� atakuj�cy
		if(armyPresent == 0){
			//zamiana graczy, by ustawi� parametry obro�cy
			switchPlayers();
			activePlayer.army -= Level.army[id];
			activePlayer.moneyGain -= Level.type[id];
			activePlayer.terrains--;
			switchPlayers();
			//powr�t do poprzeniego gracza i ustawienie parametr�w
			Level.army[selected] -= army;
			Level.ownership[id] = player;
			Level.army[id] = newArmy;
			activePlayer.terrains++;
			activePlayer.remainingMoves--;
			activePlayer.moneyGain += Level.type[id];
			activePlayer.army = activePlayer.army - (army - newArmy);
			releaseEmptyTerrain();
		}
		//je�eli wygra� obro�ca
		else if (newArmy == 0){
			//zamiana graczy, by ustawi� parametry obro�cy
			switchPlayers();
			activePlayer.army = activePlayer.army - (Level.army[id] - armyPresent);
			switchPlayers();
			//powr�t do poprzedniego gracza i ustawienie parametr�w
			Level.army[selected] -= army;
			Level.army[id] = armyPresent;
			activePlayer.remainingMoves--;
			activePlayer.army -= army;
			
			releaseEmptyTerrain();
		}
		//wy��czenie trybu przenoszenia
		transport = false;
		selected = 0;
		//ustawienie normalnego trybu przycisk�w
		if(activePlayer.remainingMoves == 0) transportButton.setEnabled(false);
		else transportButton.setEnabled(true);
		turnButton.setEnabled(true);
		transportButton.setText("Transportuj");
		this.repaint();
	}
	
	/**
	 * Zmiana aktywnego gracza na przeciwnego
	 */
	private void switchPlayers(){
		if (activePlayer.id == 1) activePlayer = player2;
		else if (activePlayer.id == 2) activePlayer = player1;
	}
	
	/**
	 * Operacje na koniec tury i rozpocz�cie kolejnej
	 */
	private void endTurn(){
		//System.out.println("[debug] Turn end button pressed");
		//ustawienie parametr�w ko�cz�cego gracza
		activePlayer.remainingMoves = 3;
		activePlayer.money += activePlayer.moneyGain;
		//przygotowanie pola dla nast�pnego
		turn++;
		selected = 0;
		transportButton.setEnabled(false);
		recruitButton.setEnabled(false);
		//sprawdzenie ko�ca gry dla bou graczy i zmiana graczy
		checkEndGame();
		switchPlayers();
		this.repaint();
	}
	
	/**
	 * Sprawdzenie, czy kt�ry� z graczy na koniec tury przygra� (ma 0 wojsk)
	 */
	private void checkEndGame(){
		if(activePlayer.army == 0) endGame();
		else {
			switchPlayers();
			if(activePlayer.army == 0) endGame();
			else switchPlayers();
		}
	}
	
	/**
	 * Ko�czenie gry
	 */
	private void endGame(){
		gameover = true;
		transportButton.setVisible(false);
		recruitButton.setVisible(false);
		turnButton.setVisible(false);
		startConfirm.setVisible(true);
		startConfirm.setEnabled(true);
	}
	
	/**
	 * Rozpocz�cie gry
	 */
	private void startGame(){
		//inicjalizacja mapy i graczy
		level = new Level("level1");
		player1 = new Player(1);
		player2 = new Player(2);
		activePlayer = player1;
		turn = -1;
		
		//tryb przygotowa� - ustawienie pozycji pocz�tkowych
		preparations = true;
		gameover = false;
		
		//wy��czenie zwyk�ych przycisk�w i w��czenie przycisku potwierdzenia
		transportButton.setVisible(false);
		recruitButton.setVisible(false);
		turnButton.setVisible(false);
		startConfirm.setEnabled(false);
		
		this.repaint();
		
		//oczekiwanie na zako�czenie przygotowa�
		while(preparations == true){
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//przywr�cenie normalnego funkcjonowania przycisk�w
		transportButton.setVisible(true);
		recruitButton.setVisible(true);
		turnButton.setVisible(true);
		startConfirm.setVisible(false);
		transportButton.setEnabled(false);
		recruitButton.setEnabled(false);
	}
	
	/**
	 * odpalenie w�tku
	 */
	public void run() {
		//System.out.println("[debug]Process is running");
		frame.validate(); //sprawdzenie poprawno�ci frame'u
		this.frame.addMouseListener(new MouseHandler(this)); //dodanie s�uchacza myszy
		startGame(); //uruchomienie rozgrywki
		
	}
}
