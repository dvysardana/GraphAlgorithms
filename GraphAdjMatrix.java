import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;

class GraphAdjMatrix extends Graph{

	boolean [][] adjMatrix;

	public GraphAdjMatrix(int V, GraphType type){
		super(type);
		adjMatrix = new boolean[V][V];
		this.V = V;
	}

	public void implement_add_vertex(int v){
		//Create a new matrix, and copy all the vertices over from
		//old matrix
	}

	public void implement_add_edge(int u, int v){
		adjMatrix[u][v] = true;
		
		if(type == GraphType.UNDIR){
			adjMatrix[v][u] = true;
		}
	}

	public List<Integer> implement_get_neighbors(int v){
		List<Integer> ll = new LinkedList<Integer>();
		for(int i=0; i<V; i++){
			if(adjMatrix[v][i] != false){
				ll.add(i);
			}
		}

		return ll;
	}

	public Set<Integer> implement_get_vertices(){
		Set<Integer> s = new HashSet<Integer>();
		for(int i=0; i<V; i++){
			s.add(i);
		}
		
		return s;
	}

	public static void main(String[] args){
		
		/***********************************************/
		/*********Undirected Graph************************/
		/***********************************************/
		int V = 8;
		GraphAdjMatrix gu = new GraphAdjMatrix(V, GraphType.UNDIR);
		gu.add_edge(0,1);
		gu.add_edge(0,2);
		gu.add_edge(2,3);
		gu.add_edge(0,3);
		gu.add_edge(1,4);
		gu.add_edge(4,5);
		gu.add_edge(6,7);
		Set<Integer> visited = new HashSet<Integer>();
		//gu.dfs_rec(0, visited);
		//gu.dfs_st(0, visited);
		//gu.bfs_qu(0, visited);
		//gu.bidirectional_search(0, 5, visited);
		
		/************************/
		//Find shortest path between two vertices using BFS
		/************************/
		//int[] prev = new int[V];
		//int source = 0;
		//int dest = 5;
		//gu.find_shortest_path_bfs(source , dest, visited, prev);
		//int id = dest;
		//System.out.println("Shortest path between:" + source + " and " + dest + " (Backwards) = ");
		//while(id != -1){
		//	System.out.println(id);
		//	id = prev[id];
		//}

		
		/*******Find connected components in a graph******/
		gu.print_connected_components();
		
		/***********************/
		//Check cycle in undirected graph
		/***********************/
		int parent = -1;
		boolean cycle = false; 
		//cycle = gu.check_cycle_undir(0, visited, parent);
		//System.out.println("Is there a cycle in the undirected graph(Yes/No)?: " + cycle);
	
	
		/***********************************************/
		/*********Directed Graph************************/
		/***********************************************/
		Graph gd = new GraphAdjMatrix(3, GraphType.DIR);
		gd.add_edge(0, 1);
		gd.add_edge(1, 2);
		gd.add_edge(0,2);

		/**********Check for cycles********************/
		//The above directed graph has no cycles.
		//Add the fol. edges one by one as diff. examples of cycle
		gd.add_edge(0,0);
		//gd.add_edge(1,0);
		
		//visited = new HashSet<Integer>();
		//gd.dfs_rec(1, visited);

		visited = new HashSet<Integer>();
		Set<Integer> recactive = new HashSet<Integer>();
		//cycle = gd.check_cycle_dir(0, visited, recactive);
		//System.out.println("Graph has a cycle? (True/False)" + cycle);
	}



}
