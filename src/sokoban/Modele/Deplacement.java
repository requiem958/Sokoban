package sokoban.Modele;

enum Direction{
	HAUT,
	BAS,
	GAUCHE,
	DROITE
};

public class Deplacement {
	int oriX;
	int oriY;
	Direction d;
	
	public int xArrive(){
		switch(d){
		case HAUT:
			return oriX;
		case BAS:
			return oriX;
		case GAUCHE:
			return oriX-1;
		case DROITE:
			return oriX+1;
		default:
			return -1;
		}
	}
	public int yArrive(){
		switch(d){
		case HAUT:
			return oriY-1;
		case BAS:
			return oriY+1;
		case GAUCHE:
			return oriY;
		case DROITE:
			return oriY;
		default:
			return -1;
		}
	}
	public int xArriveN(){
		switch(d){
		case HAUT:
			return oriX;
		case BAS:
			return oriX;
		case GAUCHE:
			return oriX-2;
		case DROITE:
			return oriX+2;
		default:
			return -1;
		}
	}
	public int yArriveN(){
		switch(d){
		case HAUT:
			return oriY-2;
		case BAS:
			return oriY+2;
		case GAUCHE:
			return oriY;
		case DROITE:
			return oriY;
		default:
			return -1;
		}
	}
}
