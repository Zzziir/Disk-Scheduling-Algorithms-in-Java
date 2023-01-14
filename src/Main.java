import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> queue = new ArrayList<Integer>();
        System.out.print("Enter the intial head position: ");
        int initialHeadPosition = sc.nextInt();

        System.out.println("Enter the Request Queue [Separated by a space]: ");
        sc.nextLine();
        String requestQueue = sc.nextLine();
        String[] queueArray = requestQueue.split(" ");

        for(int i = 0; i < queueArray.length; i++){
            queue.add(Integer.parseInt(queueArray[i]));
        }
        //passes the queue, and initialHeadPosition to an instance of DiskScheduling.
        DiskScheduling ds = new DiskScheduling(queue, initialHeadPosition);
    }
}