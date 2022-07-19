package Methods;

import java.util.ArrayList;
import java.util.Stack;
import representation.State;
/* Cette classe r�soud le probl�me du taquin en utiliant l'algorithme DFS */
public class DFS extends Parent{

	private Stack<State> frontier ; /*Une pile utilis�e par l'algorithme DFS pour sauvegarder 
	                                les �tats qui ne sont pas encore d�velopp�s (La liste ouverte)*/
	private final static int maxDepth = 80; //seuil de profondeur de l'arbre de recherche
	

	public DFS() {
		super();
	}
/* cette m�thode est red�finie, elle r�soud le probl�me du taquin en appliquant la m�thode 
	Depth--first-Search et retourne un booleen qui est �gale � vrai dans le cas ou elle reussi � 
	trouver le but, est � faux dans le cas contraire
	*/
	@Override
	public boolean solve(State initialState) {
		if(initialState == null) {
			return false;
		}
		initialize();
		frontier = new Stack<>();
		frontier.push(initialState); //mettre l'�tat initial dans la liste ouverte
		getInFrontier().add(initialState.getIntRepresentation());/*mettre la repr�sentation enti�re
		de l'�tat initial dans la hashset InFrontier qui indexe les �tats non encore d�velopp�s */
		startTimer(); 
		while(!frontier.isEmpty()) {
			State currentState = frontier.pop(); //d�piler un �tat
			
			explored.add(currentState.getIntRepresentation());//l'ajouter dans la liste ferm� car il est d�velop�
			setSearchDepth(Math.max(getSearchDepth(), currentState.getDepth()));//mette � jour la profondeur maximale de l'espace des �tats
			if(utility.goalTest(currentState)) { //si l'�tat courrant est un �tat but
				stopTimer(); //arreter le timer
				setGoalState(currentState);
				return true; // sortir de la boulcle avec succ�s (but trouv�)
			}
			/*g�n�rer les fils (voisins ou successeurs) de l'�tat courrant.
			 * On teste d'abord si la profondeur du noeud � atteint le seuil fix�
			 * Si c'est le cas, aucun fils ne sera g�n�r� pour ce noeud.
			 * */
			if(currentState.getDepth()< maxDepth)
			{ArrayList<State> neighbours = currentState.neighbours(false, false);
			for(State neighbour : neighbours) {
				Integer representation = neighbour.getIntRepresentation();
				if(!getInFrontier().contains(representation) && !explored.contains(representation)) {
					//v�rifier si un des voisins g�n�r�s n'est ni dans la liste ouverte ni dans ferm�e
					frontier.push(neighbour);
					getInFrontier().add(representation);
				}
			}
		}
			
		}
		stopTimer(); //arreter le timer
		return false; // sortir de la boulcle avec echec (but non trouv�)
	}
	
}
