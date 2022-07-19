package Methods;

import representation.State;

import java.util.Stack;

public class Utility {
	public static int goalState;  //l'�tat but qu'on cherche 
	
	/*Cette m�thode retourne une pile contenant les �tats du chemin qui m�ne vers le but,
	 * en d'autres termes, c'est la solution du probl�me.
	 * elle prends en param�tre l'�tat but et elle fait un marche en arri�re (backtracking) 
	 * pour avoir tous les �tats reli�s entre eux (lien de parent�) jusqu'� ce qu'elle arrive � 
	 * l'�tat initial. 
	 * */
	public Stack<State> backtrack(State state) {
		Stack<State> stack = new Stack<>();
		while (state != null) { //l'�tat � un parent
			stack.push(state);
			state = state.getParent();
		}
		return stack;
	}
	
	/* cette m�thode v�rifie si un �tat donn� est un �tat but en utilisant la repr�sentation enti�re 
	de ce dernier.*/
	public boolean goalTest(State currentState) {
		return currentState.getIntRepresentation() == this.goalState;
	}

}
