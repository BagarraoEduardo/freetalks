package eduardo.bagarrao.freetalks.util;

public class Pair<T,K> {
	
	private T first;
	private K second;
	
	public Pair(){
		this.first= null;
		this.second = null;
	}
	
	public Pair(T first, K second) {
		this.first = first;
		this.second = second;
	}
	
	public T getFirst() {
		return first;
	}
	
	public K getSecond() {
		return second;
	}
	
	public void setFirst(T first) {
		this.first = first;
	}
	
	public void setSecond(K second) {
		this.second = second;
	}
}
