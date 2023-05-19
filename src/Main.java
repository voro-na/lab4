import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        System.out.println("Введите кол-во этажей в доме");
        int floors = new Scanner(System.in).nextInt();
        System.out.println("Введите частоту генерации вызовов лифта(в миллисекундах). Рекомендуемая 5000");
        int requestSpeed = new Scanner(System.in).nextInt();
        System.out.println("Введите скорость движения лифта(в миллисекундах). Рекомендуемая 1000");
        int elevatorSpeed = new Scanner(System.in).nextInt();
        Queue<Request> requests = new ArrayDeque<>();
        Building building = new Building("1", "2", floors, requests, elevatorSpeed);

        building.startElevators();

        RequestCreator generator = new RequestCreator(requests,  floors, requestSpeed);
        Thread generatorThread = new Thread(generator);
        generatorThread.start();
    }
}