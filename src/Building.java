import java.util.Queue;

public class Building {
    private final Manager manager;

    public Building(String elev1, String elev2, int floors, Queue<Request> requests, int speed) {
        Elevator elevator1 = new Elevator(elev1,  requests, floors, speed);
        Elevator elevator2 = new Elevator(elev2,  requests, floors, speed);
        this.manager = new Manager(elevator1, elevator2, requests);
    }

    public void startElevators() {
        Thread managerThread = new Thread(manager);
        managerThread.start();
    }

}