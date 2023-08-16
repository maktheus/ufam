package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Scheduller {

    public void fcfs(ArrayList<Process> processes) {
        int currentTime = 0;
        int totalExecutionTime = 0;

        for (Process process : processes) {
            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;
            }

            System.out.println(process.name);
            currentTime += process.duration;
            totalExecutionTime += process.duration;
        }

        double averageExecutionTime = (double) totalExecutionTime / processes.size();
        System.out.println("Tempo médio de execução: " + averageExecutionTime);
        System.out.println("Tempo total de execução: " + totalExecutionTime);
    }

    public void shortestJobFirst(ArrayList<Process> processes) {
        Collections.sort(processes, Comparator.comparingInt(p -> p.duration));

        int currentTime = 0;
        int totalExecutionTime = 0;

        for (Process process : processes) {
            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;
            }

            System.out.println(process.name);
            currentTime += process.duration;
            totalExecutionTime += process.duration;
        }

        double averageExecutionTime = (double) totalExecutionTime / processes.size();
        System.out.println("Tempo médio de execução: " + averageExecutionTime);
        System.out.println("Tempo total de execução: " + totalExecutionTime);
    }

    public void shortestRemainingTimeFirst(ArrayList<Process> processes, int quantum) {
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.duration));

        int currentTime = 0;
        int completedProcesses = 0;
        Process runningProcess = null;
        int totalExecutionTime = 0;

        while (completedProcesses < processes.size()) {
            // Adicionar processos chegados à fila de prontos
            for (Process process : processes) {
                if (process.arrivalTime <= currentTime && !process.isCompleted()) {
                    readyQueue.add(process);
                }
            }

            if (runningProcess != null && runningProcess.isCompleted()) {
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
                totalExecutionTime += quantum;
            }

            currentTime++;
        }

        double averageExecutionTime = (double) totalExecutionTime / processes.size();
        System.out.println("Tempo médio de execução: " + averageExecutionTime);
        System.out.println("Tempo total de execução: " + totalExecutionTime);
    }

    public void prioC(ArrayList<Process> processes) {
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.priority));
        int currentTime = 0;
        int totalExecutionTime = 0;
        int numberOfProcesses = processes.size();

        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {
                readyQueue.add(processes.remove(0));
            }

            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                System.out.println(currentProcess.name);
                currentTime += currentProcess.duration;
                totalExecutionTime += currentProcess.duration;
            } else {
                currentTime++;
            }
        }

        double averageExecutionTime = (double) totalExecutionTime / numberOfProcesses;
        System.out.println("Tempo médio de execução: " + averageExecutionTime);
        System.out.println("Tempo total de execução: " + totalExecutionTime);
    }

    public void prioP(ArrayList<Process> processes, int quantum) {
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.priority));
        int currentTime = 0;
        int totalExecutionTime = 0;
        int numberOfProcesses = processes.size();

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
                    totalExecutionTime += quantum;
                    if (!currentProcess.isCompleted()) {
                        readyQueue.add(currentProcess); // Add the process back to the queue
                    }
                } else {
                    currentTime += currentProcess.duration;
                    totalExecutionTime += currentProcess.duration;
                }
            } else {
                currentTime++;
            }
        }

        double averageExecutionTime = (double) totalExecutionTime / numberOfProcesses;
        System.out.println("Tempo médio de execução: " + averageExecutionTime);
        System.out.println("Tempo total de execução: " + totalExecutionTime);
    }

    public void roundRobin(ArrayList<Process> processes, int timeQuantum) {
        ArrayList<Process> queue = new ArrayList<>(processes);
        int currentTime = 0;
        int index = 0;
        int totalExecutionTime = 0;

        while (!queue.isEmpty()) {
            Process currentProcess = queue.get(index);

            if (!currentProcess.isCompleted()) {
                int executionTime = Math.min(timeQuantum, currentProcess.remainingTime());
                System.out.println();
                currentProcess.execute(executionTime);
                currentTime += executionTime;

                System.out.println(currentProcess.name);

                if (currentProcess.isCompleted()) {
                    queue.remove(index);
                    totalExecutionTime += executionTime;
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

        double averageExecutionTime = (double) totalExecutionTime / processes.size();
        System.out.println("Tempo médio de execução: " + averageExecutionTime);
        System.out.println("Tempo total de execução: " + totalExecutionTime);
    }
}
