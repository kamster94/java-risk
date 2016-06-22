package risk;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Klasa zawieraj¹ca zmienne mapy i metodê do wczytanie i zainicjowania mapy
 * @author kamster
 *
 */
public class Level {
	
	//skaner do wczytania z pliku
	FileInputStream file;
	InputStreamReader reader;
	Scanner scanner;
	
	//tablice z parametrami, [][] - po x,y; [] - po id
	public int[][] map;
	public static int[] ownership;
	public static int[] army;
	public static int[] type;
	
	/**
	 * inicjalizacja - wczytanie
	 * @param fileName nazwa pliku z map¹
	 */
	public Level(String fileName){
		//stworzenie tablic
		map = new int[Screen.numX][Screen.numY];
		ownership = new int [Screen.numX*Screen.numY + 1];
		army = new int [Screen.numX*Screen.numY + 1];
		type = new int [Screen.numX*Screen.numY + 1];
		
		try{
			//wczytywanie pliku z map¹ do tablicy
			file = new FileInputStream("levels/" + fileName + ".level");
			reader = new InputStreamReader(file);
			scanner = new Scanner(reader);
			
			for (int y = 0; y < Screen.numY; y++)
				for (int x = 0; x < Screen.numX; x++){
					map[x][y] = scanner.nextInt();
				}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
		//inicjowanie parametrów mapy
		initArmy();
		initType();
	}
	
	/**
	 * inicjalizacja typów
	 */
	private void initType(){
		int iterator = 1;
		for (int i = 0; i < Screen.numY; i++){
			for (int j = 0; j < Screen.numX; j++){
				type[iterator] = map[j][i];
				iterator++;
			}
		}
	}
	
	/**
	 * inicjalizacja armii stacjonuj¹cych
	 */
	private void initArmy(){
		for (int i = 1; i < Screen.numX * Screen.numY + 1; i++){
			army[i]=0;
			ownership[i]=0;
		}
	}
}
