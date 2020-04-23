import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class reader {
	
	
	/* input a file num returns data in file
	 * reads file and puts it in a string
	 */
	public String readRec(int fnum) {

		String fileName = "src/Project2Dataset/" + "F" + fnum + ".txt"; //get the file name

		String data = null;

		try {
			File file = new File(fileName); //read content with appropriate file number
			Scanner myReader = new Scanner(file);

			while (myReader.hasNextLine()) {
				data = data + myReader.nextLine();
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return data;
	}
	
	//returns whatever record you want for printing based on file number and position
	public String printRec(int fnum, int position) {

		String fileName = "src/Project2Dataset/" + "F" + fnum + ".txt"; //get the file name
		String rec = null;
		String data = null;

		try {
			File file = new File(fileName); //read content with appropriate file number
			Scanner myReader = new Scanner(file);



			while (myReader.hasNextLine()) {
				data = data + myReader.nextLine();
			}
			rec = data.substring(position, position+40); //the record you wanted
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return rec;
	}
}
