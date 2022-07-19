package Methods;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import representation.State;

public class Parent {
	/*
	 *  cette classe est commune entre les 3 m�thodes de r�solution (BFS,DFS,AStar). ces derni�res 
	 *  h�ritent de cette classe et r�utilisent ses fonctions.
	 * */
	protected Set<Integer> explored; /*Table de hachage contenant des entiers repr�sentant les noeuds
	                                 d�j� d�velopp�s (dans la liste ferm�e)*/
	protected Set<Integer> inFrontier;/*Table de hachage contenant des entiers repr�sentant les noeuds
	                                  qui sont dans la liste ouvert*/
	protected State goalState; //l'�tat but
	protected Utility utility; //Objet de type Utility
	protected int searchDepth; //la profondeur de la recherche
	protected long runningTime; //le temps d'ex�cution
	
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

	
	// Cette m�thode initialise les variables necessaires avant de commmen�er la r�solution 
	protected void initialize() {
		explored = new HashSet<>();
		inFrontier = new HashSet<>();
		searchDepth  = 0;
		runningTime = 0;
		goalState = null;
	}
	


	// M�thodes de calcul de temps d'ex�cution
	protected void startTimer() {
		setRunningTime(System.currentTimeMillis());
	}
	
	protected void stopTimer() {
		setRunningTime(System.currentTimeMillis() - getRunningTime());
	}
	
    /* M�thode de recherche d'un �tat dans la liste ouverts en utilisant la repr�sentation enti�re
	  de l'�tat (cette m�thode sera appel�e dans l'algorithme A*
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

	// Cette m�thode sera red�finie dans les classes BFS et DFS 
	public boolean solve(State root) {
		return false;
	}

	// Cette m�thode sera red�finie dans la classe AStar
	public boolean solveStar(State root, boolean euclidean) {
		return false;
	}
}
