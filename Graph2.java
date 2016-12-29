import java.util.List;
import java.util.Iterator;
import java.util.Set;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.BitSet;

enum GraphType{
	DIR, UNDIR
}

abstract class Graph2<T>{
	int V; //NumVertices
	GraphType type;

	public Graph2(GraphType type){
		V = 0;
		this.type = type;
	}

	public int get_num_vertices(){
		return V;
	}

	public Vertex<T> add_vertex(int id, T data){
		Vertex<T> vo = implement_add_vertex(id, data);
		V=V+1;
		return vo;
	}

	public abstract Vertex<T> implement_add_vertex(int id, T data);

	public void add_edge(int uid, T udata, int vid, T vdata, int w){
		implement_add_edge(uid, udata, vid, vdata, w);
	}

	public abstract void implement_add_edge(int uid, T udata, int vid, T vdata, int w);

	public List<Edge<T>> get_neighbors(Vertex<T> v){
		List<Edge<T>> ll = implement_get_neighbors(v);
		return ll;
	}
	
	public abstract List<Edge<T>> implement_get_neighbors(Vertex<T> v);

	public Set<Vertex<T>> get_vertices(){
		Set<Vertex<T>> s = implement_get_vertices();
		return s;
	}

	public abstract Set<Vertex<T>> implement_get_vertices();
	
	//DFS (Recursive version)
	public void dfs_rec(Vertex<T> v){
		//Visit v
		if(v.get_visited() == Status.Unvisited){
		   v.set_visited(Status.Visited);
		   visit(v);
		                      
		   //Get all one hop neighbors of v
		   List<Edge<T>> ll = get_neighbors(v);
		   Iterator<Edge<T>> it = ll.iterator();
		   while(it.hasNext()){
			Edge<T> w = it.next();
			dfs_rec(w.get_to());
		   }       
		} 
	} 
        
	public void visit(Vertex<T> v){
		System.out.println("Visiting the vertex:" + v.get_data());
	}

	public void min_spanning_tree(Vertex<T> root){
		//Set<Vertex> mintree = new HashSet<Vertex>();

		//A priority queue to hold all vertices by their key value
		//(in non decreasing order)
		Queue<Vertex<T>> q = new PriorityQueue<Vertex<T>>();
	
		//A BitSet to avoid using queue contains method
		BitSet b = new BitSet(V);

		//Set root vertex's key to 0,
		//Rest all have key = Integer.MAX_VALUE
		root.set_key(0);
		
		//Get all vertices in this graph
		Set<Vertex<T>> vertices = get_vertices();

		//Add all vertices to a priority queue
		for(Vertex<T> v: vertices){
			q.offer(v);
		}
		
		while(!q.isEmpty()){
			Vertex<T> u = q.poll();
			b.set(u.get_id());

			//Add u to spanning tree.
			//May be not needed
			//parent pointers will help.
			
			//Update key for all adjacent vertices to u
			List<Edge<T>> elist = u.get_edge_list();
			Iterator<Edge<T>> it = elist.iterator();
			while(it.hasNext()){
				Edge<T> e = it.next();
				Vertex<T> w = e.get_to();
				//if(q.contains(w) && e.get_weight() < w.get_key()){
				if(!b.get(w.get_id()) && e.get_weight() < w.get_key()){
					w.set_key(e.get_weight());
					w.set_parent(u);
				}
			}
		}

		
		//return mintree;
	}
 
	//Complexity
	//O(V*Extractmin + E*DecreaseKey)
	//=O(VlogV + ElogV),
	//Using Fibonnaci heap: O(VlogV + E)
	public void dijkstra_shortest_path(Vertex<T> s){
	
		//A priority queue to hold all vertices in decreasing order of key
		//key is the distance estimate from source for each vertex.
		//Initially, it is = Integer.MAX_VALUE for each vertex.
		Queue<Vertex<T>> q = new PriorityQueue<Vertex<T>>();

		//Set source vertex's key to 0;
		s.set_key(0);

		//Add all vertices to q
		Set<Vertex<T>> vertices = get_vertices();
		for(Vertex<T> v: vertices){
			q.offer(v);
		}			

		while(!q.isEmpty()){
			//Add u to shortest path
			Vertex<T> u = q.poll();
		
			//Relax all adjacent edges for u.
			List<Edge<T>> elist = u.get_edge_list();
			Iterator<Edge<T>> it = elist.iterator();
			while(it.hasNext()){
			
				Edge<T> e = it.next();
				Vertex<T> w = e.get_to();
				if(w.get_key() > (u.get_key() + e.get_weight())){
					w.set_key(u.get_key() + e.get_weight());
					w.set_parent(u);
				}		
			}		
		}	


	}

  }
