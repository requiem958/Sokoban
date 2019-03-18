package sokoban.Modele.Global;

import java.io.FileInputStream;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import sokoban.Structures.FabriqueSequence;
import sokoban.Structures.FabriqueSequenceListe;
import sokoban.Structures.FabriqueSequenceTableau;

public class Configuration {
	
	private static Configuration c = null;

	Properties pUser;
	Properties pDefault;

	private Configuration() throws Exception {
		this.pDefault = new Properties();
		this.pUser = new Properties();
		this.loadProperties(pUser, pDefault);
	}
	
	public static Configuration instance() throws Exception {
		c = (c==null) ? new Configuration() : c;
		return c;
	}
	
	public String lis(String p) throws NoSuchElementException{
		String s =pUser.getProperty(p);
		if (s!=null) {
			return s;
		}
		else if ((s=pDefault.getProperty(p)) != null) {
			return s;
		}
		else {
			throw new NoSuchElementException("Propriété "+p+" non existante");
		}
	}
	
	public FabriqueSequence fabriqueSequence() throws NoSuchElementException{
		return (this.lis("Sequence").equalsIgnoreCase("Tableau")) ? 
				new FabriqueSequenceTableau() : 
				new FabriqueSequenceListe();
		
	}
	
	public Logger logger(){
		Logger l = Logger.getLogger("Sokoban.Logger");
		l.setLevel(Level.parse(c.lis("LogLevel")));
		return l;
	}

	private void loadProperties(Properties pUser, Properties pDefault) throws Exception{

		final String home = System.getProperty("user.home");

		try {
			pDefault.load(ClassLoader.getSystemClassLoader().getResourceAsStream("default.cfg"));
		}
		catch(Exception e){
			System.err.println("Fichier config default defectueux");
			throw e;
		}

		try {
			pUser.load(new FileInputStream(home+"/.armoroides"));
		}
		catch (Exception e) {

			System.err.println("Fichier de config user defectueux");
		}
	}
}
