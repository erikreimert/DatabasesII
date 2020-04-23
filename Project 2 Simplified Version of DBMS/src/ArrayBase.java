public class ArrayBase extends reader {
	private  String[] array = new String[5001];
	

	//initializes the array with empty values
	public void init() {
		
		for(int i = 0; i<5001;i++) {
			this.array[i] = "";
		}
	}
	
	//takes in a file as string and a file number, returns populated array
	public String[] populate(String data, int ifnum) {


		int position = 4;
		
		for (int j = 1; j<=100;j++) {
			String sub = data.substring(position, position+40); // gets the record and extracts the key
			String keyS = sub.substring(33, 37);
			int key = Integer.parseInt(keyS);
			
			String fnum = Integer.toString(ifnum); //gets fnum and position and turns them to string and adds same word next to it so i can split the string and have it as an array later where even numbers are file numbers and odds positions
			fnum = fnum.concat("puta");
			String pos = Integer.toString(position);
			pos = pos.concat("puta");
			
			String ahh = this.array[key]; //copies previously written values into new string and concats new values to it
			ahh = ahh.concat(fnum);
			ahh = ahh.concat(pos);
			this.array[key] = ahh;
			
			position +=40;
		}
		
			
			
	return this.array;
	}
}
