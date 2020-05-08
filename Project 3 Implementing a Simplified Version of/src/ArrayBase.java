public class ArrayBase extends reader {
	private  int[] array = new int[5001];
	

	//initializes the array with empty values
	public void init() {
		
		for(int i = 0; i<101;i++) {
			this.array[i] = 0;
		}
	}
	
	//takes in a file as string and a file number, returns populated array
	public int[] populate(String data, int ifnum) {


		int position = 4;
		
		for (int j = 1; j<=100;j++) {
			String sub = data.substring(position, position+40); // gets the record and extracts the key
			String keyS = sub.substring(33, 37);
			int key = Integer.parseInt(keyS);
			this.array[j] = key;
			
			position +=40;
		}
	return this.array;
	}

	public int[] get() {
		return this.array;
	}
}
