import java.util.List;
import java.util.LinkedList;

class Vertex<T> implements Comparable<T>{
	T data;
	int id;
	Status visited;
	List<Edge<T>> edges;
	Vertex<T> parent;
	int key;

	public Vertex(int id, T data){
		this.id = id;
		this.data = data;
		visited = Status.Unvisited;
		edges = new LinkedList<Edge<T>>();
		parent = null;
		key = Integer.MAX_VALUE;
	}

	public int get_id(){
		return id;
	}

	public void set_id(int id){
		this.id = id;
	}

	public List<Edge<T>> get_edge_list(){
		return this.edges;
	}

	public Status get_visited(){
		return visited;
	}

	public void set_visited(Status v){
		this.visited = v;
	}

	public T get_data(){
		return data;
	}

	public void set_data(T data){
		this.data = data;
	}

	public int get_key(){
		return key;
	}

	public void set_key(int key){
		this.key = key;
	}

	public Vertex<T> get_parent(){
		return this.parent;
	}

	public void set_parent(Vertex<T> v){
		this.parent = v;
	}
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}

		if(getClass() != obj.getClass()){
			return false;
		}

		final Vertex other = (Vertex) obj;
		if(this.data == other.data){
			return true;
		}

		return false;
	}

	@Override
	public int hashCode(){
		int hash = 5;
		hash = 59*hash + (this.data==null?0:this.data.hashCode());
		return hash;
	}

	@Override
	public int compareTo(Object obj) throws ClassCastException{
		if(!(obj instanceof Vertex)){
			throw new ClassCastException("A Vertex object is expected.");
		}

		final Vertex other = (Vertex) obj;
		

		int result = -1;
		
		if(this.key==other.key){
			result = 0;
		}else if(this.key>other.key){
			result = 1;
		}else{
			result = -1;
		}
		
		return result;
		
	}

	@Override
	public String toString(){
		return this.data.toString();
	}
}

enum Status {Unvisited, InProgress, Visited}
