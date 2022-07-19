package main;
import java.util.Scanner;
import java.util.Stack;
import java.util.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import Methods.*;
import representation.State;

public class Main extends JFrame  {
	
	/*General attributs*/
	Utility utility = new Utility();
	// input
	Integer[][] array = new Integer[3][3];
	char[][] array_init = new char[3][3];
	char[][] array_goal = new char[3][3];
	Integer[][] array_init_Integer = new Integer[3][3];
	Stack<State> stack = new Stack<>();
	State root = new State();
	
	
	
	/*GUI attributs*/
	private final JButton tiles[];
	private String initialState;
	private String GoalState; 
	private boolean solved = false;
	private char[] ch1 = new char[9];
	private char[] ch2 = new char[9];
	private char[] ch3 = new char[9];
	//initialisation des variabes
	private  JComboBox<String> cb;
	private String[] mthds = {"DFS", "BFS" , "A* [distance Euclidienne]", "A* [distance de Manhattan]"};
	private JLabel Label_init;
    private JButton reset;
    private JButton MAJ;
    private JButton Run;
    private JButton path;
    private JPanel ButtonsPanel;
    public JPanel Main_Middle;
    private JPanel Main_Right;
    private JButton Tile_1;
    private JButton Tile_2;
    private JButton Tile_3;
    private JButton Tile_4;
    private JButton Tile_5;
    private JButton Tile_6;
    private JButton Tile_7;
    private JButton Tile_8;
    private JButton Tile_9;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JLabel  Label_1;
    private JLabel  Label_2;
    private JLabel  Label_3;
    private JLabel  Label_4;
    private JLabel  Label_5;
    private JLabel  Label_6;
    private JLabel  Label_7;
    private JLabel  Label_8;
    private JLabel  Label_9;
    private JLabel  Label_10;
    private JLabel  Label_11;
    private JLabel  Label_12;
    private JLabel  Label_13;
    private JTextField TextEdit1;
    private JTextField TextEdit2;
  
	 public  Main() {
		 //frame Title
	        super("Jeux du taquin à 8 cases");
	        this.setSize(600,900);
	       
	        //set GUI settings
	        initComponents();
	        this.setLocationRelativeTo(null);   //center the frame in the screen on open
	        this.setResizable(false);
	        this.setAlwaysOnTop(true); 
	        path.setFocusable(false);
	        this.tiles = new JButton[]{Tile_1, Tile_2, Tile_3, Tile_4, Tile_5, Tile_6, Tile_7, Tile_8, Tile_9};
	        for(int i = 0 ; i < tiles.length ; ++i){
	            
	            tiles[i].setFocusable(false);
	            tiles[i].setFont(tiles[i].getFont().deriveFont(25.0f));
	            
	                }
	      //action listener for the MAJ button
	        this.MAJ.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					path.setText("Voir chemin");	
			    	path.setBackground(new Color(137,219,236));
			    	path.setForeground(new Color(0,48,102));
					int i,j, k= 0; 
					initialState=TextEdit1.getText();
					for( i = 0; i<9 ; i++) {
						ch1[i] = initialState.charAt(i);
					}
					for( i = 0; i<9 ; i++) {
						
						if(Character.toString(ch1[i]).equals("0"))
							{tiles[i].setText("");
							 tiles[i].setBackground(new Color(137,219,236));
							 tiles[i].setForeground(Color.white);
							 }
						else
						{tiles[i].setText(Character.toString(ch1[i]));
						tiles[i].setBackground(new Color(0,48,102));
						tiles[i].setForeground(Color.white);}
						
					}
					for ( i = 0; i < 3; i++) {
						for ( j = 0; j < 3; j++) {
								array_init[i][j] = ch1[k];
								k++;
							}
						}
					for ( i = 0; i < 3; i++) {
						for ( j = 0; j < 3; j++) {
							array_init_Integer[i][j] = Character.getNumericValue(array_init[i][j]);
						}
					}
					
				}
			
			} );
	      //action listener for the Run button	
	           this.Run.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						int i,j, k= 0; 
						GoalState=TextEdit2.getText();
						utility.goalState = Integer.parseInt(GoalState.toString());
						
						for( i = 0; i<9 ; i++) {
							ch2[i] = GoalState.charAt(i);
						}
						
						for ( i = 0; i < 3; i++) {
							for ( j = 0; j < 3; j++) {
									array_goal[i][j] = ch2[k];
									k++;
								}
							}
						
						//get selected item from menu
						String selected_item =  cb.getSelectedItem().toString();
						if(selected_item.equals("BFS")) {
							Parent bfs = new BFS();
							root.setMapping(array_init_Integer);
							if(solved = bfs.solve(root)) {
								
								Label_5.setText(Long.toString(bfs.getRunningTime()));
								Label_7.setText(String.valueOf(bfs.getSearchDepth()));
								Label_9.setText(Double.toString(bfs.getGoalState().getCost()));
								Label_11.setText(Long.toString(bfs.getExplored().size()));
								Label_13.setText(Long.toString(bfs.getInFrontier().size()));
								stack = utility.backtrack(bfs.getGoalState());
								
								
						
							}
						}
						
						else if(selected_item.equals("DFS")) {
							Parent dfs = new DFS();
							root.setMapping(array_init_Integer);
							if(solved = dfs.solve(root)) {
								
								Label_5.setText(Long.toString(dfs.getRunningTime()));
								Label_7.setText(String.valueOf(dfs.getSearchDepth()));
								Label_9.setText(Double.toString(dfs.getGoalState().getCost()));
								Label_11.setText(Long.toString(dfs.getExplored().size()));
								Label_13.setText(Long.toString(dfs.getInFrontier().size()));
								
								stack = utility.backtrack(dfs.getGoalState());
								
						
							}
						}
						else if (selected_item.equals("A* [distance Euclidienne]")) {
							Parent aStarEuclidean = new AStar();
							root.setMapping(array_init_Integer);
							if(solved = aStarEuclidean.solveStar(root, true)) {
								
								Label_5.setText(Long.toString(aStarEuclidean.getRunningTime()));
								Label_7.setText(String.valueOf(aStarEuclidean.getSearchDepth()));
								Label_9.setText(Double.toString(aStarEuclidean.getGoalState().getCost()));
								Label_11.setText(Long.toString(aStarEuclidean.getExplored().size()));
								Label_13.setText(Long.toString(aStarEuclidean.getInFrontier().size()));
								
								stack = utility.backtrack(aStarEuclidean.getGoalState());
								
						
							}
						}
						else if (selected_item.equals("A* [distance de Manhattan]"))  {
							Parent aStarManhattan = new AStar();
							root.setMapping(array_init_Integer);
							if(solved = aStarManhattan.solveStar(root, false)) {
								
								Label_5.setText(Long.toString(aStarManhattan.getRunningTime()));
								Label_7.setText(String.valueOf(aStarManhattan.getSearchDepth()));
								Label_9.setText(Double.toString(aStarManhattan.getGoalState().getCost()));
								Label_11.setText(Long.toString(aStarManhattan.getExplored().size()));
								Label_13.setText(Long.toString(aStarManhattan.getInFrontier().size()));
								
								stack = utility.backtrack(aStarManhattan.getGoalState());
								
						
							}
						}
						if(!solved && !TextEdit1.getText().equals("") && !TextEdit1.getText().equals("")) {
							Run.setText("Insoluble!");
							Run.setBackground(new Color(200,0,42));
							Run.setForeground(Color.white);
						}
						
					
					}
				} ); 
	          
	         //action listener for the Show_Path button	
	           this.path.addActionListener(new ActionListener() {
					int i;
					@Override
					public void actionPerformed(ActionEvent e) {
					
						    if(!stack.isEmpty()) {
						    	i =0;
								State s = stack.pop();
								 for(int row = 0; row < 3; row++) {
										for(int col = 0; col < 3; col++) {
											array[row][col]= s.getMapping()[row][col];
											if(array[row][col] == 0)
											{tiles[i].setText("");
											 tiles[i].setBackground(new Color(137,219,236));
											 tiles[i].setForeground(Color.white);}
											else
												{tiles[i].setText(Integer.toString(array[row][col]));
												 tiles[i].setBackground(new Color(0,48,102));
												 tiles[i].setForeground(Color.white);}
											i++;
										}
									
								 }
						    }
						    else
						    {  if(solved)
						    	{path.setText("Résolu !");	
						    	path.setBackground(new Color(9,148,65));
						    	path.setForeground(Color.white);
						    	path.setFocusable(false);
						    	solved = false;
						    	}
						    	
						    }	
					}
				} ); 
	           //action listener for the reset button
		        this.reset.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						path.setText("Voir chemin");	
				    	path.setBackground(new Color(137,219,236));
				    	path.setForeground(new Color(0,48,102));
						TextEdit1.setText("");
						TextEdit2.setText("");
						Label_5.setText("0");
						Label_7.setText("0");
						Label_9.setText("0");
						Label_11.setText("0");
						
						
						 Tile_1.setText("1");
						 Tile_1.setBackground(new Color(0,48,102));
						 Tile_1.setForeground(Color.white);
						 
						 Tile_2.setText("2");
						 Tile_2.setBackground(new Color(0,48,102));
						 Tile_2.setForeground(Color.white);
						 
						 Tile_3.setText("3");
						 Tile_3.setBackground(new Color(0,48,102));
						 Tile_3.setForeground(Color.white);
						 
						 Tile_4.setText("4");
						 Tile_4.setBackground(new Color(0,48,102));
						 Tile_4.setForeground(Color.white);
						 
						 Tile_5.setText("5");
						 Tile_5.setBackground(new Color(0,48,102));
						 Tile_5.setForeground(Color.white);
						 
						 Tile_6.setText("6");
						 Tile_6.setBackground(new Color(0,48,102));
						 Tile_6.setForeground(Color.white);
						 
						 Tile_7.setText("7");
						 Tile_7.setBackground(new Color(0,48,102));
						 Tile_7.setForeground(Color.white);
						 
						 Tile_8.setText("8");
						 Tile_8.setBackground(new Color(0,48,102));
						 Tile_8.setForeground(Color.white);
						 
						 Tile_9.setText("");
						 Tile_9.setBackground(new Color(137,219,236));
						 
					}
				
				} ); 
	 }
	
	 
	 private void initComponents() {
	        java.awt.GridBagConstraints gridBagConstraints;

	        jPanel1 = new JPanel();
	        Main_Right = new JPanel();
	        ButtonsPanel = new JPanel();
	        reset = new JButton();
	        Run = new JButton();
	        jPanel2 = new JPanel();
	        Main_Middle = new JPanel();
	        Tile_1 = new JButton();
	        Tile_2 = new JButton();
	        Tile_3 = new JButton();
	        Tile_4 = new JButton();
	        Tile_5 = new JButton();
	        Tile_6 = new JButton();
	        Tile_7 = new JButton();
	        Tile_8 = new JButton();
	        Tile_9 = new JButton();
	        MAJ    = new JButton();
	        path    = new JButton();
	        Label_1 = new JLabel();
	        Label_2 = new JLabel();
	        Label_3 = new JLabel();
	        Label_4 = new JLabel();
	        Label_5 = new JLabel();
	        Label_6 = new JLabel();
	        Label_7 = new JLabel();
	        Label_8 = new JLabel();
	        Label_9 = new JLabel();
	        Label_10 = new JLabel();
	        Label_11 = new JLabel();
	        Label_12 = new JLabel();
	        Label_13 = new JLabel();
	        TextEdit1 = new JTextField();
	        TextEdit2 = new JTextField();
	        cb = new JComboBox<String>(mthds);
	        
	      

	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	       

	        Main_Right.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 20, 50, 50));
	        Main_Right.setLayout(new java.awt.BorderLayout());
	        
	        
	        ButtonsPanel.setLayout(new GridLayout(10,2 ,10,10));
	        Font font = new Font("Lucida", Font.BOLD,15);

	        //Label 1
			Label_1.setText("Etat initial");
			Label_1.setFont(font);
			ButtonsPanel.add(Label_1 );
			
			//TextField1
			
			TextEdit1.setPreferredSize(new Dimension(60,20));
			TextEdit1.setFont(font);
			TextEdit1.setForeground(new Color(0,48,102));
			ButtonsPanel.add(TextEdit1);
			//Bouton MAJ
			MAJ.setText("Mettre à jour ");
			MAJ.setBackground(new Color(137,219,236));
			MAJ.setForeground(new Color(0,48,102));
			MAJ.setFont(new Font("Lucida",Font.BOLD,15));
			MAJ.setBounds(100,20,160,30);
			ButtonsPanel.add(MAJ);
			//Bouton2
			reset.setText("Réinitialiser");
			reset.setBackground(new Color(137,219,236));
			reset.setForeground(new Color(0,48,102));
			reset.setFont(new Font("Lucida",Font.BOLD,15));
			reset.setBounds(100,20,160,30);
			ButtonsPanel.add(reset);
	        Main_Right.add(ButtonsPanel, java.awt.BorderLayout.LINE_START);
			
			//Label 2
			Label_2.setText("Etat but"); 
			Label_2.setFont(font);
			ButtonsPanel.add(Label_2 );
			//TextField2
			 
			TextEdit2.setPreferredSize(new Dimension(120,30));
			TextEdit2.setFont(font);
			TextEdit2.setForeground(new Color(0,48,102));
			ButtonsPanel.add(TextEdit2);
			//Label 3
			Label_3.setText(" Méthode de résolution");
			Label_3.setFont(font);
			ButtonsPanel.add(Label_3 );
			//Select Menu 
			//cb.setBackground(new Color(0, 66, 54));
			//cb.setForeground(new Color(0, 144, 158));
			cb.setFont(new Font("Lucida",Font.BOLD,13));
			ButtonsPanel.add(cb);
			//Bouton Run
			Run.setText("Résoudre"); 
			Run.setBackground(new Color(137,219,236));
			Run.setForeground(new Color(0,48,102));
			Run.setFont(new Font("Lucida",Font.BOLD,15));
			Run.setBounds(100,20,160,30);
			ButtonsPanel.add(Run);
			//Bouton2
			path.setText("Voir chemin");
			path.setBackground(new Color(137,219,236));
			path.setForeground(new Color(0,48,102));
			path.setFont(new Font("Lucida",Font.BOLD,15));
			path.setBounds(100,20,160,30);
			ButtonsPanel.add(path);
	        Main_Right.add(ButtonsPanel, java.awt.BorderLayout.LINE_START);
		
			//Label 4
			Label_4.setText("Temps d'exécution (ms)");
			Label_4.setFont(font);
			ButtonsPanel.add(Label_4 );
			//Label 5
			Label_5.setText("0");
			Label_5.setFont(font);
			Label_5.setForeground(new Color(0,48,102));
			ButtonsPanel.add(Label_5 );
			//Label 6
			Label_6.setText("Profondeur maximale");
			Label_6.setFont(font);
			ButtonsPanel.add(Label_6 );
			//Label 7
			Label_7.setText("0");
			Label_7.setFont(font);
			Label_7.setForeground(new Color(0,48,102));
			ButtonsPanel.add(Label_7);
			//Label 8
			Label_8.setText("Coût");
			Label_8.setFont(font);
			ButtonsPanel.add(Label_8 );
			//Label 9
			Label_9.setText("0");
			Label_9.setFont(font);
			Label_9.setForeground(new Color(0,48,102));
			ButtonsPanel.add(Label_9);
			//Label 10
			Label_10.setText("Nombre de nœuds développés");
			Label_10.setFont(font);
			ButtonsPanel.add(Label_10 );
			//Label 11
			Label_11.setText("0");
			Label_11.setFont(font);
			Label_11.setForeground(new Color(0,48,102));
			ButtonsPanel.add(Label_11);
			//Label 12
			Label_12.setText("Nombre de nœuds générés");
			Label_12.setFont(font);
			ButtonsPanel.add(Label_12 );
			//Label 13
			Label_13.setText("0");
			Label_13.setFont(font);
			Label_13.setForeground(new Color(0,48,102));
			ButtonsPanel.add(Label_13);
			

	        getContentPane().add(Main_Right, java.awt.BorderLayout.LINE_START);

	       
             //main middle*************************************
	        Main_Middle.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 50, 50, 50));
	        Main_Middle.setLayout(new java.awt.GridBagLayout());

	        Tile_1.setText("1");
	        gridBagConstraints = new java.awt.GridBagConstraints();
	        gridBagConstraints.gridx = 0;
	        gridBagConstraints.gridy = 0;
	        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
	        gridBagConstraints.ipadx = 70;
	        gridBagConstraints.ipady = 70;
	        Tile_1.setFont(new Font("Lucida",Font.BOLD,14));
	        Tile_1.setBackground(new Color(0,48,102));
	        Tile_1.setForeground(Color.white);
	        Main_Middle.add(Tile_1, gridBagConstraints);

	        Tile_2.setText("2");
	        gridBagConstraints = new java.awt.GridBagConstraints();
	        gridBagConstraints.gridx = 1;
	        gridBagConstraints.gridy = 0;
	        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
	        gridBagConstraints.ipadx = 70;
	        gridBagConstraints.ipady = 70;
	        Tile_2.setFont(new Font("Lucida",Font.BOLD,14));
	        Tile_2.setBackground( new Color(0,48,102));
	        Tile_2.setForeground(Color.white);
	        Main_Middle.add(Tile_2, gridBagConstraints);

	        Tile_3.setText("3");
	        gridBagConstraints = new java.awt.GridBagConstraints();
	        gridBagConstraints.gridx = 2;
	        gridBagConstraints.gridy = 0;
	        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
	        gridBagConstraints.ipadx = 70;
	        gridBagConstraints.ipady = 70;
	        Tile_3.setFont(new Font("Lucida",Font.BOLD,14));
	        Tile_3.setBackground(new Color(0,48,102));
	        Tile_3.setForeground(Color.white);
	        Main_Middle.add(Tile_3, gridBagConstraints);

	        Tile_4.setText("4");
	        gridBagConstraints = new java.awt.GridBagConstraints();
	        gridBagConstraints.gridx = 0;
	        gridBagConstraints.gridy = 1;
	        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
	        gridBagConstraints.ipadx = 70;
	        gridBagConstraints.ipady = 70;
	        Tile_4.setFont(new Font("Lucida",Font.BOLD,14));
	        Tile_4.setBackground(new Color(0,48,102));
	        Tile_4.setForeground(Color.white);
	        Main_Middle.add(Tile_4, gridBagConstraints);

	        Tile_5.setText("5");
	        gridBagConstraints = new java.awt.GridBagConstraints();
	        gridBagConstraints.gridx = 1;
	        gridBagConstraints.gridy = 1;
	        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
	        gridBagConstraints.ipadx = 70;
	        gridBagConstraints.ipady = 70;
	        Tile_5.setFont(new Font("Lucida",Font.BOLD,14));
	        Tile_5.setBackground(new Color(0,48,102));
	        Tile_5.setForeground(Color.white);
	        Main_Middle.add(Tile_5, gridBagConstraints);

	        Tile_6.setText("6");
	        gridBagConstraints = new java.awt.GridBagConstraints();
	        gridBagConstraints.gridx = 2;
	        gridBagConstraints.gridy = 1;
	        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
	        gridBagConstraints.ipadx = 70;
	        gridBagConstraints.ipady = 70;
	        Tile_6.setFont(new Font("Lucida",Font.BOLD,14));
	        Tile_6.setBackground(new Color(0,48,102));
	        Tile_6.setForeground(Color.white);
	        Main_Middle.add(Tile_6, gridBagConstraints);

	        Tile_7.setText("7");
	        gridBagConstraints = new java.awt.GridBagConstraints();
	        gridBagConstraints.gridx = 0;
	        gridBagConstraints.gridy = 2;
	        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
	        gridBagConstraints.ipadx = 70;
	        gridBagConstraints.ipady = 70;
	        Tile_7.setFont(new Font("Lucida",Font.BOLD,14));
	        Tile_7.setBackground(new Color(0,48,102));
	        Tile_7.setForeground(Color.white);
	        Main_Middle.add(Tile_7, gridBagConstraints);
	        
	        Tile_8.setText("8");
	        gridBagConstraints = new java.awt.GridBagConstraints();
	        gridBagConstraints.gridx = 1;
	        gridBagConstraints.gridy = 2;
	        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
	        gridBagConstraints.ipadx = 70;
	        gridBagConstraints.ipady = 70;
	        Tile_8.setFont(new Font("Lucida",Font.BOLD,14));
	        Tile_8.setBackground(new Color(0,48,102));
	        Tile_8.setForeground(Color.white);
	        Main_Middle.add(Tile_8, gridBagConstraints);
	        
	        
	        gridBagConstraints = new java.awt.GridBagConstraints();
	        gridBagConstraints.gridx = 2;
	        gridBagConstraints.gridy = 2;
	        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
	        gridBagConstraints.ipadx = 70;
	        gridBagConstraints.ipady = 70;
	        Tile_9.setFont(new Font("Lucida",Font.BOLD,14));
	        //Tile_8.setForeground(Color.white);
	        Tile_9.setBackground(new Color(137,219,236));
	        
	        Main_Middle.add(Tile_9, gridBagConstraints);

	        getContentPane().add(Main_Middle, java.awt.BorderLayout.CENTER);
	     
	        pack();
	      
	    }
	
	public static void main(String[] args) throws Exception {
		//Change look'n fell
				UIManager.setLookAndFeel(new NimbusLookAndFeel());
				
		//Start  window
			Main Window = new Main();
		Window.setVisible(true);
		
	
	}
	
}
