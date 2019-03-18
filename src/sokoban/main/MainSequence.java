package sokoban.main;

import sokoban.Modele.Global.Configuration;
import sokoban.Structures.FabriqueSequence;
import sokoban.Structures.Iterateur;
import sokoban.Structures.Sequence;

public class MainSequence {

	public static void main(String [] args){

		Configuration c = null;
		
		try {
			c = Configuration.instance();
		} catch (Exception e) {
			System.err.println("Problème lecture config");
			//e.printStackTrace();
		}
		
		test(c.fabriqueSequence());
		c.lis("Prop");


	}
	
	
	static void test(FabriqueSequence f) {

		Sequence<Integer> s = null;

		s = f.nouvelle();
		for (int i = 0; i < 10; i++) {
			s.insereTete(i);
		}

		if (!s.estVide())
			System.out.println("S non vide (ouf)");
		else
			System.out.println("Bléme bléme");

		System.out.println("séquence : "+s);
		
		Iterateur<Integer> it = s.iterator();
		
		it.prochain();
		it.prochain();
		it.supprime();
		//it.supprime();
		System.out.println("séq : "+s);
		
		it = s.iterator();
		while(it.aProchain()) {
			System.out.println("Element : "+it.prochain());
			it.supprime();
			System.out.println("séquence : " +s);
		}
		s.extraitTete();
		
		try {
			s.extraitTete();
		}catch(RuntimeException e) {
			System.out.println("Okay pour runtime");
		}
		
		
		
		
	}
}
