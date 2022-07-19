package Methods;

import representation.State;

import java.util.Stack;

public class Utility {
	public static int goalState;  //l'état but qu'on cherche 
	
	/*Cette méthode retourne une pile contenant les états du chemin qui mène vers le but,
	 * en d'autres termes, c'est la solution du problème.
	 * elle prends en paramètre l'état but et elle fait un marche en arrière (backtracking) 
	 * pour avoir tous les états reliés entre eux (lien de parenté) jusqu'à ce qu'elle arrive à 
	 * l'état initial. 
	 * */
	public Stack<State> backtrack(State state) {
		Stack<State> stack = new Stack<>();
		while (state != null) { //l'état à un parent
			stack.push(state);
			state = state.getParent();
		}
		return stack;
	}
	
	/* cette méthode vérifie si un état donné est un état but en utilisant la représentation entière 
	de ce dernier.*/
	public boolean goalTest(State currentState) {
		return currentState.getIntRepresentation() == this.goalState;
	}

}
