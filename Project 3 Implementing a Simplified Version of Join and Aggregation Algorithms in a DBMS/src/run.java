import java.time.ZonedDateTime;
import java.util.*;


public class run {

	public static void main(String[] args){

		Scanner keyboard = new Scanner(System.in);
		reader r = new reader();

		while(true) {

			System.out.print("\nPlease input the next command or type 'E' for exit\n");	

			//scan for next command 
			String check = keyboard.nextLine();
			check = check.toUpperCase();
			check = check.replaceAll("\\s+","");
			String commandarr[] = check.split("FROM");
			String command = commandarr[0];

			switch(command){
			case "SELECTA.COL1,A.COL2,B.COL1,B.COL2": //Section 2
				long ti2 = ZonedDateTime.now().toInstant().toEpochMilli(); // get time
				Hash h = new Hash();
				ArrayList<Integer> print = new ArrayList<Integer>();
				for(int i = 1; i<= 99 ; i++) { //loop through 99 files, extract their data and put them in the hash and array

					String dataA = r.readRec(i, "A"); //read database A	
					h.init(dataA); //populate hash with Database A
				}
				for(int i = 1; i<= 99 ; i++) { //loop through 99 files, extract their data and put them in the hash and array

					String dataB = r.readRec(i, "B"); //read database B
					print = h.checkb(dataB); //go through files in Dataset B and put records in buckets according to join statement
				}
				for(int x: print) {

					System.out.print(h.get().get(x));
					System.out.print("\n");

				}
				long tf2 = ZonedDateTime.now().toInstant().toEpochMilli();
				long t2 = tf2-ti2; //final time minus initial
				System.out.print("The time this operation took in ms: " + t2 + "\n");
				break;
			case "SELECTCOUNT(*)": //Section 3
				long ti3 = ZonedDateTime.now().toInstant().toEpochMilli(); // get time

				/////////create lists to hold records, size 100 each slot stores randomv for succesive records
				ArrayBase fa = new ArrayBase();
				ArrayBase fb = new ArrayBase();
				fa.init();
				fb.init();
				////////
				int count = 0; //counter

				for(int i = 1; i <= 99; i++) {
					String fileA = r.readRec(i, "A");// read file in dataset A
					fa.populate(fileA, i); // populate array with records in file i of datasetA
					int[] comparea = fa.get();											
						for(int x = 1; x <= 99; x++) {						
							String fileB = r.readRec(x, "B"); // read file in dataset B
							fb.populate(fileB, x);// populate array with records in file x of datasetB
							int[] compareb = fb.get();							
							for(int japiro = 1; japiro <= 100;japiro++) {
							for(int y = 1; y <= 100;y++) {
								if(comparea[japiro] > compareb[y]) {
									count++;
								}
							}
						}
					}
				}

				System.out.print("The count is: " +count + "\n");

				long tf3 = ZonedDateTime.now().toInstant().toEpochMilli();
				long t3 = tf3-ti3; //final time minus initial
				System.out.print("The time this operation took in ms: " + t3 + "\n");
				break;
			case "SELECTCOL2,SUM(RANDOMV)": //Section 4
			case "SELECTCOL2,AVG(RANDOMV)":
				long ti4 = ZonedDateTime.now().toInstant().toEpochMilli(); // get time

				Hash h2 = new Hash();
				String[] dataArr = commandarr[1].split("GROUP");//extract which dataset the operation is on
				String dataset = dataArr[0];
				String[] comsplit = command.split(",");//get what command is going to be used either sum or avg

				for(int i = 1; i<= 99 ; i++) { //loop through 99 files, extract their data and put them in the hash and array

					String data = r.readRec(i, dataset); //read database A	or b depends on variable
					h2.sec3(data); //populate hash by group
				}
				Set<String> keys = h2.get2().keySet(); // Gets a set of the keys

				if(comsplit[1].equals("SUM(RANDOMV)")) {		
					for(String key: keys) {
						ArrayList<Integer> sumthis = h2.get2().get(key); //Gets the list of values
						int print2 = sumthis.stream().mapToInt(Integer::intValue).sum();
						System.out.print("Col 2: "+ key + ", Summed RandomV: " + print2 + "\n");
					}
				}else {	
					for(String key: keys) {
						ArrayList<Integer> sumthis = h2.get2().get(key); //Gets the list of values
						int len = sumthis.size();
						int print2 = sumthis.stream().mapToInt(Integer::intValue).sum();
						print2 = print2/len;
						System.out.print("Col 2: "+ key + ", AVG RandomV: " + print2 + "\n");
					}
				}
				long tf4 = ZonedDateTime.now().toInstant().toEpochMilli();
				long t4 = tf4-ti4; //final time minus initial
				System.out.print("The time this operation took in ms: " + t4 + "\n");
				break;
			case "E":
				System.out.println("\nExiting program now...\n");
				keyboard.close();
				System.exit(0);
				break;
			}
		}

	}
}
