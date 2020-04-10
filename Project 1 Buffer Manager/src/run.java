import java.util.*; 

public class run {

	public static void main(String[] args) {

		int arg = Integer.parseInt(args[0]);

		//init the buff pool
		BufferPool japiro = new BufferPool();
		japiro.initialize(arg);

		//scan for next command 
		Scanner keyboard = new Scanner(System.in);

		while(true) {
			System.out.println("\nThe program is ready for the next command (input 'EXIT 1' to end program)\n");
			String command = keyboard.next();
			int myint = keyboard.nextInt();
			japiro.setevi(myint-1);  //sets up the eviction checker
			command = command.toUpperCase();
			
			//different commands
			switch(command) {
			case "GET":
				japiro.bget(myint);
				break;
			case "SET":
				String newRec = keyboard.nextLine();
				japiro.bset(myint, newRec);
				break;

			case "PIN":
				japiro.bpin(myint);
				break;
			case "UNPIN":
				japiro.bunpin(myint);
				break;
				
			case "EXIT":
				System.out.println("\nExiting program now...\n");
				keyboard.close();
				System.exit(0);
				break;
				
			default:
				keyboard.close();
			}
		}

	}

}
