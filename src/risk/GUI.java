package risk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Klasa zawieraj¹ca metody rysuj¹ce interfejs, nie jest instancjonowana
 * @author kamster
 *
 */
public class GUI {
	
	/**
	 * Rysowanie t³a okna
	 * @param g grafika
	 * @param x wspo³rzêdna x
	 * @param y wspó³rzêdna y
	 */
	public static void paintBackground(Graphics g, int x, int y){
		g.drawImage(new ImageIcon("textures/background.png").getImage(), x, y, null);
	}
	
	/**
	 * Rysowanie siatki grid'u
	 * @param g grafika
	 * @param x wspo³rzêdna x
	 * @param y wspó³rzêdna y
	 */
	public static void paintGrid(Graphics g, int x, int y){
		g.setColor(Color.GRAY);
		for (int i = 0; i < Screen.numX; i++)
			for(int j = 0; j < Screen.numY; j++)
				g.drawRect(x + (i*50), y + (j * 50), 50, 50);
	}
	
	/**
	 * rysowanie statystyk gracza
	 * @param g grafika
	 * @param x wspo³rzêdna x
	 * @param y wspó³rzêdna y
	 */
	public static void paintText(Graphics g, int x, int y){
		g.setColor(Color.BLACK);
		g.drawString("Zasoby: " + Screen.activePlayer.money, x, y+10);
		g.drawString("Wojska: " + Screen.activePlayer.army, x, y+30);
		g.drawString("Terytoria: " + Screen.activePlayer.terrains, x, y+50);
		g.drawString("Pozosta³e ruchy wojsk: " + Screen.activePlayer.remainingMoves, x, y+70);
	}
	
	/**
	 * rysowanie informacji na pocz¹tku gry
	 * @param g grafika
	 * @param x wspo³rzêdna x
	 * @param y wspó³rzêdna y
	 */
	public static void paintPreparations(Graphics g, int x, int y){
		g.setColor(Color.BLACK);
		g.drawString("Przygotowanie do gry. Wybierz region pocz¹tkowy, ", x, y+50);
		if (Screen.activePlayer.id == 1) g.setColor(Color.RED);
		else g.setColor(Color.BLUE);
		g.drawString(Screen.activePlayer.name, x+275, y+50);
	}
	
	public static void paintGameOver(Graphics g, int x, int y){
		g.setColor(Color.BLACK);
		g.drawString("Koniec gry. Zwyciê¿y³: ", x, y+50);
		if (Screen.activePlayer.id == 1) g.setColor(Color.RED);
		else g.setColor(Color.BLUE);
		g.drawString(Screen.activePlayer.name, x+120, y+50);
	}
	
	/**
	 * rysowanie terenu
	 * @param g grafika
	 * @param level mapa
	 * @param gridX pozycja siatki x
	 * @param gridY pozycja siatki y
	 */
	public static void paintTerrain(Graphics g, Level level, int gridX, int gridY){
		for (int i = 0; i < Screen.numX; i++){
			for (int j = 0; j < Screen.numY; j++){
				switch(level.map[i][j]){ //w zale¿noœci od typu w mapie rysowanie ró¿nych tekstur pól
					case 1: g.drawImage(new ImageIcon("textures/dirt.png").getImage(), gridX + (i * 50) + 1, gridY + (j * 50) + 1, null);
					break;
					case 2: g.drawImage(new ImageIcon("textures/grass.png").getImage(), gridX + (i * 50) + 1, gridY + (j * 50) + 1, null);
					break;
					case 3: g.drawImage(new ImageIcon("textures/city.png").getImage(), gridX + (i * 50) + 1, gridY + (j * 50) + 1, null);
					break;
				}
			}
		}
	}
	
	/**
	 * rysowanie logu
	 * @param g grafika
	 * @param x wspo³rzêdna x
	 * @param y wspó³rzêdna y
	 */
	public static void paintLog(Graphics g, int x, int y){
		g.setColor(Color.BLACK);
		g.drawString("Tura " + Screen.turn, x, y);
		if (Screen.activePlayer.id == 1) g.setColor(Color.RED);
		else g.setColor(Color.BLUE);
		g.drawString("Tura gracza " + Screen.activePlayer.name, x, y+20);
	}
	
	/**
	 * rysowanie ramki z info o polu
	 * @param g grafika
	 * @param x wspo³rzêdna x
	 * @param y wspó³rzêdna y
	 */
	public static void paintTerrainInfo(Graphics g, int x, int y){
		g.setColor(Color.BLACK);
		g.drawString("Informacje o regionie", x, y);
		g.drawRect(x, y+7, 200, 80);
		if (Screen.selected > 0) {
			g.drawString("Identyfikator: " + Screen.selected, x+5, y+20);
			if (Level.ownership[Screen.selected] > 0) g.drawString("Nale¿y do: Gracz " + Level.ownership[Screen.selected], x+5, y+40);
			else g.drawString("Nale¿y do: nikogo", x+5, y+40);
			g.drawString("Stacjonuj¹ce wojsko: " + Level.army[Screen.selected], x+5, y+60);
			g.drawString("Produkcja: " + Level.type[Screen.selected], x+5, y+80);
		}
		
	}
	
	/**
	 * rysowanie wybranego terytorium
	 * @param g grafika
	 * @param id id pola
	 */
	public static void paintSelectedTerrain(Graphics g, int id){
		int y = id / 15;
		int x = id - (y * 15);
		x = (x * 50) + 1;
		y = (y * 50) + 1;
		g.drawImage(new ImageIcon("textures/selected.png").getImage(), x, y, null);
	}
	
	/**
	 * rysowanie armii
	 * @param g grafika
	 */
	public static void paintArmy(Graphics g){
		for (int id = 1; id <= Screen.numX*Screen.numY; id++){
			int y = (id-1) / 15;
			int x = (id-1) - (y * 15);
			x = (x * 50) + 1;
			y = (y * 50) + 1;
			if (Level.ownership[id] > 0){
				Font font = new Font("Verdana", Font.BOLD, 30);
				g.setFont(font);
				//dla liczb dwucyfrowych zacznij rysowaæ trochê wczeœniej
				if (Level.army[id] > 9) {
					g.setColor(new Color(0, 0, 0, 200));
					g.drawString(Level.army[id] + "", x+3, y+35);
					if (Level.ownership[id] == 1) g.setColor(Color.RED);
					else if (Level.ownership[id] == 2) g.setColor(new Color(90, 90, 255));
					g.drawString(Level.army[id] + "", x+6, y+33);
				}
				else {
					g.setColor(new Color(0, 0, 0, 200));
					g.drawString(Level.army[id] + "", x+12, y+35);
					if (Level.ownership[id] == 1) g.setColor(Color.RED);
					else if (Level.ownership[id] == 2) g.setColor(new Color(90, 90, 255));
					g.drawString(Level.army[id] + "", x+15, y+33);
				}
			}
		}
	}
	
	/**
	 * rysowanie przylegaj¹cych pól
	 * @param g grafika
	 * @param id id pola
	 */
	public static void paintAdjected(Graphics g, int id){
		id -= 1;
		for (int i = 0; i < 4; i++){
			//policzenie lokalizacji zaznaczonego terenu
			int y = id / 15;
			int x = id - (y * 15);
			switch (i){
			case 0:{
				if(y>0){ //rysowanie góry, je¿eli przynajmniej druga linia (0 jest pierwsza)
					y -= 1;
					x = (x * 50) + 1;
					y = (y * 50) + 1;
					g.drawImage(new ImageIcon("textures/adjected.png").getImage(), x, y, null);
				}
				break;
			}
			case 1:{ //rysowanie lewej strony, gdy kolumna przynajmniej druga
				if(x>0){
					x -= 1;
					x = (x * 50) + 1;
					y = (y * 50) + 1;
					g.drawImage(new ImageIcon("textures/adjected.png").getImage(), x, y, null);
				}
				break;
			}
			case 2:{ //rysowanie prawej, je¿eli najwy¿ej przedostatnia kolumna
				if(x<Screen.numX-1){
					x += 1;
					x = (x * 50) + 1;
					y = (y * 50) + 1;
					g.drawImage(new ImageIcon("textures/adjected.png").getImage(), x, y, null);
				}
				break;
			}
			case 3:{ //rysowanie do³u, je¿eli najwy¿ej przedostatnia linia
				if(y<Screen.numY-1){
					y += 1;
					x = (x * 50) + 1;
					y = (y * 50) + 1;
					g.drawImage(new ImageIcon("textures/adjected.png").getImage(), x, y, null);
				}
				break;
			}
			}
		}
	}
	
	/**
	 * popup dla rekrutacji i transportu wojska
	 * @param frame okno w którym tworzony jest popup
	 * @param max maksymalna wartoœæ slidera
	 * @param mode tryb: 1 dla transportu i 2 dla rekrutacji
	 * @return zwraca wartoœæ ze slidera
	 */
	public static int createPopUp(Frame frame, int max, int mode) {
		//ustawienie tytu³u okna
		String title;
		if (mode == 1) title = "Transportuj jednostki";
		else title = "Rekrutuj jednostki";
		//stworzenie okna
	    JOptionPane optionPane = new JOptionPane();
	    //dodanie slidera
	    JSlider slider = getSlider(optionPane, max);
	    //ustawienie parametrów
	    optionPane.setMessage(new Object[] { "Wybierz liczbê jednostek: ", slider });
	    optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
	    Object[] options = {"OK"};
	    optionPane.setOptions(options);
	    optionPane.setPreferredSize(new Dimension(550,125));
	    JDialog dialog = optionPane.createDialog(frame, title);
	    dialog.setVisible(true);
	    //zwrócenie wartoœci - 0 w przypadku niezainicjowanej wartoœci (gracz nie dotkn¹³ slidera)
	    if (optionPane.getInputValue() == JOptionPane.UNINITIALIZED_VALUE) return 0;
	    else return (Integer)optionPane.getInputValue();
	  }

	/**
	 * slider dla popupa
	 * @param optionPane dialog, w którym tworzony jest slider
	 * @param max maksymalna wartoœæ
	 * @return zwraca wybran¹ wartoœæ
	 */
	  static JSlider getSlider(final JOptionPane optionPane, int max) {
	    JSlider slider = new JSlider();
	    //parametry slidera
	    if (max >= 20) {
	    	slider.setMajorTickSpacing(10);
		    slider.setMinorTickSpacing(1);
	    }
	    else slider.setMajorTickSpacing(1);
	    slider.setValue(0);
	    slider.setMaximum(max);
	    slider.setPaintTicks(true);
	    slider.setPaintLabels(true);
	    //s³uchacz dla slidera
	    ChangeListener changeListener = new ChangeListener() {
	      public void stateChanged(ChangeEvent changeEvent) {
	        JSlider theSlider = (JSlider) changeEvent.getSource();
	        if (!theSlider.getValueIsAdjusting()) {
	          optionPane.setInputValue(new Integer(theSlider.getValue()));
	        }
	      }
	    };
	    slider.addChangeListener(changeListener);
	    return slider;
	  }
}
