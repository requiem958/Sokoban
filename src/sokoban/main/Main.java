package sokoban.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import sokoban.Modele.level.Level;
import sokoban.Modele.level.LevelReader;
import sokoban.Modele.level.LevelWriter;

class Main {
	public static void main(String [] args){
		try {
			FileInputStream in = new FileInputStream(args[0]);
			FileOutputStream out = new FileOutputStream(args[1]);
			Level niveau = (new LevelReader()).ReadNextLevel(in);
			
			while (niveau  != null) {
				(new LevelWriter()).ecrisNiveau(niveau, out);
				niveau = (new LevelReader()).ReadNextLevel(in);
				if (niveau != null) {
					out.write('\n');
				}
				
			}
			
			in.close();
			out.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("Fichier non existant");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Impossible d'écrire");
			e.printStackTrace();
		}

	}
}
