package Methods;

import representation.State;
import java.util.*;

public class AStar extends Parent{
	/* Cette classe r�soud le probl�me du taquin en utiliant l'algorithme A* */
    private PriorityQueue<State> frontier;
    private Comparator<State> costSorter;

    public AStar() {
    	super();
    }

    /* cette m�thode est red�finie, elle r�soud le probl�me du taquin en appliquant la m�thode 
	A* et retourne un booleen qui est �gale � vrai dans le cas ou elle reussi � 
	trouver le but, est � faux dans le cas contraire
	*/
    @Override
    public boolean solveStar(State initialState, boolean euclidean) {
		if(initialState == null) {
			return false;
		}
		initialize();
        costSorter = Comparator.comparing(State::getFOfNCost);
        frontier = new PriorityQueue<State>(costSorter);
        frontier.add(initialState); //mettre l'�tat initial dans la liste ouverte
        getInFrontier().add(initialState.getIntRepresentation());/*mettre la repr�sentation enti�re
		de l'�tat initial dans la hashset InFrontier qui indexe les �tats non encore d�velopp�s */
        startTimer();
        while(!frontier.isEmpty()) {
            State currentState = frontier.poll();//r�cup�rer un �tat
            explored.add(currentState.getIntRepresentation());//l'ajouter dans la liste ferm� car il est d�velop�
            setSearchDepth(Math.max(getSearchDepth(), currentState.getDepth()));//mette � jour la profondeur maximale de l'espace des �tats
            if(utility.goalTest(currentState)) {
            	stopTimer();
				setGoalState(currentState);
				return true; // sortir de la boulcle avec succ�s (but trouv�)
            }
            /*g�n�rer les fils (voisins ou successeurs) de l'�tat courrant. 
             * euclidean = true : utiliser l'heuristique diastance Euclidienne
             * euclidean = false : utiliser l'heuristique diastance de Manhattan
             * */
            ArrayList<State> neighbours = currentState.neighbours(true, euclidean);
            for(State neighbour : neighbours) {
                Integer representation = neighbour.getIntRepresentation();
                boolean visited = explored.contains(representation); 
                if(visited) { // se touve  dans ferm� (d�j� visit�) 
                	
                	continue;
                }
                boolean existInFrontier = getInFrontier().contains(representation);
                
                if(!existInFrontier) {  //ne se trouve pas dans ouverte (1 ere fois visit�)
                
                	frontier.add(neighbour);
                	//explored.add(neighbour.getIntRepresentation());
                	getInFrontier().add(representation);
                }else {
                	/*l'�tat est visit�, donc v�rifier si le cout du nouveau chemin est inf�rieur au cout
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
		return false; // sortir de la boulcle avec echec (but non trouv�)
    }
}
