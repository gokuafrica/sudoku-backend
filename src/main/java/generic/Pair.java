package generic;

public class Pair<K, V> {

	public K x;
	public V y;

	public Pair(K element0, V element1) {
		this.x = element0;
		this.y = element1;
	}

	@Override
	public String toString() {
		return x + " " + y;
	}
}
