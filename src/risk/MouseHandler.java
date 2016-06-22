package risk;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Klasa ze s�uchaczem myszy
 * @author kamster
 *
 */
public class MouseHandler implements MouseListener{

	private Screen screen;
	
	public MouseHandler(Screen screen){
		this.screen = screen;
	}

	public void mouseClicked(MouseEvent e) {
	
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	/**
	 * sprawdzenie, czy nastapi�o klikniecie
	 */
	public void mousePressed(MouseEvent e) {
		screen.repaint();
		//poprawka na wymiary ramki okna - faktyczna pozycja myszy jest inna ni� ta na g��wnym ekranie
		int x = e.getX()-2;
		int y = e.getY()-25;
		//System.out.println("[debug] Mouse pressed in (" + x + ", " + y + ")");
		//aktualizacja wybranego terenu je�eli klikni�cie wewn�trz mapy i tryb transportu wy��czony
		if (Screen.transport == false && e.getY() <= 526) screen.updateSelected(x,y);
		//je�eli tryb transportu w��czony, przeniesienie armii (w�a�ciwe sprawdzenie wsp�rz�dnych nast�pi w funkcji
		if (Screen.transport == true) screen.transportArmy(x, y);
		//ustawienie przycisku potwierdzenia
		if(Screen.preparations == true && Screen.selected != 0 && Level.ownership[Screen.selected] == 0) Screen.startConfirm.setEnabled(true);
		if(Screen.preparations == true &&  Level.ownership[Screen.selected] != 0) Screen.startConfirm.setEnabled(false);
		//prze��czanie aktywno�ci przycisk�w transportu i rekrutacji
		if (Level.ownership[Screen.selected] != Screen.activePlayer.id) {
			Screen.transportButton.setEnabled(false);
			Screen.recruitButton.setEnabled(false);
		}
		else {
			if (Screen.activePlayer.remainingMoves > 0 ) Screen.transportButton.setEnabled(true);
			if (Screen.activePlayer.money > 0 ) Screen.recruitButton.setEnabled(true);
		}
		if (Screen.transport == true) Screen.recruitButton.setEnabled(false);
	}

	public void mouseReleased(MouseEvent e) {

	}

}
