package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Scheduller {

    private void printExecutionStats(int totalExecutionTime, int totalWaitTime, int numberOfProcesses) {
        double averageExecutionTime = (double) totalExecutionTime / numberOfProcesses;
        double averageWaitTime = (double) totalWaitTime / numberOfProcesses;

        System.out.println("Tempo médio de execução: " + averageExecutionTime);
        System.out.println("Tempo médio de espera: " + averageWaitTime);
        System.out.println("Tempo total de execução: " + totalExecutionTime);
    }

    public void fcfs(ArrayList<Process> processes) {
        int currentTime = 0;
        int totalExecutionTime = 0;
        int totalWaitTime = 0;
        int numberOfProcesses = processes.size();

        for (Process process : processes) {
            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;
            }

            System.out.println(process.name);
            totalWaitTime += currentTime - process.arrivalTime;
            currentTime += process.duration;
            totalExecutionTime += process.duration;
        }

        printExecutionStats(totalExecutionTime, totalWaitTime, numberOfProcesses);

    }

    // public void fcfs(ArrayList<Process> processes) {
    // int currentTime = 0;
    // int totalExecutionTime = 0;

    // for (Process process : processes) {
    // if (currentTime < process.arrivalTime) {
    // currentTime = process.arrivalTime;
    // }

    // System.out.println(process.name);
    // currentTime += process.duration;
    // totalExecutionTime += process.duration;
    // }

    // double averageExecutionTime = (double) totalExecutionTime / processes.size();
    // System.out.println("Tempo médio de execução: " + averageExecutionTime);
    // System.out.println("Tempo total de execução: " + totalExecutionTime);
    // }

    public void shortestJobFirst(ArrayList<Process> processes) {
        Collections.sort(processes, Comparator.comparingInt(p -> p.duration));

        int currentTime = 0;
        int totalExecutionTime = 0;
        int totalWaitTime = 0;
        int numberOfProcesses = processes.size();

        for (Process process : processes) {
            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;
            }

            System.out.println(process.name);
            totalWaitTime += currentTime - process.arrivalTime; // Calcula o tempo de espera
            currentTime += process.duration;
            totalExecutionTime += process.duration;
        }

        printExecutionStats(totalExecutionTime, totalWaitTime, numberOfProcesses);
    }

    public void shortestRemainingTimeFirst(ArrayList<Process> processes) {
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.duration));

        int currentTime = 0;
        int completedProcesses = 0;
        Process runningProcess = null;
        int totalExecutionTime = 0;
        int totalWaitTime = 0;
        int numberOfProcesses = processes.size();

        while (completedProcesses < numberOfProcesses) {
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
                runningProcess.execute(1);
                currentTime++;
                totalExecutionTime++;
            } else {
                currentTime++;
            }

            // Não precisa incrementar currentTime novamente aqui
        }

        printExecutionStats(totalExecutionTime, totalWaitTime, numberOfProcesses);
    }

    public void prioC(ArrayList<Process> processes) {
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.priority));
        int currentTime = 0;
        int totalExecutionTime = 0;
        int totalWaitTime = 0;
        int numberOfProcesses = processes.size();
    
        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {
                readyQueue.add(processes.remove(0));
            }
    
            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                System.out.println(currentProcess.name);
                totalWaitTime += currentTime - currentProcess.arrivalTime; // Calcula o tempo de espera
                currentTime += currentProcess.duration;
                totalExecutionTime += currentProcess.duration;
            } else {
                currentTime++;
            }
        }
    
        printExecutionStats(totalExecutionTime, totalWaitTime, numberOfProcesses);
    }
    

    public void prioP(ArrayList<Process> processes) {
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.priority));
        int currentTime = 0;
        int totalExecutionTime = 0;
        int totalWaitTime = 0;
        int numberOfProcesses = processes.size();
    
        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {
                readyQueue.add(processes.remove(0));
            }
    
            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                System.out.println(currentProcess.name);
    
                if (currentProcess.duration > 1) {
                    currentProcess.execute(1);
                    currentTime++;
                    totalExecutionTime++;
                    readyQueue.add(currentProcess); // Adiciona o processo de volta à fila
                } else {
                    currentTime++;
                    totalExecutionTime += currentProcess.duration;
                    totalWaitTime += currentTime - currentProcess.arrivalTime; // Calcula o tempo de espera
                }
            } else {
                currentTime++;
            }
        }
    
        printExecutionStats(totalExecutionTime, totalWaitTime, numberOfProcesses);
    }
    

    public void roundRobin(ArrayList<Process> processes, int timeQuantum) {
        ArrayList<Process> queue = new ArrayList<>(processes);
        int currentTime = 0;
        int index = 0;
        int totalExecutionTime = 0;
        int totalWaitTime = 0;
        int numberOfProcesses = processes.size();
    
        while (!queue.isEmpty()) {
            Process currentProcess = queue.get(index);
    
            if (!currentProcess.isCompleted()) {
                int executionTime = Math.min(timeQuantum, currentProcess.remainingTime());
    
                currentProcess.execute(executionTime);
                currentTime += executionTime;
                totalExecutionTime += executionTime;
                totalWaitTime += currentTime - currentProcess.arrivalTime; // Calcula o tempo de espera
    
                System.out.println(currentProcess.name);
    
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
    
        printExecutionStats(totalExecutionTime, totalWaitTime, numberOfProcesses);
    }
    
}
