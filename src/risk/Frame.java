package risk;

import javax.swing.JFrame;

/**
 * Klasa zawieraj�ca punkt wej�cia
 * tworzy okno programu i dodaje do niego panel
 * @author kamster
 *
 */
@SuppressWarnings("serial")
public class Frame extends JFrame{
	
	//parametry okna
	public static int windowSize[] = {757, 700};
	public static String windowName = "Ryzyko";
	
	/**
	 * Konstruktor tworz�cy okno programu
	 * @param size wymiary okna
	 * @param title tytu� okna
	 */
	public Frame(int size[], String title){
		new JFrame();
		setSize(size[0],size[1]);
		setTitle(title);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		
		//dodanie panelu rozgrywki
		Screen screen = new Screen(this);
		add(screen);
	}
	
	/**
	 * Metoda main programu
	 * @param args nieu�ywane
	 */
	public static void main(String[] args){
		new Frame(windowSize, windowName);
	}

}
