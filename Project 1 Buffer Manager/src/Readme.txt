Erik Reimert
ereimertburro 115054845



	Section 1:

To compile and execute my code you must open it in Eclipse, set the argument for however amount large you want the buffer pool to be and click the run button.
Once it is running you will be prompted with a command. You must insert a command as detailed by the instructions (all of the commands are in uppercase, not like in the test set provided, be wary of that) i.e: GET 40, PIN 1. 

DO NOT use quotations around the new record in the SET command. Do as shown here: SET 30 F01-Rec040, Name040, address040, age040.

To exit the program you must write the command "EXIT 1".

	Section 2:

All of the test cases are working but the last one. Instead of evicting frame 3 it evicts frame 2. I believe this is probably an error in the printing.

	Section 3:

Beyond what the instruction said, I put my main method in a while loop with an exit boolean so that multiple inputs could be placed during runtime.
I have three different search methods for the buffer pool:
search() just searches for a frame in the pool with matching id to whats inputted to function
fsearch() searches for an empty frame in the pool and populates it with desired frame
psearch() searches for unpinned frames that can be evicted and swapped for a desired frame. Also runs the overwriting of the files in case of eviction
I have an update function that updates the values of the frame to whatever should be its current value in the buffer
I also created a int eviction and int evicheck attribute for the buffer class. These attributes serve as a a counter of on what frame the program should be evicting
and a checker so that the eviction number does not increase beyond the size of the array.
