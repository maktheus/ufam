package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Scheduller {
    public void fcfs(ArrayList<Process> processes) {
        int currentTime = 0;

        for (Process process : processes) {
            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;
            }

            System.out.println(process.name);
            currentTime += process.duration;

            // System.out.println("Process " + process.name + " completed at time: " +
            // currentTime);
        }
    }

    public void shortestJobFirst(ArrayList<Process> processes) {
        Collections.sort(processes, (p1, p2) -> p1.duration - p2.duration);

        int currentTime = 0;

        for (Process process : processes) {
            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;
            }

            System.out.println(process.name);
            currentTime += process.duration;
            // System.out.println("Process " + process.name + " completed at time: " +
            // currentTime);
        }
    }

    public void shortestRemainingTimeFirst(ArrayList<Process> processes, int quantum) {
        PriorityQueue<Process> readyQueue = new PriorityQueue<>((p1, p2) -> p1.duration - p2.duration);

        int currentTime = 0;
        int completedProcesses = 0;
        Process runningProcess = null;

        while (completedProcesses < processes.size()) {
            // Adicionar processos chegados à fila de prontos
            for (Process process : processes) {
                if (process.arrivalTime <= currentTime && !process.isCompleted()) {
                    readyQueue.add(process);
                }
            }

            if (runningProcess != null && runningProcess.isCompleted()) {
                // System.out.println("Process " + runningProcess.name + " completed at time: "
                // + currentTime);
                completedProcesses++;
                runningProcess = null;
            }

            if (!readyQueue.isEmpty()) {
                Process nextProcess = readyQueue.poll();
                if (runningProcess == null || nextProcess.duration < runningProcess.remainingTime()) {
                    if (runningProcess != null) {
                        readyQueue.add(runningProcess); // Adiciona o processo em execução de volta à fila
                    }
                    runningProcess = nextProcess;
                }
            }

            if (runningProcess != null) {
                System.out.println(runningProcess.name);
                runningProcess.execute(quantum);
            }

            currentTime++;
        }
    }

    public void prioC(ArrayList<Process> processes) {
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.priority));
        int currentTime = 0;

        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {
                readyQueue.add(processes.remove(0));
            }

            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                System.out.println(currentProcess.name);
                currentTime += currentProcess.duration;
                // System.out.println("Process " + currentProcess.name + " completed at time: "
                // + currentTime);
            } else {
                currentTime++;
            }
        }
    }

    public void prioP(ArrayList<Process> processes, int quantum) {
        PriorityQueue<Process> readyQueue = new PriorityQueue<>((p1, p2) -> p1.priority - p2.priority);
        int currentTime = 0;

        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {
                readyQueue.add(processes.remove(0));
            }

            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                System.out.println(currentProcess.name);

                if (currentProcess.duration > quantum) {
                    currentProcess.execute(quantum); // Execute the process for quantum time
                    currentTime += quantum;
                    currentProcess.duration -= quantum;
                    if (!currentProcess.isCompleted()) {
                        readyQueue.add(currentProcess); // Add the process back to the queue
                    }
                } else {
                    currentTime += currentProcess.duration;
                    // System.out.println("Process " + currentProcess.name + " completed at time: "
                    // + currentTime);
                }
            } else {
                currentTime++;
            }
        }
    }

    public void roundRobin(ArrayList<Process> processes, int timeQuantum) {
        ArrayList<Process> queue = new ArrayList<>(processes);
        int currentTime = 0;
        int index = 0;

        while (!queue.isEmpty()) {
            Process currentProcess = queue.get(index);

            if (!currentProcess.isCompleted()) {
                int executionTime = Math.min(timeQuantum, currentProcess.remainingTime());
                System.out.println();
                currentProcess.execute(executionTime);
                currentTime += executionTime;

                System.out.println(currentProcess.name );

                if (currentProcess.isCompleted()) {
                    queue.remove(index);
                } else {
                    index = (index + 1) % queue.size();
                }
            } else {
                queue.remove(index);
            }

            if (queue.size() > 0) {
                index = (index + 1) % queue.size();
            }
        }
    }

}
