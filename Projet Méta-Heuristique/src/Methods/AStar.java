package Methods;

import representation.State;
import java.util.*;

public class AStar extends Parent{
	/* Cette classe résoud le problème du taquin en utiliant l'algorithme A* */
    private PriorityQueue<State> frontier;
    private Comparator<State> costSorter;

    public AStar() {
    	super();
    }

    /* cette méthode est redéfinie, elle résoud le problème du taquin en appliquant la méthode 
	A* et retourne un booleen qui est égale à vrai dans le cas ou elle reussi à 
	trouver le but, est à faux dans le cas contraire
	*/
    @Override
    public boolean solveStar(State initialState, boolean euclidean) {
		if(initialState == null) {
			return false;
		}
		initialize();
        costSorter = Comparator.comparing(State::getFOfNCost);
        frontier = new PriorityQueue<State>(costSorter);
        frontier.add(initialState); //mettre l'état initial dans la liste ouverte
        getInFrontier().add(initialState.getIntRepresentation());/*mettre la représentation entière
		de l'état initial dans la hashset InFrontier qui indexe les états non encore développés */
        startTimer();
        while(!frontier.isEmpty()) {
            State currentState = frontier.poll();//récupérer un état
            explored.add(currentState.getIntRepresentation());//l'ajouter dans la liste fermé car il est dévelopé
            setSearchDepth(Math.max(getSearchDepth(), currentState.getDepth()));//mette à jour la profondeur maximale de l'espace des états
            if(utility.goalTest(currentState)) {
            	stopTimer();
				setGoalState(currentState);
				return true; // sortir de la boulcle avec succès (but trouvé)
            }
            /*générer les fils (voisins ou successeurs) de l'état courrant. 
             * euclidean = true : utiliser l'heuristique diastance Euclidienne
             * euclidean = false : utiliser l'heuristique diastance de Manhattan
             * */
            ArrayList<State> neighbours = currentState.neighbours(true, euclidean);
            for(State neighbour : neighbours) {
                Integer representation = neighbour.getIntRepresentation();
                boolean visited = explored.contains(representation); 
                if(visited) { // se touve  dans fermé (déjà visité) 
                	
                	continue;
                }
                boolean existInFrontier = getInFrontier().contains(representation);
                
                if(!existInFrontier) {  //ne se trouve pas dans ouverte (1 ere fois visité)
                
                	frontier.add(neighbour);
                	//explored.add(neighbour.getIntRepresentation());
                	getInFrontier().add(representation);
                }else {
                	/*l'état est visité, donc vérifier si le cout du nouveau chemin est inférieur au cout
                	du chemin courrant*/
    
                	State frontierState = searchInFrontier(frontier.iterator(), representation);
                	if(neighbour.getCost() < frontierState.getCost()) {
                		frontier.remove(frontierState);
                		frontier.add(neighbour);
                		
                		
                	}	
                }
            }
        }
        stopTimer(); //arreter le timer
		return false; // sortir de la boulcle avec echec (but non trouvé)
    }
}
