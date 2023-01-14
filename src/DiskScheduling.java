import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class DiskScheduling {

    //fields for seek time, distance, current process, and the initial head position
    static int seekTime, distance, current, initialHeadPosition;

    //storage for processes in queue
    static ArrayList<Integer> requestQueue = new ArrayList<>();

    //storage for SCAN, C-SCAN, LOOK, C-LOOK
    static ArrayList<Integer> LeftSide = new ArrayList<>(), RightSide = new ArrayList<>(), SeekSequence = new ArrayList<>();

    //Constructor of DiskScheduling class
    public DiskScheduling(ArrayList<Integer> requestQueue, int initialHeadPosition) {

        boolean loop = true;
        while(loop) {

            seekTime = 0;
            distance = 0;
            current = 0;
            LeftSide.clear();
            RightSide.clear();
            SeekSequence.clear();
            DiskScheduling.initialHeadPosition = initialHeadPosition;
            DiskScheduling.requestQueue = requestQueue;

            Scanner sc = new Scanner(System.in);

            System.out.println("\n[0] FCFS (First Come, First Serve)" +
                    "\n[1] SSTF (Shortest Seek Time First) " +
                    "\n[2] SCAN " +
                    "\n[3] C-SCAN " +
                    "\n[4] LOOK " +
                    "\n[5] C-LOOK\n");

            System.out.print("What Disk Scheduling Algorithm do you want to simulate?: ");
            int option = sc.nextInt();
            int DiskSize, d_option;
            switch (option) {
                case 0:
                    //FCFS
                    System.out.println("\nFCFS (First Come, First Serve)\n");
                    FCFS();
                    loop = tryAgain();
                    break;

                case 1:
                    //SSTF
                    System.out.println("\nSSTF (Shortest Seek Time First)\n");
                    SSTF();
                    loop = tryAgain();
                    break;

                case 2:
                    //SCAN
                    System.out.println("\nSCAN Algorithm\n");
                    System.out.print("Enter Disk size: ");
                    DiskSize = sc.nextInt();

                    System.out.println("\n[0] Left\n" +
                            "[1] Right");
                    System.out.print("Choose direction: ");
                    d_option = sc.nextInt();
                    if(d_option == 0){
                        SCAN(DiskSize, d_option);
                    }
                    else if(d_option == 1){
                        SCAN(DiskSize ,d_option);
                    }
                    else{
                        System.out.println("Invalid option. Please try again");
                    }
                    loop = tryAgain();
                    break;

                case 3:
                    //C-SCAN
                    System.out.println("\nC-SCAN Algorithm\n");
                    System.out.print("Enter Disk size: ");
                    DiskSize = sc.nextInt();

                    System.out.println("\n[0] Left\n" +
                            "[1] Right");
                    System.out.print("Choose direction: ");
                    d_option = sc.nextInt();
                    if(d_option == 0){
                        C_SCAN(DiskSize, d_option);
                    }
                    else if(d_option == 1){
                        C_SCAN(DiskSize ,d_option);
                    }
                    else{
                        System.out.println("Invalid option. Please try again");
                    }
                    loop = tryAgain();
                    break;

                case 4:
                    //LOOK
                    System.out.println("\nLOOK\n");
                    System.out.println("\n[0] Left\n" +
                            "[1] Right");
                    System.out.print("Choose direction: ");
                    d_option = sc.nextInt();
                    if(d_option == 0){
                        LOOK(d_option);
                    }
                    else if(d_option == 1){
                        LOOK(d_option);
                    }
                    else{
                        System.out.println("Invalid option. Please try again");
                    }
                    loop = tryAgain();
                    break;

                case 5:
                    //C_LOOK
                    System.out.println("\nC-LOOK\n");
                    System.out.println("\n[0] Left\n" +
                            "[1] Right");
                    System.out.print("Choose direction: ");
                    d_option = sc.nextInt();
                    if(d_option == 0){
                        C_LOOK(d_option);
                    }
                    else if(d_option == 1){
                        C_LOOK(d_option);
                    }
                    else{
                        System.out.println("Invalid option. Please try again");
                    }
                    loop = tryAgain();
                    break;

                default:
                    System.out.println("\nInvalid Option. Please choose another. ");
            }

        }
    }

    //========================================== Disk Scheduling Algorithms ==========================================//

    //Method for FCFS Algorithm
    public static void FCFS(){

        System.out.println("Request Queue: " + requestQueue);
        System.out.println("Initial head position: " + initialHeadPosition);
        System.out.println();

        //Iterates each request from the request queue to be accessed one by one.
        for(int i = 0; i < requestQueue.size(); i++){

            /*Updates the current variable as the current request value then subtracts that current to the initial head
            position to get the distance. For each calculated distance, it will be added to the seekTime variable.
            Afterwards, the initial head position will now be set as the current.
             */
            current = requestQueue.get(i);
            distance = Math.abs(current - initialHeadPosition);
            SeekSequence.add(current);
            seekTime += distance;
            initialHeadPosition = current;
        }
        printSeekSequence();
        System.out.println();
        System.out.println("Seek Time: " + seekTime);
    }

    //Method for SSTF Algorithm
    public static void SSTF(){

        ArrayList<Integer> RQ_copy = new ArrayList<>(requestQueue);
        System.out.println("Request Queue: " + RQ_copy);
        System.out.println("Initial head position: " + initialHeadPosition);
        System.out.println();

        //declare a variable for counting the number of processes/request done
        int processesDone = 0;

        //Looping statement will stop after it has completed all processes from the request queue
        while(processesDone != RQ_copy.size()){

            //sets a minimum variable to 1000
            int min = 1000, distance, index = 0;

            /*Determines which process/request has the shortest distance from the current initial head position
              (initial head position is updated after every done process/request). Afterwards, the index of the determined
              value will be targeted as the pointer to be accessed.
            */
            for(int i = 0; i < RQ_copy.size(); i++){
                distance = Math.abs(RQ_copy.get(i) - initialHeadPosition);
                if(min > distance){
                    min = distance;
                    index = i;
                }
            }
            //Every distance will be added to the seek time
            seekTime+=min;
            initialHeadPosition = RQ_copy.get(index);
            SeekSequence.add(RQ_copy.get(index));
            /*The targeted pointer index will now be set as 1000 so that it will not be accessed by the for loop, it will
              just simply denote that that particular process is done.
            */
            RQ_copy.set(index, 1000);
            processesDone++;
        }
        printSeekSequence();
        System.out.println();
        System.out.println("Seek Time: " + seekTime);
    }

    //Method for SCAN Algorithm
    public static void SCAN(int DiskSize, int direction){

        System.out.println("Request Queue: " + requestQueue);
        System.out.println("Initial head position: " + initialHeadPosition);

        /*LeftSide arraylist will be the values below than the initial head position.
        RightSide arraylist will be the values above than the initial head position.

        Initially adds a value (minimum value or max value) to respective arrays depending on the direction entered by the user
        0 = left
        1 = right
         */
        if(direction == 0){
            LeftSide.add(0);
        }

        else if(direction == 1){
            RightSide.add(DiskSize - 1);
        }

        //Determines which processes/request from the request queue is belonged to the left or right of the initial head position
        for(int i = 0; i < requestQueue.size(); i++){
            if(requestQueue.get(i) < initialHeadPosition)
                LeftSide.add(requestQueue.get(i));
            if(requestQueue.get(i) > initialHeadPosition)
                RightSide.add(requestQueue.get(i));
        }

        //Sorts the arraylist from least to greatest
        Collections.sort(LeftSide);
        Collections.sort(RightSide);

        int loop = 0;
        /*Runs the while loop 2 times
        left process + right process = 2, vice versa
        */
        while(loop++ < 2){
            /*Starts with the left side first if the direction entered by the user is left. After it has completed all
              requests from the left side, it will now proceed to the right side requests. Vice versa
            */
            if(direction == 0){
                for(int i = LeftSide.size() - 1; i >= 0; i--){
                    /*Updates the current variable as the current request value then subtracts that current to the initial head
                       position to get the distance. For each calculated distance, it will be added to the seekTime variable.
                       Afterwards, the initial head position will now be set as the current. (Same goes for the right side)
                    */
                    current = LeftSide.get(i);
                    SeekSequence.add(current); //SeekSequence stores the order of requests that was accessed.
                    distance = Math.abs(current - initialHeadPosition);
                    seekTime += distance;
                    initialHeadPosition = current;
                }
                direction = 1;
            }
            else if(direction == 1){
                for(int i = 0; i < RightSide.size(); i++){
                    current = RightSide.get(i);
                    SeekSequence.add(current);
                    distance = Math.abs(current - initialHeadPosition);
                    seekTime += distance;
                    initialHeadPosition = current;
                }
                direction = 0;
            }
        }
        printSeekSequence();
        //Prints the seek time.
        System.out.println();
        System.out.println("Seek Time: " + seekTime);
    }

    //Method for C-SCAN Algorithm
    public static void C_SCAN(int DiskSize, int direction){

        System.out.println("Request Queue: " + requestQueue);
        System.out.println("Initial head position: " + initialHeadPosition);

        /*LeftSide arraylist will be the values below than the initial head position.
        RightSide arraylist will be the values above than the initial head position.

        Initially adds a value (minimum value or max value) to respective arrays depending on the direction entered by the user
        0 = left
        1 = right
        */

        if(direction == 0){
            LeftSide.add(0);
        }
        else if(direction == 1){
            RightSide.add(DiskSize - 1);
        }

        //Determines which processes/request from the request queue is belonged to the left or right of the initial head position
        for(int i = 0; i < requestQueue.size(); i++){
            if(requestQueue.get(i) < initialHeadPosition)
                LeftSide.add(requestQueue.get(i));
            if(requestQueue.get(i) > initialHeadPosition)
                RightSide.add(requestQueue.get(i));
        }

        //Sorts the arraylist from least to greatest
        Collections.sort(LeftSide);
        Collections.sort(RightSide);

        int loop = 0;
        /*Runs the while loop 2 times
        left process + right process = 2, vice versa
        */
        boolean oneSideDone = false;
        while(loop++ < 2){
            /*Starts with the left side first if the direction entered by the user is left. After it has completed all
              requests from the left side, it will now proceed to the right side requests. Vice versa
            */
            if(direction == 0){
                for(int i = LeftSide.size() - 1; i >= 0; i--){
                    /*Updates the current variable as the current request value then subtracts that current to the initial head
                       position to get the distance. For each calculated distance, it will be added to the seekTime variable.
                       Afterwards, the initial head position will now be set as the current. (Same goes for the right side)
                    */
                    current = LeftSide.get(i);
                    SeekSequence.add(current);
                    distance = Math.abs(current - initialHeadPosition);
                    seekTime += distance;
                    initialHeadPosition = current;
                }

                //Runs this statement if there's no side that has been processed yet (Same goes for the right side)
                if(!oneSideDone) {
                    /*C-SCAN moves from one end of the disk to the other. When it reaches the other end, it returns to
                      the beginning of the disk. With that, there will be an added seek time from end to beginning (vice versa)

                      Updates the oneSideDone boolean to true since we have done already 1 side at this point. Also change
                      the direction to right (vice versa).
                    */
                    seekTime += DiskSize - 1;
                    initialHeadPosition = DiskSize - 1;
                    oneSideDone = true;
                    direction = 1;
                    RightSide.sort(Collections.reverseOrder());
                }
            }
            //Right
            else if(direction == 1){
                for(int i = 0; i < RightSide.size(); i++){
                    current = RightSide.get(i);
                    SeekSequence.add(current);
                    distance = Math.abs(current - initialHeadPosition);
                    seekTime += distance;
                    initialHeadPosition = current;
                }
                if(!oneSideDone) {
                    seekTime += DiskSize - 1;
                    initialHeadPosition = 0;
                    oneSideDone = true;
                    direction = 0;
                    LeftSide.sort(Collections.reverseOrder());
                }
            }
        }
        printSeekSequence();
        //Prints the seek time
        System.out.println();
        System.out.println("Seek Time: " + seekTime);
    }

    //Method for LOOK Algorithm
    public static void LOOK(int direction){
        System.out.println("Request Queue: " + requestQueue);
        System.out.println("Initial head position: " + initialHeadPosition);

        //Determines which processes/request from the request queue is belonged to the left or right of the initial head position
        for(int i = 0; i < requestQueue.size(); i++){
            if(requestQueue.get(i) < initialHeadPosition)
                LeftSide.add(requestQueue.get(i));
            if(requestQueue.get(i) > initialHeadPosition)
                RightSide.add(requestQueue.get(i));
        }

        //Sorts the arraylist from least to greatest
        Collections.sort(LeftSide);
        Collections.sort(RightSide);

        int loop = 0;
        /*Runs the while loop 2 times
          left process + right process = 2, vice versa
        */
        while(loop++ < 2){
            /* Starts with the left side first if the direction entered by the user is left. After it has completed all
               requests from the left side, it will now proceed to the right side requests. Vice versa
            */
            if(direction == 0){
                for(int i = LeftSide.size() - 1; i >= 0; i--){
                    /* Updates the current variable as the current request value then subtracts that current to the initial head
                       position to get the distance. For each calculated distance, it will be added to the seekTime variable.
                       Afterwards, the initial head position will now be set as the current. (Same goes for the right side)
                    */
                    current = LeftSide.get(i);
                    SeekSequence.add(current); //SeekSequence stores the order of requests that was accessed.
                    distance = Math.abs(current - initialHeadPosition);
                    seekTime += distance;
                    initialHeadPosition = current;
                }
                direction = 1;
            }
            else if(direction == 1){
                for(int i = 0; i < RightSide.size(); i++){
                    current = RightSide.get(i);
                    SeekSequence.add(current);
                    distance = Math.abs(current - initialHeadPosition);
                    seekTime += distance;
                    initialHeadPosition = current;
                }
                direction = 0;
            }
        }
        printSeekSequence();
        //Displays the seek time
        System.out.println();
        System.out.println("Seek Time: " + seekTime);
    }

    //Method for C-LOOK Algorithm
    public static void C_LOOK(int direction){

        System.out.println("Request Queue: " + requestQueue);
        System.out.println("Initial head position: " + initialHeadPosition);

        //Determines which processes/request from the request queue is belonged to the left or right of the initial head position
        for(int i = 0; i < requestQueue.size(); i++){
            if(requestQueue.get(i) < initialHeadPosition)
                LeftSide.add(requestQueue.get(i));
            if(requestQueue.get(i) > initialHeadPosition)
                RightSide.add(requestQueue.get(i));
        }

        //Sorts the arraylist from least to greatest
        Collections.sort(LeftSide);
        Collections.sort(RightSide);

        int loop = 0;
        /* Runs the while loop 2 times
           left process + right process = 2, vice versa
        */
        boolean oneSideDone = false;
        while(loop++ < 2){
            /*Starts with the left side first if the direction entered by the user is left. After it has completed all
              requests from the left side, it will now proceed to the right side requests. Vice versa
            */
            if(direction == 0){
                for(int i = LeftSide.size() - 1; i >= 0; i--){
                    /*Updates the current variable as the current request value then subtracts that current to the initial head
                       position to get the distance. For each calculated distance, it will be added to the seekTime variable.
                       Afterwards, the initial head position will now be set as the current. (Same goes for the right side)
                    */
                    current = LeftSide.get(i);
                    SeekSequence.add(current);
                    distance = Math.abs(current - initialHeadPosition);
                    seekTime += distance;
                    initialHeadPosition = current;
                }
                //Runs this statement if there's no side that has been processed yet. (Same goes for the right side)
                if(!oneSideDone) {
                    /*C-LOOK moves from the minimum to the maximum of the request values (vice versa). When it reaches
                      the maximum value, it goes back to the minimum value (vice versa).

                      Updates the oneSideDone boolean to true since we have done already 1 side at this point. Also change
                      the direction to right (vice versa).
                    */
                    seekTime += Math.abs(initialHeadPosition - Collections.max(RightSide));
                    initialHeadPosition = Collections.max(RightSide);
                    oneSideDone = true;
                    direction = 1;
                    RightSide.sort(Collections.reverseOrder());
                }
            }
            //Right
            else if(direction == 1){
                for(int i = 0; i < RightSide.size(); i++){
                    current = RightSide.get(i);
                    SeekSequence.add(current);
                    distance = Math.abs(current - initialHeadPosition);
                    seekTime += distance;
                    initialHeadPosition = current;
                }
                if(!oneSideDone) {
                    seekTime += Math.abs(initialHeadPosition - LeftSide.get(0));
                    initialHeadPosition = LeftSide.get(0);
                    oneSideDone = true;
                    direction = 0;
                    LeftSide.sort(Collections.reverseOrder());
                }
            }
        }
        //displays the seek time
        printSeekSequence();
        System.out.println();
        System.out.println("Seek Time: " + seekTime);
    }

    //Method to print the seek sequence
    public static void printSeekSequence(){
        System.out.println("Seek Sequence: ");
        for(int i = 0; i < SeekSequence.size(); i++){
            System.out.print(SeekSequence.get(i) + " ");
        }
    }

    //Method for choosing another algorithm to simulate
    public static boolean tryAgain(){
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.print("Do you want to try another algorithm? [Y] / [N]: ");
        String tryAgainOption = sc.next();
        if(tryAgainOption.equals("N") || tryAgainOption.equals("n")) {
            return false;
        }
        return true;
    }
}
