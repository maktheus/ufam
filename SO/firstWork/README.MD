# Process Scheduling Algorithms for ELF Operating System

This program simulates process scheduling using various algorithms available in the ELF operating system's kernel. The implemented algorithms include FCFS, RR, SJF, SRTF (preemptive), Prioc, and Priop. The program takes a sequence of processes as input, including PID, arrival time, duration, priority, and type (CPU bound, I/O bound, or both). It then executes the processes based on the selected algorithms and outputs the execution order, as well as the average execution and wait times.

## Implemented Algorithms

The program allows you to choose from the following scheduling algorithms:

1. First Come First Served (FCFS)
2. Round Robin (RR)
3. Shortest Job First (SJF)
4. Shortest Remaining Time First (SRTF - preemptive)
5. Priority Scheduling (Prioc)
6. Priority Scheduling (Priop)

## Usage

1. Compile the source files:
   ```sh
   javac src/*.java
    ```
2. Run the program:
    ```sh
    java src.MainSo <filename>

    ```

##Input File Format
The input file should contain process information in the following format:

```
<process_name> <arrival_time> <duration> <priority> <type>
```

<process_name>: Name of the process (a string).
<arrival_time>: Arrival time of the process (an integer).
<duration>: Duration of the process (an integer).
<priority>: Priority of the process (an integer).
<type>: Type of process (an integer, where 1 indicates CPU-bound, 2 indicates I/O-bound, and 3 indicates both).


###Example:

```
P1 0 10 2 1
P2 3 5 1 1
P3 4 8 3 2
...

```
##Output

The program will display the execution order of processes (by PID) based on the selected algorithm and the process type. Additionally, it will provide the average execution and wait times for the executed processes.


##License

Distributed under the MIT License. See `LICENSE` for more information.