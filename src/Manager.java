import java.util.Queue;

public class Manager implements Runnable {
    private final Elevator elevator1;
    private final Elevator elevator2;
    private final Queue<Request> requests;

    public Manager(Elevator elevator1, Elevator elevator2, Queue<Request> requests) {
        this.elevator1 = elevator1;
        this.elevator2 = elevator2;
        this.requests = requests;
    }

    private boolean checkElevators() {
        return elevator1.getDirection() == Direction.WAIT || elevator2.getDirection() == Direction.WAIT;
    }

    @Override
    public void run() {

        Thread elevatorThread1 = new Thread(elevator1);
        Thread elevatorThread2 = new Thread(elevator2);


        while (true) {
            boolean available = checkElevators();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (!requests.isEmpty() && available) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Request request = requests.poll();
                if (request != null) {

                    if (elevator1.getDirection() == Direction.WAIT &&
                            Math.abs(elevator1.getCurrentFloor() - request.getCallingFloor()) <=
                                    Math.abs(elevator2.getCurrentFloor() - request.getCallingFloor())
                            || elevator2.getDirection() != Direction.WAIT) {

                        elevator1.setCallingFloor(request.getCallingFloor());
                        elevator1.setFinalFloor(request.getFinalFloor());
                        elevator1.setDirection(request.getDirection());

                        elevatorThread1 = new Thread(elevator1);
                        elevatorThread1.start();
                    } else {
                        elevator2.setCallingFloor(request.getCallingFloor());
                        elevator2.setFinalFloor(request.getFinalFloor());
                        elevator2.setDirection(request.getDirection());
                        elevatorThread2 = new Thread(elevator2);
                        elevatorThread2.start();
                    }
                }
            }
        }
    }
}