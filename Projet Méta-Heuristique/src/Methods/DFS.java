package Methods;

import java.util.ArrayList;
import java.util.Stack;
import representation.State;
/* Cette classe résoud le problème du taquin en utiliant l'algorithme DFS */
public class DFS extends Parent{

	private Stack<State> frontier ; /*Une pile utilisée par l'algorithme DFS pour sauvegarder 
	                                les états qui ne sont pas encore développés (La liste ouverte)*/
	private final static int maxDepth = 80; //seuil de profondeur de l'arbre de recherche
	

	public DFS() {
		super();
	}
/* cette méthode est redéfinie, elle résoud le problème du taquin en appliquant la méthode 
	Depth--first-Search et retourne un booleen qui est égale à vrai dans le cas ou elle reussi à 
	trouver le but, est à faux dans le cas contraire
	*/
	@Override
	public boolean solve(State initialState) {
		if(initialState == null) {
			return false;
		}
		initialize();
		frontier = new Stack<>();
		frontier.push(initialState); //mettre l'état initial dans la liste ouverte
		getInFrontier().add(initialState.getIntRepresentation());/*mettre la représentation entière
		de l'état initial dans la hashset InFrontier qui indexe les états non encore développés */
		startTimer(); 
		while(!frontier.isEmpty()) {
			State currentState = frontier.pop(); //dépiler un état
			
			explored.add(currentState.getIntRepresentation());//l'ajouter dans la liste fermé car il est dévelopé
			setSearchDepth(Math.max(getSearchDepth(), currentState.getDepth()));//mette à jour la profondeur maximale de l'espace des états
			if(utility.goalTest(currentState)) { //si l'état courrant est un état but
				stopTimer(); //arreter le timer
				setGoalState(currentState);
				return true; // sortir de la boulcle avec succès (but trouvé)
			}
			/*générer les fils (voisins ou successeurs) de l'état courrant.
			 * On teste d'abord si la profondeur du noeud à atteint le seuil fixé
			 * Si c'est le cas, aucun fils ne sera généré pour ce noeud.
			 * */
			if(currentState.getDepth()< maxDepth)
			{ArrayList<State> neighbours = currentState.neighbours(false, false);
			for(State neighbour : neighbours) {
				Integer representation = neighbour.getIntRepresentation();
				if(!getInFrontier().contains(representation) && !explored.contains(representation)) {
					//vérifier si un des voisins générés n'est ni dans la liste ouverte ni dans fermée
					frontier.push(neighbour);
					getInFrontier().add(representation);
				}
			}
		}
			
		}
		stopTimer(); //arreter le timer
		return false; // sortir de la boulcle avec echec (but non trouvé)
	}
	
}
