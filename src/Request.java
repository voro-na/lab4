public class Request {
    private final int callingFloor;
    private final Direction direction;
    private final int finalFloor;

    public Request(int callingFloor, int finalFloor, Direction direction) {
        this.callingFloor = callingFloor;
        this.direction = direction;
        this.finalFloor = finalFloor;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Request request = (Request) obj;
        return callingFloor == request.callingFloor
                && direction == request.direction
                && request.finalFloor == finalFloor;
    }
    public int getCallingFloor() {
        return callingFloor;
    }
    public int getFinalFloor() {
        return finalFloor;
    }
    public Direction getDirection() {
        return direction;
    }

}