package sokoban.Modele.level;

import java.io.OutputStream;
import java.io.PrintStream;

import sokoban.Modele.level.Level;

public class LevelWriter {

	public void ecrisNiveau(Level l, OutputStream out) {
		PrintStream ps = new PrintStream(out); 

		for(int i=0 ; i< l.lignes(); i++) {
			for(int j=0 ; j< l.colonnes(); j++) {
				if (l.aMur(i, j))
					ps.print('#');
				else if (l.aCaisse(i, j))
					ps.print('$');
				else if (l.aBut(i, j))
					ps.print('.');
				else if (l.aPousseur(i, j))
					ps.print('@');
				else if (l.aPousseurSurBut(i, j))
					ps.print('+');
				else if (l.aCaisseSurBut(i, j))
					ps.print('*');	
				else if (l.estVide(i, j))
					ps.print(' ');
			}
			ps.print('\n');
		}

		ps.print(";" + l.nom()+'\n');
	}
}
