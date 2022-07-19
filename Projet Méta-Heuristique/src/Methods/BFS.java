package Methods;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import representation.State;

public class BFS extends Parent{
	/* Cette classe r�soud le probl�me du taquin en utiliant l'algorithme BFS */
	private Queue<State> frontier;/*Une file utilis�e par l'algorithme BFS pour sauvegarder 
    les �tats qui ne sont pas encore d�velopp�s (La liste ouverte)*/


	public BFS() {
		super();
	}
	/* cette m�thode est red�finie, elle r�soud le probl�me du taquin en appliquant la m�thode 
	Beadth-first-Search et retourne un booleen qui est �gale � vrai dans le cas ou elle reussi � 
	trouver le but, est � faux dans le cas contraire
	*/
	@Override
	public boolean solve(State initialState) {
		if(initialState == null) {
			return false;
		}
		initialize();
		frontier = new LinkedList<>();
		frontier.add(initialState);//mettre l'�tat initial dans la liste ouverte
		getInFrontier().add(initialState.getIntRepresentation()); /*mettre la repr�sentation enti�re
		de l'�tat initial dans la hashset InFrontier qui indexe les �tats non encore d�velopp�s */
		startTimer();
		while(!frontier.isEmpty()) {
			State currentState = frontier.remove(); //d�filer un �tat
			explored.add(currentState.getIntRepresentation()); //l'ajouter dans la liste ferm� car il est d�velop�
			setSearchDepth(Math.max(getSearchDepth(), currentState.getDepth()));//mette � jour la profondeur maximale de l'espace des �tats
			if(utility.goalTest(currentState)) { //si l'�tat courrant est un �tat but
				stopTimer(); //arreter le timer
				setGoalState(currentState);
				return true; // sortir de la boulcle avec succ�s (but trouv�)
			}
			/*g�n�rer les fils (voisins ou successeurs) de l'�tat courrant. */
			ArrayList<State> neighbours = currentState.neighbours(false, false);
			for(State neighbour : neighbours) {
				Integer representation = neighbour.getIntRepresentation();
				//v�rifier si un des voisins g�n�r�s n'est ni dans la liste ouverte ni dans ferm�e
				if(!getInFrontier().contains(representation)&& !explored.contains(representation)) {
					frontier.add(neighbour);
					getInFrontier().add(representation);
				}
			}
		}
		stopTimer();//arreter le timer
		return false;  // sortir de la boulcle avec echec (but non trouv�)
	}
	
}
