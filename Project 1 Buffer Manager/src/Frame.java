import java.io.*;
import java.io.File;
//import java.io.IOException;
//import java.awt.Desktop;
import java.util.Scanner;

public class Frame{

	private String content;
	private boolean dirty; //set to TRUE if values changed
	private boolean pinned; //false means can be taken out, true means theres a request to keep in disk
	private int blockid;

	public String get1case(int record) {

		int position;
		if(record%100 != 0) {
			position = ((record%100)*40)-36; //get the first byte the record is taking up
		}else {
			position = 4;
		}

		String rec = this.content.substring(position, position+40); //the record you wanted

		return rec;
	}

	//changes desired record for case 1 of set
	public String set1case(int record, String newRec) {

		int position;
		if(record%100 != 0) {
			position = ((record%100)*40)-36; //get the first byte the record is taking up
		}else {
			position = 4;
		}
		String oldRec = this.content.substring(position, position+40);

		String data = this.content;

		data = data.replaceAll(oldRec, newRec);

		return data;
	}
	//changes the value of the record given with the new record inputted by the user
	public String changeRec(int record, String newRec) {

		double fum = Math.floor((record/100) +1);
		int fnum = (int)fum;

		String fileName = "src/Files/" + "F" + fnum + ".txt"; //get the file name

		int position;
		if(record%100 != 0) {
			position = ((record%100)*40)-36; //get the first byte the record is taking up
		}else {
			position = 4;
		}
		
		String data = null;

		try {
			File file = new File(fileName); //read content with appropriate file number
			Scanner myReader = new Scanner(file);

			while(myReader.hasNextLine()) {
				data = data + myReader.nextLine();
			}
			String oldRec = data.substring(position, position+40);
			if(data.contains(oldRec)){
				data = data.replaceAll(oldRec, newRec);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return data;
	}

	//returns whatever record you want for printing
	public String printRec(int record) {

		double fum = Math.floor((record/100) +1);
		int fnum = (int)fum;

		String fileName = "src/Files/" + "F" + fnum + ".txt"; //get the file name
		int position;
		if(record%100 != 0) {
			position = ((record%100)*40)-36; //get the first byte the record is taking up
		}else {
			position = 4;
		}

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

	//returns full record in file
	public String readRec(int record) {

		double fum = Math.floor((record/100) +1);
		int fnum = (int)fum;

		String fileName = "src/Files/" + "F" + fnum + ".txt"; //get the file name

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

	//overwrites the disk data when frames get kicked out of buffer
	public void overwrite(int fnum) {

		this.pinned = false; //because its kicked out of memory
		this.dirty = false; //because the new file is not altered

		File fold = new File("src/Files/" + "F" + fnum + ".txt");
		fold.delete();

		File fnew = new File("src/Files/" + "F" + fnum + ".txt");
		String source = this.content;

		try {
			FileWriter f2 = new FileWriter(fnew, false);
			f2.write(source);
			f2.close();

		} catch (IOException e) {
			e.printStackTrace();
		}  
	};

	/*
	 * calculate the record based on the number, bring to frame and change it
	 * 
	 * Output From “Set” Command: The required output is:
(1) Whether or not the write is successful
(2) Whether or not an I/O is done (i.e., whether the block was already in memory or brought from disk)
(3) The frame# (the slot number in the buffers array) that contains the block (for CASES #1,2,3)
	 */
	public void sets(int record, String newRec, BufferPool a){ //used to add new content set dirty flag to TRUE

		double d = Math.floor((record/100) +1);
		int fnum = (int)d;
		this.blockid = fnum;

		if (a.search(fnum) != -1) { //////case1

			//			String check1 = this.content;
			//			this.download(fnum, a.getcont(fnum), a.getdir(fnum), a.getpin(fnum));
			//			String check = this.content;
			this.dirty = true;
			this.content = this.set1case(record, newRec);
			int locale = a.search(fnum); // gets the position in the buff array of the frame
//			a.update(fnum, this.content, this.pinned, this.dirty);
			System.out.println("\nWrite Succesful; File " + fnum + " already in memory; Placed in Frame #" + (locale+1) + "\n"); //case 1 in bufferPool

		}else {//////case 2

			if (a.fsearch(this.blockid, this, fnum) != -1) {  //if there is a frame available

				this.dirty = true;
				this.content = this.changeRec(record, newRec);
				int locale = a.search(fnum);
				a.update(fnum, this.content, this.pinned, this.dirty);
				System.out.print("\nWrite Succesful; Brought file "+ fnum+" from disk; Block is in Frame #" + (locale+1) + "\n");


			}else { /////// case 3

				int p = a.psearch(this.blockid, this); //case 4 embedded on psearch
				if (p == 0) { //if there is a unpinned frame
					//					this.sets(record, newRec, a);
					this.dirty =true;
					this.content = this.changeRec(record, newRec);
					int locale = a.search(fnum);
					a.update(fnum, this.content, this.pinned, this.dirty);
					System.out.print("\nWrite Succesful; Brought file "+ fnum +" from disk; Block is in Frame #" + (locale+1) + "\n");

				}
			}
		}
	};


	/* returns modification successful
	 * Call search() in buffer class to receive block if -1 then doesn't exist
	 * calculate in what file the function is
	 */
	public void gets(int record, BufferPool a){ 
		double d = Math.floor((record/100) +1);
		int fnum = (int)d;
		this.blockid = fnum;

		if (a.search(fnum) != -1) { //////case1



			//			this.download(fnum, shit, crap, dick);
			//			String ah = this.content;
//			a.update(fnum, this.content, this.pinned, this.dirty);
			int locale = a.search(fnum); // gets the frame position in the buffer array
			System.out.println(this.get1case(record) + "\n File " + fnum + " already in memory; Placed in Frame #" + (locale+1) + "\n"); //case 1 in bufferPool

		}else {//////case 2
			if (a.fsearch(this.blockid, this, fnum) != -1) {

				//				System.out.print(a.buffers.get(0).getblockid());


				this.content = this.readRec(record);
				a.update(fnum, this.content, this.pinned, this.dirty);
				int locale = a.search(fnum); //////case1
				System.out.println(this.printRec(record) + "\nBrought file "+ fnum+" from disk; Block is in Frame #" + (locale+1) + "\n"); //case 1 in bufferPool

			}else { /////// case 3

				int p = a.psearch(this.blockid, this); //case 4 embedded on psearch
				if (p == 0) {


					this.content = this.readRec(record);
					a.update(fnum, this.content, this.pinned, this.dirty);
					int locale = a.search(fnum); //////case1
					System.out.println(this.printRec(record) + "\nBrought file "+ fnum+" from disk; Block is in Frame #" + (locale+1) + "\n"); //case 1 in bufferPool
				}
			}
		}
	};


	//helper for pin, checks if something was pinned previously
	public void checker(boolean check) {
		if (check) {
			System.out.print("Pinned flag was already true\n");
		}else {
			System.out.print("Pinned flag was not set to true beforehand\n");
		}
	}


	// sets pinned flags to true when COMMAND 3 is inputted
	public void pins(int fnum, BufferPool a) {

			if (a.fsearch(this.blockid, this, fnum) != -1) {

				this.pinned = true;
				this.content = this.readRec(fnum);
				a.update(fnum, this.content, this.pinned, this.dirty);
				System.out.print("Pinned flag was not set to true beforehand and was just brought to memory\n");
				int locale = a.search(fnum);
				System.out.print("Frame #" + (locale+1) + " pinned\n");

			}else 
				if(a.psearch(fnum, this) == 0) { //case 3 is in psearch

					this.pinned = true;
					this.content = this.readRec(fnum);
					a.update(fnum, this.content, this.pinned, this.dirty);
					System.out.print("Pinned flag was not set to true beforehand and was just brought to memory\n");
					int locale = a.search(fnum);
					System.out.print("Frame #" + (locale+1) + " pinned\n");
				}
	}




	// sets pinned flags to false when COMMAND 4 is inputted
	public void unpins(int fnum, BufferPool a) {

		boolean checker = this.pinned;
		int locale = a.search(fnum);

		System.out.print("File "+fnum+" in frame #" + (locale+1) + " unpinned\n");
		this.pinned = false;
		a.update(fnum, this.content, this.pinned, this.dirty);

		if (!checker) {
			System.out.print("Pinned flag was already false\n");
		}else {
			System.out.print("Pinned flag was not set to false beforehand\n");
		}
	}





	//gets blockid
	public int getblockid() {
		return this.blockid;
	}

	public void setblockid(int fnum) {
		this.blockid = fnum;
	}

	//gets pinned 
	public boolean getpinned() {
		return this.pinned;
	}

	//sets pinned 
	public boolean setpinned(boolean AH) {
		return this.pinned = AH;
	}

	//gets dirty 
	public boolean getdirty() {
		return this.dirty;
	}

	//sets dirty 
	public boolean setdirty(boolean AH) {
		return this.dirty = AH;
	}

	//gets content 
	public String getcontent() {
		return this.content;
	}
	//sets content 
	public String setcontent(String AH) {
		return this.content = AH;
	}

	public void download(int id, String cont, boolean dirty, boolean pin) {
		this.blockid = id;
		this.content = cont;
		this.dirty = dirty;
		this.pinned = pin;
	}




	//initializes a frame for the bufferpool
	public void init() {
		this.blockid = -1;
		this.content = "";
		this.dirty = false;
		this.pinned = false;
	}
}
