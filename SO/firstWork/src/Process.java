package src;

class Process {
    String name;
    int arrivalTime;
    int duration;
    int priority;
    int type;
    int remainingTime;

    public Process(String name, int arrivalTime, int duration, int priority, int type) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.priority = priority;
        this.type = type;
        this.remainingTime = duration;
    }

    public boolean isCompleted() {
        return remainingTime == 0;
    }

    public int remainingTime() {
        return remainingTime;
    }

    public void execute(int time) {
        if (remainingTime >= time) {
            remainingTime -= time;
        } else {
            remainingTime = 0;
        }
    }
}