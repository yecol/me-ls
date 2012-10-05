package act.mashup.global;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Item implements Comparable {

	private Map<String, Object> KVMap;
	private Integer rank;

	public Item() {
		rank = 0;
		KVMap = new HashMap<String, Object>();
	}

	public Set<String> getKeys() {
		return KVMap.keySet();
	}

	public Object getValue(String key) {
		return KVMap.get(key);
	}

	public boolean RenameKey(String oldKey, String newKey) {
		if (KVMap.containsKey(oldKey) && !KVMap.containsKey(newKey)) {
			KVMap.put(newKey, KVMap.get(oldKey));
			KVMap.remove(oldKey);
			return true;
		} else
			return false;
	}

	public void setValue(String key, Object value) {
		KVMap.put(key, value);
	}

	public String toString() {
		return KVMap.toString();
	}

	public void addRank() {
		this.rank++;
	}

	public int getRank() {
		return this.rank;
	}
	
	public void rankToMap(){
		this.KVMap.put("rank", this.rank.toString());
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		Item other = (Item) o;
		return other.getRank() - this.rank;
	}
	

}