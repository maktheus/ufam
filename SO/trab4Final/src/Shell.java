import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Shell {
    // Diretório atual de trabalho
    private static String currentWorkingDirectory = System.getProperty("user.dir");
    
    // Lista de processos executados em segundo plano
    private static List<Process> backgroundProcessList = new ArrayList<>();
    
    // Flag para controlar o loop de execução do shell
    private static boolean shellIsActive = true;
    
    // Scanner para captura de input do usuário
    private static Scanner userInputScanner = new Scanner(System.in);
    
    // Nome do arquivo de log
    private static  String logFileName = "shell_log_" + System.currentTimeMillis() + ".txt";

    public static void run() {
        
        try {
            // Inicializando os escritores de arquivo de log
            FileWriter logFileWriter = new FileWriter(logFileName);
            PrintWriter logFilePrintWriter = new PrintWriter(logFileWriter);

            while (shellIsActive) {
                System.out.print(currentWorkingDirectory + "> ");
                String userInput = userInputScanner.nextLine();
                logFilePrintWriter.println(userInput);

                String[] commandTokens = userInput.split("\\s+");
                String command = commandTokens[0];

                switch (command) {
                    case "copy":
                        Copy.execute(commandTokens, currentWorkingDirectory);
                        break;
                    case "rename":
                        Rename.execute(commandTokens, currentWorkingDirectory);
                        break;
                    case "remove":
                        Remove.execute(commandTokens, currentWorkingDirectory);
                        break;
                    case "cd":
                        currentWorkingDirectory = Cd.execute(commandTokens, currentWorkingDirectory);
                        break;
                    case "pwd":
                        Pwd.execute(currentWorkingDirectory);
                        break;
                    case "ls":
                        Ls.execute(currentWorkingDirectory);
                        break;
                    case "cat":
                        Cat.execute(commandTokens);
                        break;
                    case "mkdir":
                        Mkdir.execute(commandTokens, currentWorkingDirectory);
                        break;
                    case "find":
                        Find.execute(commandTokens, currentWorkingDirectory);
                        break;
                    case "locate":
                        Locate.execute(commandTokens, currentWorkingDirectory);
                        break;
                    case "grep":
                        Grep.execute(commandTokens);
                        break;
                    case "exit":
                        shellIsActive = false;
                        break;
                    default:
                        if (userInput.contains("|")) {
                            executePipedCommand(userInput);
                        } else {
                            executeGeneralCommand(commandTokens, logFilePrintWriter);
                        }
                        break;
                }
            }

            logFilePrintWriter.close();
            logFileWriter.close();
        } catch (IOException e) {
            System.err.println("Failed to create log file: " + e.getMessage());
        }
        userInputScanner.close();
    }

    // Função para executar comandos encadeados com pipe (|)
    private static void executePipedCommand(String userInput) {
        String[] pipedCommands = userInput.split("\\|");
        
        if (pipedCommands.length != 2) {
            System.err.println("Invalid pipe command.");
            return;
        }

        String[] secondCommandTokens = pipedCommands[1].trim().split("\\s+");

        try {
            ProcessBuilder secondProcessBuilder = new ProcessBuilder(secondCommandTokens);
            secondProcessBuilder.redirectInput();
            Process secondProcess = secondProcessBuilder.start();
            secondProcess.waitFor();
        } catch (Exception e) {
            System.err.println("Failed to execute the pipe command: " + e.getMessage());
        }
    }

    // Função para executar comandos gerais
    private static void executeGeneralCommand(String[] commandTokens, PrintWriter logFilePrintWriter) {
        boolean runInBackground = false;
        int ampersandPosition = commandTokens.length - 1;

        if ("&".equals(commandTokens[ampersandPosition])) {
            runInBackground = true;
            commandTokens = Arrays.copyOf(commandTokens, ampersandPosition);
        }

        try {
            String resolvedCommand = resolveCommandPath(commandTokens[0]);
            commandTokens[0] = resolvedCommand;

            ProcessBuilder commandProcessBuilder = new ProcessBuilder(commandTokens);
            commandProcessBuilder.redirectErrorStream(true);

            if (isInputRedirected(commandTokens)) {
                String inputFilePath = getInputRedirectFilePath(commandTokens);
                File inputFile = new File(inputFilePath);
                commandProcessBuilder.redirectInput(inputFile);
            }

            if (isOutputRedirected(commandTokens)) {
                String outputFilePath = getOutputRedirectFilePath(commandTokens);
                File outputFile = new File(outputFilePath);
                commandProcessBuilder.redirectOutput(outputFile);
            }

            Process commandProcess = commandProcessBuilder.start();

            if (runInBackground) {
                backgroundProcessList.add(commandProcess);
                final String[] finalCommandTokens = commandTokens;

                new Thread(() -> {
                    try {
                        commandProcess.waitFor();
                        System.out.println("Process " + Arrays.toString(finalCommandTokens) + " completed!");
                        backgroundProcessList.remove(commandProcess);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            } else {
                commandProcess.waitFor();
            }
        } catch (Exception e) {
            System.err.println("Failed to execute the command: " + Arrays.toString(commandTokens));
        }

        logFilePrintWriter.println("Command executed: " + Arrays.toString(commandTokens));
    }

    // Função para resolver o caminho do comando
    private static String resolveCommandPath(String command) {
        if (command.startsWith("~/")) {
            return System.getProperty("user.home") + command.substring(1);
        } else if (command.startsWith("./")) {
            return currentWorkingDirectory + command.substring(1);
        } else {
            return command;
        }
    }

    // Verifica se o comando tem input redirecionado
    private static boolean isInputRedirected(String[] commandTokens) {
        for (String token : commandTokens) {
            if ("<".equals(token)) {
                return true;
            }
        }
        return false;
    }

    // Obtém o caminho do arquivo para redirecionamento de entrada
    private static String getInputRedirectFilePath(String[] commandTokens) {
        for (int i = 0; i < commandTokens.length; i++) {
            if ("<".equals(commandTokens[i]) && i + 1 < commandTokens.length) {
                return commandTokens[i + 1];
            }
        }
        return null;
    }

    // Verifica se o comando tem output redirecionado
    private static boolean isOutputRedirected(String[] commandTokens) {
        for (String token : commandTokens) {
            if (">".equals(token)) {
                return true;
            }
        }
        return false;
    }

    // Obtém o caminho do arquivo para redirecionamento de saída
    private static String getOutputRedirectFilePath(String[] commandTokens) {
        for (int i = 0; i < commandTokens.length; i++) {
            if (">".equals(commandTokens[i]) && i + 1 < commandTokens.length) {
                return commandTokens[i + 1];
            }
        }
        return null;
    }
}
