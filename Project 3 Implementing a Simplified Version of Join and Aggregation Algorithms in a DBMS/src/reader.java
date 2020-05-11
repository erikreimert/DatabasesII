import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class reader {
	
	
	/* input a file num and what dataset returns data in file
	 * reads file and puts it in a string
	 */
	public String readRec(int fnum, String A) {
		String fileName;
		if(A.equals("A")) {
			fileName = "src/Project3Dataset-A/" + "A" + fnum + ".txt"; //get the file name
		}else {
		fileName = "src/Project3Dataset-B/" + "B" + fnum + ".txt"; //get the file name
		}
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
	
}
