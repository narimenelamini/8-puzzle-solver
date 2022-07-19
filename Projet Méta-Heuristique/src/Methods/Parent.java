package Methods;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import representation.State;

public class Parent {
	/*
	 *  cette classe est commune entre les 3 méthodes de résolution (BFS,DFS,AStar). ces dernières 
	 *  héritent de cette classe et réutilisent ses fonctions.
	 * */
	protected Set<Integer> explored; /*Table de hachage contenant des entiers représentant les noeuds
	                                 déjà développés (dans la liste fermée)*/
	protected Set<Integer> inFrontier;/*Table de hachage contenant des entiers représentant les noeuds
	                                  qui sont dans la liste ouvert*/
	protected State goalState; //l'état but
	protected Utility utility; //Objet de type Utility
	protected int searchDepth; //la profondeur de la recherche
	protected long runningTime; //le temps d'exécution
	
	protected Parent() {
		utility = new Utility();
	}

	// setters and getters
	public Set<Integer> getExplored() {
		return explored;
	}

	public int getSearchDepth() {
		return searchDepth;
	}

	protected void setSearchDepth(int searchDepth) {
		this.searchDepth = searchDepth;
	}

	public long getRunningTime() {
		return runningTime;
	}

	private void setRunningTime(long runningTime) {
		this.runningTime = runningTime;
	}

	public State getGoalState() {
		return goalState;
	}

	protected void setGoalState(State goalState) {
		this.goalState = goalState;
	}
	public Set<Integer> getInFrontier() {
		return inFrontier;
	}

	
	// Cette méthode initialise les variables necessaires avant de commmençer la résolution 
	protected void initialize() {
		explored = new HashSet<>();
		inFrontier = new HashSet<>();
		searchDepth  = 0;
		runningTime = 0;
		goalState = null;
	}
	


	// Méthodes de calcul de temps d'exécution
	protected void startTimer() {
		setRunningTime(System.currentTimeMillis());
	}
	
	protected void stopTimer() {
		setRunningTime(System.currentTimeMillis() - getRunningTime());
	}
	
    /* Méthode de recherche d'un état dans la liste ouverts en utilisant la représentation entière
	  de l'état (cette méthode sera appelée dans l'algorithme A*
	  */
	protected State searchInFrontier(Iterator<State> iterator, Integer representation) {
		while(iterator.hasNext()){
			State state = iterator.next();
			if(state.getIntRepresentation() == representation) {
				return state;
			}
		}
		return null;
	}

	// Cette méthode sera redéfinie dans les classes BFS et DFS 
	public boolean solve(State root) {
		return false;
	}

	// Cette méthode sera redéfinie dans la classe AStar
	public boolean solveStar(State root, boolean euclidean) {
		return false;
	}
}
