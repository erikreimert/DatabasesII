import java.util.*;

public class Hash extends reader{
	
	private Hashtable<Integer, List<String>> h = new Hashtable<Integer, List<String>>();
	private Hashtable<String, ArrayList<Integer>> h2 = new Hashtable<String, ArrayList<Integer>>();

	/* creates the hash array index
	 * key = RandomV, value = col1 and col2 of record
	 */
	public void init(String data) {
 
		int position = 4;
		
			for (int j = 1; j<=100;j++) {
				
				String sub = data.substring(position, position+40); // gets the record and extracts the key and position of it
				String keyS = sub.substring(33, 37);
				sub = sub.substring(4, 19);
				int key = Integer.parseInt(keyS);
				
				if(this.h.containsKey(key)) { // if already a key then add new file num and position to list of fnum and positions
					List<String> replace = this.h.get(key);
					replace = new ArrayList<>(replace);
					replace.add(sub);
					this.h.replace(key, replace);					
				}else {
					List<String> add = Arrays.asList(sub);
					this.h.put(key, add);
				}
				position +=40;
			}
	}
	
	/* creates the hash array index
	 * key = Col2 value, value = List of randomV associated with that name
	 */
	public void sec3(String data) {
		 
		int position = 4;
		
			for (int j = 1; j<=100;j++) {
				
				String sub = data.substring(position, position+40); // gets the record and extracts the key and position of it
				String rvs = sub.substring(33, 37);
				int rv = Integer.parseInt(rvs);
				String key = sub.substring(12, 19);
				
				if(this.h2.containsKey(key)) { // if already a key then add new file num and position to list of fnum and positions
					ArrayList<Integer> replace = this.h2.get(key);
					replace.add(rv);
					this.h2.replace(key, replace);					
				}else {
					ArrayList<Integer> add = new ArrayList<Integer>();
					add.add(rv);
					this.h2.put(key, add);
				}
				position +=40;
			}
	}
	
	/* takes in read file, returns array of RandomV
	 * checks if RandomV in file for DatasetB exists in the hash and if so stores it in the proper bucket and writes 
	 * down what bucket it is for printing
	 */
	public ArrayList<Integer> checkb(String data) {
		 
		int position = 4;
		
			ArrayList<Integer> pbucket = new ArrayList<Integer>();
			for (int j = 1; j<=100;j++) {
				
				String sub = data.substring(position, position+40); // gets the record and extracts the key and position of it
				String keyS = sub.substring(33, 37);
				sub = sub.substring(4, 19);
				int key = Integer.parseInt(keyS);

				
				if(this.h.containsKey(key)) { // if already a key then add new file num and position to list of fnum and positions
					List<String> replace = this.h.get(key);
					replace = new ArrayList<>(replace);
					replace.add(sub);
					this.h.replace(key, replace);
					pbucket.add(key);
				}
				position +=40;
			}
		return pbucket;
	}
	
	//returns this h1
	public Hashtable<Integer, List<String>> get(){
		return this.h;
	}
	
	//returns this h2
	public Hashtable<String, ArrayList<Integer>> get2(){
		return this.h2;
	}

} 



