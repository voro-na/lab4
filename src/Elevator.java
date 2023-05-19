import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Elevator implements Runnable {
    private final String name;

    private Direction direction;

    private int currentFloor;

    private int callingFloor;
    private int finalFloor;
    private int speed;

    private final int floors;

    private final Queue<Request> requests;

    public Elevator(String name, Queue<Request> requests, int floors, int speed) {
        this.name = name;
        this.direction = Direction.WAIT;
        this.currentFloor = 0;
        this.callingFloor = floors;
        this.finalFloor = floors;
        this.requests = requests;
        this.floors = floors;
        this.speed = speed;

    }

    @Override
    public void run() {
        System.out.println("Лифт #" + name + " движется на  " + callingFloor + " этаж");
        List<Integer> currentRequests = new ArrayList<>();
        List<Integer> currentStops = new ArrayList<>();
        int step = currentFloor < callingFloor ? 1 : -1;

        move(step, currentRequests, currentStops);
        System.out.println("В лифт #" + name + " зашли на " + currentFloor + " этаже");

        callingFloor = this.finalFloor;
        step = direction == Direction.DOWN ? -1 : 1;
        currentRequests.clear();


        move(step, currentRequests, currentStops);
        System.out.println("Лифт #" + name + " закончил движение на " + currentFloor + " этаже");
        this.callingFloor = -1;
        this.direction = Direction.WAIT;

    }

    public void move(int step, List<Integer> concurrentRequests, List<Integer> concurrentStops) {
        try {
            while (currentFloor != callingFloor ) {
                int sizeBefore = concurrentRequests.size();
                List<Integer> newRequests = new ArrayList<>();
                List<Integer> newRequestsFinal = new ArrayList<>();

                if (step == -1) {
                    for (Request request : requests) {
                        if (request.getCallingFloor() <= currentFloor
                                && (request.getDirection() == direction && callingFloor == finalFloor ||
                                (callingFloor == 0 && request.getDirection() == Direction.DOWN))) {
                            if (callingFloor == -1 || callingFloor == floors + 1) {
                                callingFloor = request.getCallingFloor();
                            }
                            callingFloor = Math.min(callingFloor, request.getFinalFloor());
                            newRequests.add(request.getCallingFloor());
                            newRequestsFinal.add(request.getFinalFloor());
                        }
                    }
                } else {
                    for (Request request : requests) {
                        if (request.getCallingFloor() >= currentFloor
                                && (request.getDirection() == direction && callingFloor == finalFloor ||
                                (callingFloor == floors && request.getDirection() == Direction.UP))) {
                            if (callingFloor == -1 || callingFloor == floors + 1) {
                                callingFloor = request.getCallingFloor();
                            }
                            callingFloor = Math.max(callingFloor, request.getFinalFloor());
                            newRequests.add(request.getCallingFloor());
                            newRequestsFinal.add(request.getFinalFloor());
                        }
                    }
                }

                concurrentRequests.addAll(newRequests);
                concurrentStops.addAll(newRequestsFinal);

                for (int i = sizeBefore; i < concurrentRequests.size(); i++) {
                    requests.remove(new Request(concurrentRequests.get(i), newRequestsFinal.get(i), direction));
                }

                while (concurrentRequests.contains(currentFloor)) {
                    System.out.printf("Лифт #%s (на этаже %d) принял пассажира.\n", name, currentFloor);
                    concurrentRequests.remove(Integer.valueOf(currentFloor));
                }
                while (concurrentStops.contains(currentFloor)) {
                    System.out.printf("Лифт #%s (на этаже %d) высадил пассажира.\n", name, currentFloor);
                    concurrentStops.remove(Integer.valueOf(currentFloor));
                }


                System.out.printf("Лифт #%s движется: %s этаж %d\n", name, step == 1 ? "ВВЕРХ" : "ВНИЗ", currentFloor + step);
                currentFloor += step;

                Thread.sleep(speed);
            }
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public int getCurrentFloor() {
        return currentFloor;
    }
    public void setCallingFloor(int callingFloor) {
        this.callingFloor = callingFloor;
    }
    public void setFinalFloor(int floor) {
        this.finalFloor = floor;
    }
}