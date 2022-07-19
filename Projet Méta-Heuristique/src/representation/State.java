package representation;

import java.util.ArrayList;

public class State {
	/*Cette classe  conteint les informations necessaire sur un état */

	private Integer[][] mapping; // représenter le taquin sous forme d'une matrice
	private State parent; // le parent d'un noeud (état). le noeud initial n'en a pas
	private double cost; // le cout d'un chemin démarant de l'état initial est arrivant au noeud n
	private double estimatedCostToGoal;// le cout calculé dans le cas de la recherche A* 
	//pour avoir le cout estimé de l'état n à l'état but 
	private int depth; // la profondeur du chemin de la racine à l'état cible (le but) 
	private int intRepresentation; //représentation entière d'un état
	

	// constructeur
	public State() {
		mapping = new Integer[3][3];
		cost = 0;
		estimatedCostToGoal = 0;
		depth = 0;
	}

	public State(Integer[][] mapping) {
		this.mapping = mapping;
	}

	// setters and getters
	public Integer[][] getMapping() {
		return mapping;
	}

	public void setMapping(Integer[][] state) {
		this.mapping = state;
		calculateIntRepresentation();
	}

	public State getParent() {
		return parent;
	}

	public void setParent(State parent) {
		this.parent = parent;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public double getEstimatedCostToGoal() {
		return estimatedCostToGoal;
	}

	public void setEstimatedCostToGoal(double estimatedCostToGoal) {
		this.estimatedCostToGoal = estimatedCostToGoal;
	}
	
	public  void setIntRepresentation(int intRepresentation) {
		this.intRepresentation = intRepresentation;
	}
	
	public int getIntRepresentation() {
		return intRepresentation;
	}
	/* 
	 * la méthode "neighbours" retourne les voisins d'un noeuds (ses fils) dans une arrayList
	 * ceci en testant les 4 déplacements possibles.
	 *  computeEstimatedCost:   true => calculer le cost , 
	                         false => ne pas calculer cost
	    heuristicCostFunction: false => calculer le cost avec distance de Manhattan 
                             true => calculer le cost avec distance Euclidienne
	 * */

	 
	public ArrayList<State> neighbours(boolean computeEstimatedCost, boolean heuristicCostFunction) {
		// Trouver le coordonnés de la case vide (le 0 dans la matrice)
		int row, col = 0;
		for (row = 0; row < 3; row++) {
			boolean found = false;
			for (col = 0; col < 3; col++) {
				if (this.mapping[row][col] == 0) {
					found = true;
					break;
				}
			}
			if (found)
				break;
		}
		ArrayList<State> neighbours = new ArrayList<>();

		// Déplacer à droite
		if (col < 2) {
			neighbours.add(nextState(row, col, row, col + 1, computeEstimatedCost, heuristicCostFunction));
		}
		// Déplacer en bas
		if (row < 2) {
			neighbours.add(nextState(row, col, row + 1, col, computeEstimatedCost, heuristicCostFunction));
		}
		// Déplacer à gauche
		if (col > 0) {
			neighbours.add(nextState(row, col, row, col - 1, computeEstimatedCost, heuristicCostFunction));
		}
		// Déplacer en haut
		if (row > 0) {
			neighbours.add(nextState(row, col, row - 1, col, computeEstimatedCost, heuristicCostFunction));
		}
		return neighbours; //liste des voisin accessible
	}

	/*
	 * Cette méthode créé un noeud avec les nouveaux déplacements de la case vide (le 0).
	 */
	private State nextState(int row, int col, int targetRow, int targetCol, boolean computeEstimatedCost,
			boolean heuristicCostFunction) {
		State state = new State();
		state.setMapping(getNewMapping(row, col, targetRow, targetCol));
		state.setParent(this);
		state.setCost(this.getCost() + 1);
		if (computeEstimatedCost) //Dans le cas le l'algorithme A*
			state.setEstimatedCostToGoal(computeEstimatedCost(state, heuristicCostFunction));
		state.setDepth(this.getDepth() + 1);
		return state;
	}
	/*Cette méthode trouve le nouvel état d'un noeud en déplaçant le 0 de sa position initiale
	 * (zeroRow, zeroCol) à la nouvelle position (targetRow, targetCol)
	 */
	private Integer[][] getNewMapping(int zeroRow, int zeroCol, int targetRow, int targetCol) {
		Integer[][] newMapping = new Integer[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				newMapping[i][j] = this.mapping[i][j];
			}
		} //déplacement de 0
		Integer temp = newMapping[targetRow][targetCol];
		newMapping[targetRow][targetCol] = 0;
		newMapping[zeroRow][zeroCol] = temp;
		return newMapping;
	}

	/*
	 * Cette méthode calcule la représentation entière d'un état donné
	 * Exemple : l'etat [[1 2 3],
	 *                   [4 5 6],
	 *                   [7 0 8]]
	 *    calculé comme suit:
	 *    1*10^8 +   2*10^7 + 3*10^6 + 4*10^5 + 6*10^4 + 7*10^3 + 8*10^2 + 0*10^1 +  8*10^0
	 *    qui donne la représentation entière suivante: 123456708
	 *    cet entier sera indexé dans une hashSet qui contient les états déjà visité (explored).
	 *    Ceci nous vacilitera la recherche et nous permettera de faire la recherche en O(1).           
	
	 */
	public void calculateIntRepresentation() {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				this.intRepresentation += mapping[row][col] * Math.pow(10, 8 - (row * 3 + col));
			}
		}
	}


	/* Cette méthode calcule le cout restant entre l'état donné en paramètres et l'état cible
	 *  heuristicCostFunction: false => calculer le cost avec l'heuristique " Distance de Manhattan" 
                               true => calculer le cost avec l'heuristique "Distance Euclidienne"
	 */
	private double computeEstimatedCost(State state, boolean heuristicCostFunction) {
		Integer[][] mapping = state.getMapping();
		double heuristicCost = 0;
		if (!heuristicCostFunction) {
			// Calculer la distance de Manhattan
			for (int row = 0; row < 3; row++) {
				for (int col = 0; col < 3; col++) {
					Integer number = mapping[row][col];
					heuristicCost += (Math.abs(row - number / 3) + Math.abs(col - number % 3));
				}
			}
		} else {	// Calculer la distance Euclidienne
			for (int row = 0; row < 3; row++) {
			
				for (int col = 0; col < 3; col++) {
					Integer number = mapping[row][col];
					heuristicCost += Math.sqrt(Math.pow(row - number / 3, 2) + Math.pow(col - number % 3, 2));
				}
			}
		}
		//  h(n)
		return heuristicCost;
	}
	// cette méthode retourne le cout globale ( f(n) = g(n)+ h(n))
	public double getFOfNCost() {
		return getCost() + getEstimatedCostToGoal();
	}
}