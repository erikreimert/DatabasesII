import java.util.ArrayList;


public class BufferPool {
	private ArrayList<Frame> buffers = new ArrayList<Frame>();
	private int evicted = -1;
	private int evicheck;

	/*
	 * build array given input args
	 * go over each frame and initialize this frame
	 * 
	 */
	public void initialize(int arg) {

		ArrayList<Frame> temp = new ArrayList<Frame>();
		for (int i = 0; i < arg; i++) {
			Frame tempF = new Frame();
			tempF.init();
			temp.add(tempF);
		};
		this.buffers = temp;
	};

	/* takes in block id returns its position in buffer pool
	 * 
	 * search if certain block is available in the buffer pool
	 * if block doesn't exist return -1
	 */
	public int search(int id) {

		int i = 0;
		for (Frame f: this.buffers){ 
			if(f.getblockid() == id) {
				return i;
			}
			i++;
		}
		return -1;
	};

	//searches for an available frame in buffer and populates it
	public int fsearch(int id, Frame frame, int fnum) {


		for (Frame f: this.buffers){ 
			if(f.getblockid() == -1) {
				//				f = frame;
				f.setblockid(fnum);
				//				System.out.print(this.buffers.get(0).getblockid() + "\n");
				//				f.getblockid();
				return 0;
			}
		}
		return -1;
	};


	//searches for a frame in buffer with pinned flag set to false and replaces it
	public int psearch(int id, Frame frame) {
		int i = 0;
		if(this.evicted > this.evicheck) {
			this.evicted = 0;
		}
		for (Frame f: this.buffers){ 
			if(f.getpinned() == false) {
				if (f.getdirty() == false) {
					if(i >= this.evicted) {
						this.evicted = i;
					int locale = this.buffers.indexOf(f);
					int evict = f.getblockid();
					System.out.println("Evicted file "+ evict + " from Frame "+ (locale+1));
					f.setblockid(id);
					return 0;
					}else {
						i++;
						continue;
					}
				}else {
					if(i >= this.evicted) {
					int locale = this.buffers.indexOf(f);;	
					int evict = f.getblockid();
					System.out.println("Evicted file "+ evict + " from Frame "+ (locale+1));
					f.overwrite(id);
					f.setblockid(id);
					return 0;
					}else {
						i++;
						continue;}
				}
			}else { //////case 4 for gets and sets
				
			}
		}
		System.out.print("The corresponding block #" + id + " cannot be accessed from disk because the memory buffers are full");
		return -1;
	};


	//	//runs get from the buffer
	//	public void bget(int records) {
	//		Frame getty = new Frame();
	//		getty.gets(records, this);
	//
	//	};
	//
	//	//runs set from the buffer
	//	public void bset(int records, String newRec) {
	//		Frame setty = new Frame();
	//		setty.sets(records, newRec, this);
	//
	//	};

	//runs get from the buffer
	public void bget(int records) {
		double d = Math.floor((records/100) +1);
		int fnum = (int)d;

		if(this.search(fnum) != -1) {	
			//			Frame f = this.buffers.get(this.search(fnum));
			this.buffers.get(this.search(fnum)).gets(records, this);
		}else {
			Frame setty = new Frame();
			setty.gets(records, this);
		}

	};

	//runs set from the buffer
	public void bset(int records, String newRec) {

		double d = Math.floor((records/100) +1);
		int fnum = (int)d;

		if(this.search(fnum) != -1) {
			this.buffers.get(this.search(fnum)).sets(records, newRec, this);
		}else {
			Frame setty = new Frame();
			setty.sets(records, newRec, this);
		}


	};

	//runs pin from the buffer
	public void bpin(int records) {
		int locale = this.search(records);


		if(locale != -1) {
			Frame sorete = this.buffers.get(this.search(records));
			System.out.print("File "+records+" pinned in frame #" + (locale+1) + " pinned\n");
			
			if(this.buffers.get(this.search(records)).getpinned()) {
				System.out.print("Frame pinned before this operation\n");
			}else {
					System.out.print("Frame not pinned before this operation\n");
				}
			sorete.setpinned(true);
			}else {
				Frame sorete = new Frame();
				sorete.pins(records, this);
			}

	};

	public void bunpin(int records) {
		if(this.search(records) != -1) {
			Frame sorete = this.buffers.get(this.search(records));
			sorete.unpins(records, this);
		}else {
			System.out.print("The corresponding block "+ records + " cannot be unpinned because it is not in memory\n");
		}

	};	


	//updates the frame when its ok to put in memory	
	public void update(int id, String cont, boolean pin, boolean dirt) {

		for (Frame f: this.buffers){ 
			if(f.getblockid() == id) {
				f.setblockid(id);
				f.setcontent(cont);
				f.setdirty(dirt);
				f.setpinned(pin);
//				int a = f.getblockid();
//				String b = f.getcontent();
//				boolean c = f.getdirty();
//				boolean d = f.getpinned();
			}
		}
	};


	public String getcont(int id) {
		return this.buffers.get(id).getcontent();
	}

	public boolean getpin(int id) {
		return this.buffers.get(id).getpinned();
	}

	public boolean getdir(int id) {
		return this.buffers.get(id).getdirty();
	}
	
	//sets an eviction checker, if eviction is equal to this number then eviction is reset to 0
	public void setevi(int evi) {
		this.evicheck = evi;
	}
























































}