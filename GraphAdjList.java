import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

class GraphAdjList extends Graph{
	Map<Integer, List<Integer>> adjListMap = null;

	//Constructor
	public GraphAdjList(GraphType type){
		super(type);
		adjListMap = new HashMap<Integer, List<Integer>>();
	}

	//Add vertex
	public void implement_add_vertex(int v){
		if(!adjListMap.containsKey(v)){
			List<Integer> adjList = new LinkedList<Integer>();
			adjListMap.put(v, adjList);
		}
		
	}

	//Add edge
	public void implement_add_edge(int u, int v){
		//Add vertices if not already present in map
		add_vertex(u);
		add_vertex(v);
		
		//Get adjacency lists for both vertices.
		List<Integer> adjList_u = adjListMap.get(u);
		List<Integer> adjList_v = adjListMap.get(v);

		//Add vertices to their neighbor's adjacency list
		adjList_u.add(0, v);

		if(type == GraphType.UNDIR){
			adjList_v.add(0, u);
		}
	}

	public List<Integer> implement_get_neighbors(int v){
		List<Integer> ll = adjListMap.get(v);
		return ll;
	}

	public Set<Integer> implement_get_vertices(){
		Set<Integer> s = adjListMap.keySet();
		return s;
	}

	
	public static void main(String [] args){
		/*******************************/
		/******Undirected Graph*********/
		/*******************************/
		GraphAdjList gu = new GraphAdjList(GraphType.UNDIR);
		gu.add_edge(1, 2);
		gu.add_edge(1, 4);
		gu.add_edge(1, 3);
		//gu.add_edge(3, 4);
		gu.add_edge(2, 5);
		gu.add_edge(5, 6);
		gu.add_edge(7,8);
		
		Set<Integer> visited = new HashSet<Integer>();;

		/********Graph Traversals***********/
		//gu.dfs_rec(1, visited);
		//gu.dfs_st(1, visited);
		//gu.bfs_qu(1, visited);
		//gu.bidirectional_search(1, 6, visited);
		
		/***********Shortest path*************/
		int[] prev = new int[gu.V];
		int source = 1;
		int dest = 6;
		//gu.find_shortest_path_bfs(source , dest, visited, prev);
		int id = dest;
		//System.out.println("Shortest path between:" + source + " and " + dest + " (Backwards) = ");
		//while(id != -1){
		//	System.out.println(id);
		//	id = prev[id];
		//}
		


		/*******Find connected components in a graph******/
		//gu.print_connected_components();

		/********Check if a graph has cycle***********/
		boolean cycle = false;
		cycle = gu.check_cycle_undir(1, visited, -1);
		System.out.println("Graph has cycles? (True/False):" + cycle);
		visited = new HashSet<Integer>();
		Set<Integer> recactive = new HashSet<Integer>();
		cycle = gu.check_cycle_dir(1, visited, recactive);
		System.out.println("Graph has cycles? (True/False):" + cycle);
		/***********************************/
		/*********Directed Graph************/
		/***********************************/
		Graph gd = new GraphAdjList(GraphType.DIR);
		gd.add_edge(1, 2);
		gd.add_edge(2, 3);
		gd.add_edge(1,3);
		gd.add_edge(2,4);
		gd.add_edge(4,5);
		gd.add_edge(3,5);

		/**********Check for cycles********************/
		//The above directed graph has no cycles.
		//Add the fol. edges one by one as diff. examples of cycle
		//gd.add_edge(1,1);
		//gd.add_edge(2,1);
		
		visited = new HashSet<Integer>();
		//gd.dfs_rec(1, visited);

		visited = new HashSet<Integer>();
		//Set<Integer> recactive = new HashSet<Integer>();
		//cycle = gd.check_cycle_dir(1, visited, recactive);
		//System.out.println("Graph has a cycle? (True/False)" + cycle);
	


		/*************Topological Sort**************/
		
		visited = new HashSet<Integer>();
		Deque<Integer> stack = new ArrayDeque<Integer>();
		//gd.top_sort(1, visited, stack);
		//System.out.println("Topological sort");
		//while(!stack.isEmpty()){
		//	int w = stack.pop();
		//	System.out.println(w);
		//}


		 /********Find all paths in DAG*************/
		 visited = new HashSet<Integer>();
		 int[] path = new int[gd.V];
		 int path_index = 0;
		 //gd.find_all_paths(1,5,visited, path, path_index);

	}

	


}
