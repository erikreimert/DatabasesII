import java.util.*;
import java.time.ZonedDateTime;


public class run {

	public static void main(String[] args) {

		Scanner keyboard = new Scanner(System.in);
		Hash h = new Hash();
		reader r = new reader();
		ArrayBase a = new ArrayBase();
		String[] arry = new String[5001];
		Hashtable<Integer, List<String>> hasher = new Hashtable<Integer, List<String>>();

		while(true) {

			System.out.print("\nPlease input the next command or type 'E' for exit\n");	

			//scan for next command 
			String check = keyboard.nextLine();
			check = check.toUpperCase();
			String create = "CREATE INDEX ON RANDOMV";

			if(check.equals(create)) { // sections 2 & 3
				
				a.init(); //initialize array to empty strings
				
				for(int i = 1; i<= 99 ; i++) { //loop through 99 files, extract their data and put them in the hash and array

					String data = r.readRec(i);
					
					//initialize hash
					hasher = h.init(data, i);

					// populate Array
					arry = a.populate(data, i);		

				}
				System.out.print("The hash-based and array-based indexes are built\n");

			}else if(check.equals("E")){ // exit clause
				System.out.println("\nExiting program now...\n");
				keyboard.close();
				System.exit(0);
			}else {
				
				//strips the user input for what will be the command
				check = check.replaceAll("\\s+",""); 
				String[] commandarr = check.split("PROJECT2DATASET");
				char command = commandarr[1].charAt(12);
				int blocks;

				switch(command) { //12
				case '=': // sec 4
					long ti = ZonedDateTime.now().toInstant().toEpochMilli(); //get time
					blocks = 0;
					ArrayList<Integer> count = new ArrayList<Integer>(); //used for counting files opened in operation
					String[] num = commandarr[1].split("=");
					int v = Integer.parseInt(num[1]);// gets key inputed by user as int

					List<String> vals = hasher.get(v);
					vals = new ArrayList<>(vals);
					int amount = (vals.size())-2; // how many prints iterations to do per randomv
					
					for(int i = 0; i <= amount; i+=2) {  
						int fnum = Integer.parseInt(vals.get(i));

						if(!(count.contains(fnum))) { //checks what files were opened
							count.add(fnum);
							blocks++;

						}

						String print = r.printRec(fnum, Integer.parseInt(vals.get(i+1)));
						System.out.print(print + "\n");
					}
					long tf = ZonedDateTime.now().toInstant().toEpochMilli(); 
					long t = tf-ti; //final time minus initial
					System.out.print("Using the Hash index\n");
					System.out.print("The number of files read was: " + blocks + "\n");
					System.out.print("The time this operation took in ms: " + t + "\n");

					break;
					
				case '>': //sec 5
					long ti5 = ZonedDateTime.now().toInstant().toEpochMilli(); // get time
					blocks = 0;
					ArrayList<Integer> count5 = new ArrayList<Integer>(); //used to check opened files
					String[] num5 = commandarr[1].split(">");
					num5 = num5[1].split("ANDRANDOMV<");
					int v5 = Integer.parseInt(num5[0]); //gets boundary key from user
					int v52 = Integer.parseInt(num5[1]); // gets second boundary key from user


					for(int x = v5+1; x < v52; x++) {// check array at parts needed
							
							String japiro = arry[x]; //get file nums and positions
							String[] vals5 = japiro.split("puta"); //get file nums and positions in array where evens are fnum and odds are positions
							int amount5 = (vals5.length)-2; // how many places records need to be read
							for(int i = 0; i <= amount5; i+=2) {

								int fnum = Integer.parseInt(vals5[i]);

								if(!(count5.contains(fnum))) {//check how many files are read 
									count5.add(fnum);
									blocks++;

								}


								String print = r.printRec(fnum, Integer.parseInt(vals5[i+1]));
								System.out.print(print + "\n");
							}
					}
					long tf5 = ZonedDateTime.now().toInstant().toEpochMilli();
					long t5 = tf5-ti5; //final time minus initial 
					System.out.print("Using the Array index\n");
					System.out.print("The number of files read was: " + blocks + "\n");
					System.out.print("The time this operation took in ms: " + t5 + "\n");
					break;

				case '!': //sec 6
					long ti6 = ZonedDateTime.now().toInstant().toEpochMilli(); // get time
					blocks = 0;
					ArrayList<Integer> count6 = new ArrayList<Integer>(); // used to check how many files were read
					String[] num6 = commandarr[1].split("!=");
					int v6 = Integer.parseInt(num6[1]); // get the key you dont want printed from user

					
					for(int i = 1; i<= 99 ; i++) { //loop through 99 files
						
						String data2 = r.readRec(i); //get records in file
						int position = 4;
						
						for (int j = 1; j<=100;j++) { //loop through 100 records in file
							
							String sub = data2.substring(position, position+40);
							String keyS = sub.substring(33, 37);
							int key = Integer.parseInt(keyS); //get key in current record
							
							int fnum = i;
							
							if(key != v6) { // current record not equal undesired record then print

								if(!(count6.contains(fnum))) {//checks how many files were used
									count6.add(fnum);
									blocks++;
								}

								String print = r.printRec(fnum, position);
								System.out.print(print + "\n");
							}
							position +=40;
						}
					}
					long tf6 = ZonedDateTime.now().toInstant().toEpochMilli();// get time
					long t6 = tf6-ti6; // final time minus initial
					System.out.print("No Index Used\n");
					System.out.print("The number of files read was: " + blocks + "\n");
					System.out.print("The time this operation took in ms: " + t6 + "\n");
					break;


				}
			}



		}
	}
}
