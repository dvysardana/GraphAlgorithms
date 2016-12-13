import java.util.List;
import java.util.Iterator;
import java.util.Set;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.ArrayList;

enum GraphType{
	DIR, UNDIR
}

abstract class Graph{
	int V; //NumVertices
	GraphType type;

	public Graph(GraphType type){
		V = 0;
		this.type = type;
	}

	public int get_num_vertices(){
		return V;
	}

	public void add_vertex(int v){
		implement_add_vertex(v);
		V=V+1;
	}

	public abstract void implement_add_vertex(int v);

	public void add_edge(int u, int v){
		implement_add_edge(u, v);
	}

	public abstract void implement_add_edge(int u, int v);

	public List<Integer> get_neighbors(int v){
		List<Integer> ll = implement_get_neighbors(v);
		return ll;
	}
	
	public abstract List<Integer> implement_get_neighbors(int v);

	public Set<Integer> get_vertices(){
		Set<Integer> s = implement_get_vertices();
		return s;
	}

	public abstract Set<Integer> implement_get_vertices();
	
	//DFS (Recursive version)
	public void dfs_rec(int v, Set<Integer> visited){
		//Visit v
		if(!visited.contains(v)){
		   visited.add(v);
		   visit(v);
		                      
		   //Get all one hop neighbors of v
		   List<Integer> ll = get_neighbors(v);
		   Iterator<Integer> it = ll.iterator();
		   while(it.hasNext()){
			int w = it.next();
			dfs_rec(w, visited);
		   }       
		} 
	} 
        
	public void visit(int v){
		System.out.println("Visiting the vertex:" + v);
	}


	//DFS (using an explicit stack)
	public void dfs_st(int v, Set<Integer> visited){
		//Javadoc mentions to prefer Deque to implement
		// a stack vs. Stack because Deque is more consistent.
		//Perhaps it means to say that since stack inherits
		//from vector, methods to add anywhere in the stack are exposed
		//deque only exposes methods to add in front and last.
		
		Deque<Integer> stack = new ArrayDeque<Integer>();
		stack.push(v);

		while(!stack.isEmpty()){
			int u = stack.pop();
			if(!visited.contains(u)){
				visited.add(u);
				visit(u);

				//Get all neighbors of u
				List<Integer> ll = get_neighbors(u);
				Iterator<Integer> it = ll.iterator();
				while(it.hasNext()){
					int w = it.next();
					stack.push(w);	
				}	
			}
		}

	}

	//BFS (using queue)
	public void bfs_qu(int v, Set<Integer> visited){
		//As per javadoc. ArrayDeque implementation (resizable array)
		//of queue is faster than linkedlist implementation

		Deque<Integer> queue = new ArrayDeque<Integer>();
		queue.add(v);

		while(!queue.isEmpty()){
			int u = queue.remove();
			if(!visited.contains(u)){
				visit(u);
				visited.add(u);

				//Get all neighbors of u
				List<Integer> ll = get_neighbors(u);
				Iterator<Integer> it = ll.iterator();
				while(it.hasNext()){
					int w = it.next();
					queue.add(w);
				}
			}
		}
	}




	//Bidirectional search (Using two parallel BFSs)
	public void bidirectional_search(int u, int v, Set<Integer> visited){
		Deque<Integer> qu = new ArrayDeque<Integer>();
		Deque<Integer> qv = new ArrayDeque<Integer>();

		qu.add(u);
		qv.add(v);

		while(!qu.isEmpty() && !qv.isEmpty()){
			if(!qu.isEmpty()){
				int u1 = qu.pop();
				
				if(qv.contains(u1)){
					System.out.print("BFS 1:");
					visit(u1);
					System.out.println("Collision occurred.");
					break; //A collision has taken place.
				}
				
				if(!visited.contains(u1)){
					System.out.print("BFS 1:");
					visit(u1);
					visited.add(u1);
					List<Integer> ll = get_neighbors(u1);
					Iterator<Integer> it = ll.iterator();
					while(it.hasNext()){
						int w= it.next();
						qu.add(w);
					}

				}

			}

			if(!qv.isEmpty()){
				int v1 = qv.pop();

				if(qu.contains(v1)){
					System.out.print("BFS 2:");
					visit(v1);
					System.out.println("Collision occured.");
					break; //A collision has taken place
				}

				if(!visited.contains(v1)){
					System.out.print("BFS 2:");
					visit(v1);
					visited.add(v1);
					List<Integer> ll = get_neighbors(v1);
					Iterator<Integer> it = ll.iterator();
					while(it.hasNext()){
						int w = it.next();
						qv.add(w);
					}

				}

			}
		}

	}

	//Find shortest path using BFS (using queue)
	//Array prev is used to maintain predecessor for each vertex
	//***NOTE: This will give shortest path only for an undirected graph
	public void find_shortest_path_bfs(int v, int dest, Set<Integer> visited, int[] prev){
		//As per javadoc. ArrayDeque implementation (resizable array)
		//of queue is faster than linkedlist implementation

		Deque<Integer> queue = new ArrayDeque<Integer>();
		queue.add(v);
		prev[v] = -1;

		while(!queue.isEmpty()){
			int u = queue.remove();	

			if(!visited.contains(u)){
				visit(u);
				visited.add(u);
				if(u == dest){break;}	

				//Get all neighbors of u
				List<Integer> ll = get_neighbors(u);
				Iterator<Integer> it = ll.iterator();
				while(it.hasNext()){
					int w = it.next();
					queue.add(w);
					//Set the predecessor for a vertex only if
					//it has already not being visited.
					if(!visited.contains(w)){
						prev[w] = u;
					}
				}
			}
		}
	}
	
	//Print connected components in a graph
	public void print_connected_components(){
		Set<Integer> vertices = get_vertices();
		Iterator<Integer> it = vertices.iterator();
		Set<Integer> visited = new HashSet<Integer>();
		while(it.hasNext()){
			int v = it.next();
			if(!visited.contains(v)){
				System.out.println("Connected Component:");
				dfs_rec(v, visited);
			}
		}
	}


	//Check for cycle in an undirected graph
	//Reference: http://www.geeksforgeeks.org/detect-cycle-undirected-graph/
	public boolean check_cycle_undir(int v, Set<Integer> visited, int parent){
		
		if(!visited.contains(v)){
			visit(v);
			visited.add(v);
			List<Integer> ll = get_neighbors(v);
			Iterator<Integer> it = ll.iterator();
			while(it.hasNext()){
				int w = it.next();

				if(w != parent){
					if(true == check_cycle_undir(w, visited, v)){
						return true;
					}
				}else{
					System.out.println("Skip adding parent:" + parent + " to stack.");
				}
			}

			return false;
		
	
		}else{
			System.out.println("Cycle found at:" + v);
			return true;
		}

	}

	//Check for cycle in a directed graph
	//References: 
	//a. http://www.geeksforgeeks.org/detect-cycle-in-a-graph/
	//b. Cormen book explains the concept of recactive by assigning colors
	//to a vertex (white, gray, black) to mark the different stages it 
	//goes through.
	public boolean check_cycle_dir(int v, Set<Integer>visited, Set<Integer> recactive){

		if(!visited.contains(v)){
			visited.add(v);
			recactive.add(v);
			visit(v);
			List<Integer> ll = get_neighbors(v);
			Iterator<Integer> it = ll.iterator();
			while(it.hasNext()){
				int w = it.next();

				if(true == check_cycle_dir(w, visited, recactive)){
					return true;
				}
			}
			recactive.remove(v);;
		}else{
			if(recactive.contains(v)){
				return true;
			}

		}

		return false;
	}


	
	//Topological sort using DFS (Recursive version)
	public void top_sort(int v, Set<Integer> visited, Deque<Integer> stack){
		//Visit v
		if(!visited.contains(v)){
			visited.add(v);
			visit(v);
		

			//Get all one hop neighbors of v
			List<Integer> ll = get_neighbors(v);
			Iterator<Integer> it = ll.iterator();
			while(it.hasNext()){
				int w = it.next();
				top_sort(w, visited, stack);
			}
			stack.push(v);
		}
	}

	public void find_all_paths(int s, int d, Set<Integer> visited, int[] path, int path_index){
		if(!visited.contains(s)){
			visit(s);
			visited.add(s);
			path[path_index] = s;
			path_index = path_index + 1;
			if(s == d){
				print_path(path, path_index);
				//path.remove(path.size()-1);
			}
			List<Integer> ll = get_neighbors(s);
			Iterator<Integer> it = ll.iterator();
			while(it.hasNext()){
				int u = it.next();
				find_all_paths(u, d, visited, path, path_index);
			}
			visited.remove(s);
			path_index = path_index-1;
			//path.remove(path.size()-1);
		}
	}
	
	public void print_path(int[] path, int path_index){
		System.out.println("Path:");
		for(int i=0; i<path_index; i++){
			System.out.println(path[i] + ",");
		}
	}

	public Set<Integer> min_spanning_tree(int root){
		Set<Integer> mintree = new HashSet<Integer>();
		return mintree;
	}
  }
