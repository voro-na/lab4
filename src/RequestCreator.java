import java.util.Queue;
import java.util.Random;

public class RequestCreator implements Runnable {
    private final Queue<Request> requests;
    private final int maxFloor;
    private final long ms;

    public RequestCreator(Queue<Request> requests, int maxFloor, long intervalInMs) {
        this.requests = requests;
        this.maxFloor = maxFloor;
        this.ms = intervalInMs;
    }

    @Override
    public void run() {
        Random random = new Random();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        while (true) {
            int randomStartFloor = random.nextInt(0, maxFloor);
            int randomStopFloor = random.nextInt(0, maxFloor);
//            Scanner in = new Scanner(System.in);
//            int randomStartFloor = Integer.parseInt(in.nextLine());
//            int randomStopFloor = Integer.parseInt(in.nextLine());
            while (randomStopFloor == randomStartFloor) {
                randomStopFloor = random.nextInt(0, maxFloor);
            }
            Direction direction = randomStopFloor - randomStartFloor > 0 ? Direction.UP : Direction.DOWN;

            Request request = new Request(randomStartFloor, randomStopFloor, direction);
            requests.add(request);

            System.out.println("Вызов лифта с " + request.getCallingFloor() + " этажа, на " + request.getFinalFloor());
            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}