package sokoban.Modele.level;


import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

import sokoban.Modele.level.Level;



public class LevelReader {
	
	private String ReadLine(InputStream input) {
		String line = "";
		try {
			
			int c  = input.read();
			while(c != '\n' && c != -1) {
				line += (char) c;
				c = input.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		return line;
	}

	public Level ReadNextLevel(InputStream input) {
		int MAX = 1024;

		String line, lastComment = ";Err";
		boolean endOfLevel = false;
		String tab[] = new String[MAX];

		int nbLigne = 0;
		int nbCol = 0;

		do {
			try {
				line = ReadLine(input);

				if (line.isEmpty())
					endOfLevel = true;

				else if (line.charAt(0) == ';') {
					lastComment = line; 
				}

				else{ 
					tab[nbLigne] = line;
					nbLigne++;

					if(line.length()>nbCol)
						nbCol = line.length();
				}


			}catch (NoSuchElementException e) {
				endOfLevel = true;
			}

		}while (!endOfLevel);
		
		if (lastComment==";Err" || nbLigne == 0 || nbCol == 0) {
			return null;
		}



		Level l = new Level(lastComment.substring(1),nbLigne,nbCol);

		for(int i = 0; i < nbLigne; i++) {
			String s = tab[i];
			for (int j = 0; j < s.length(); j++) {

				switch(s.charAt(j)) {
				case '#':
					l.ajouteMur(i, j);
					break;
				case '@':
					l.ajoutePousseur(i, j);
					break;
				case '.':
					l.ajouteBut(i, j);
					l.nbBut++;
					break;
				case '$':
					l.ajouteCaisse(i, j);
					break;
				case '+':
					l.ajouteBut(i, j);
					l.ajoutePousseur(i, j);
					l.nbBut++;
					break;
				case '*':
					l.ajouteBut(i, j);
					l.ajouteCaisse(i, j);
					break;
				default: 
					l.videCase(i, j);
					break;
				}
			}
		}
		return l;
	}

}
