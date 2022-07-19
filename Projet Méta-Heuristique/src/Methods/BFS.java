package Methods;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import representation.State;

public class BFS extends Parent{
	/* Cette classe résoud le problème du taquin en utiliant l'algorithme BFS */
	private Queue<State> frontier;/*Une file utilisée par l'algorithme BFS pour sauvegarder 
    les états qui ne sont pas encore développés (La liste ouverte)*/


	public BFS() {
		super();
	}
	/* cette méthode est redéfinie, elle résoud le problème du taquin en appliquant la méthode 
	Beadth-first-Search et retourne un booleen qui est égale à vrai dans le cas ou elle reussi à 
	trouver le but, est à faux dans le cas contraire
	*/
	@Override
	public boolean solve(State initialState) {
		if(initialState == null) {
			return false;
		}
		initialize();
		frontier = new LinkedList<>();
		frontier.add(initialState);//mettre l'état initial dans la liste ouverte
		getInFrontier().add(initialState.getIntRepresentation()); /*mettre la représentation entière
		de l'état initial dans la hashset InFrontier qui indexe les états non encore développés */
		startTimer();
		while(!frontier.isEmpty()) {
			State currentState = frontier.remove(); //défiler un état
			explored.add(currentState.getIntRepresentation()); //l'ajouter dans la liste fermé car il est dévelopé
			setSearchDepth(Math.max(getSearchDepth(), currentState.getDepth()));//mette à jour la profondeur maximale de l'espace des états
			if(utility.goalTest(currentState)) { //si l'état courrant est un état but
				stopTimer(); //arreter le timer
				setGoalState(currentState);
				return true; // sortir de la boulcle avec succès (but trouvé)
			}
			/*générer les fils (voisins ou successeurs) de l'état courrant. */
			ArrayList<State> neighbours = currentState.neighbours(false, false);
			for(State neighbour : neighbours) {
				Integer representation = neighbour.getIntRepresentation();
				//vérifier si un des voisins générés n'est ni dans la liste ouverte ni dans fermée
				if(!getInFrontier().contains(representation)&& !explored.contains(representation)) {
					frontier.add(neighbour);
					getInFrontier().add(representation);
				}
			}
		}
		stopTimer();//arreter le timer
		return false;  // sortir de la boulcle avec echec (but non trouvé)
	}
	
}
