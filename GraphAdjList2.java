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

class GraphAdjList2<T> extends Graph2<T>{
	Map<Integer, Vertex<T>> vertices = null;//Key is vertex id and value is vertex object

	//Constructor
	public GraphAdjList2(GraphType type){
		super(type);
		vertices = new HashMap<Integer, Vertex<T>>();
	}


	//Add vertex
	public Vertex<T> implement_add_vertex(int vid, T data){
		Vertex<T> v = null;
		if(!vertices.containsKey(vid)){
			v = new Vertex<T>(vid, data);
			vertices.put(vid, v);
		}else{
		//	System.out.println("Vertex already belongs to Graph.");
			v = vertices.get(vid);
		}
		return v;
	}

	
	//Add edge using objects
	public void implement_add_edge(int uid, T udata, int vid, T vdata, int weight){
		//Add vertices if not already present in graph
		Vertex<T> u = add_vertex(uid, udata);
		Vertex<T> v = add_vertex(vid, vdata);
		
		//Get adjacency lists for both vertices.
		List<Edge<T>> adjList_u = u.get_edge_list();
		List<Edge<T>> adjList_v = v.get_edge_list();

		//Add vertices to their neighbor's adjacency list
		adjList_u.add(new Edge<T>(v, weight));

		if(type == GraphType.UNDIR){
			adjList_v.add(new Edge<T>(u,weight));
		}
	}

	public List<Edge<T>> implement_get_neighbors(Vertex<T> v){
		return v.get_edge_list();
	}

	public Set<Vertex<T>> implement_get_vertices(){
		Set<Vertex<T>> vset = new HashSet<Vertex<T>>(vertices.values());
		return vset;
	}

	public static void main(String [] args){
		/*******************************/
		/******Undirected Graph*********/
		/*******************************/
		Graph2<Integer> gu = new GraphAdjList2<Integer>(GraphType.UNDIR);
		
		gu.add_edge(0, 0, 1, 1, 5);
		gu.add_edge(0, 0, 3, 3,  6);
		gu.add_edge(0, 0, 2, 2, 3);
		gu.add_edge(2, 2, 3, 3, 6);
		gu.add_edge(1, 1, 4, 4, 9);
		gu.add_edge(4, 4, 5, 5, 10);
		gu.add_edge(6, 6, 7, 7, 12);

		gu.add_edge(3, 3, 4, 4, 2);

		Vertex<Integer> v1 = gu.add_vertex(0, 0);
		/********Graph Traversal***********/
		//gu.dfs_rec(v1);
		
		/****Prim's algorithm (Min Spanning Tree)******/
		gu.min_spanning_tree(v1);
		
		//Print the min spanning tree
		System.out.println("Printing the minimum spanning tree (Vertex, parent).");
		for(Vertex<Integer> v: gu.get_vertices()){
			System.out.print(v + ",");
			System.out.println(v.get_parent() + ";");
		}


		/***********************************/
		/*********Directed Graph************/
		/***********************************/
		System.out.println("Directed Graph:");

		Graph2<Integer> gd = new GraphAdjList2<Integer>(GraphType.DIR);

		gd.add_edge(0,0, 1, 1, 5);
		gd.add_edge(1, 1, 2, 2, 7);
		gd.add_edge(0, 0, 2, 2, 2);
		gd.add_edge(1, 1, 3, 3, 6);
		gd.add_edge(3, 3, 4, 4, 4);
		gd.add_edge(2, 2, 4, 4, 3);

		/**********Check for cycles********************/
		//The above directed graph has no cycles.
		//Add the fol. edges one by one as diff. examples of cycle
		//gd.add_edge(1,1);
		//gd.add_edge(2,1);
		
		v1 = gd.add_vertex(0, 0);
		//gd.dfs_rec(v1);

			
	}

	


}
