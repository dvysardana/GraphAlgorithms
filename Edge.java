class Edge<T>{
	Vertex<T> to;
	int weight;

	public Edge(Vertex<T> to, int weight){
		this.to = to;
		this.weight = weight;
	}

	public Vertex<T> get_to(){
		return to;
	}

	public void set_to(Vertex<T> to){
		this.to = to;
	}

	public int get_weight(){
		return weight;
	}

	public void set_weight(int weight){
		this.weight = weight;
	}
}
