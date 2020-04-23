import java.util.*;

public class Hash extends reader{
	
	private Hashtable<Integer, List<String>> h = new Hashtable<Integer, List<String>>();

	/* creates the hash array index
	 * 
	 */
	public Hashtable<Integer, List<String>> init(String data,int ifnum) {
 
		int position = 4;
		
			for (int j = 1; j<=100;j++) {
				
				String sub = data.substring(position, position+40); // gets the record and extracts the key and position of it
				String keyS = sub.substring(33, 37);
				int key = Integer.parseInt(keyS);
				String fnum = Integer.toString(ifnum);
				String pos = Integer.toString(position);
				
				if(this.h.containsKey(key)) { // if already a key then add new file num and position to list of fnum and positions
					List<String> replace = this.h.get(key);
					replace = new ArrayList<>(replace);
					replace.add(fnum);
					replace.add(pos);
					this.h.replace(key, replace);					
				}else {
					List<String> add = Arrays.asList(fnum, pos);
//					add.add(fnum);
//					add.add(pos);
					this.h.put(key, add);
				}
				position +=40;
			}
		return this.h;
	}
	
	//returns this hashtable
	public Hashtable<Integer, List<String>> get(){
		return this.h;
	}

} 



