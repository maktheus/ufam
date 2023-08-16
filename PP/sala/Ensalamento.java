import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Ensalamento {
    ArrayList<Sala> salas = new ArrayList<Sala>();
    ArrayList<Turma> turmas = new ArrayList<Turma>();
    ArrayList<TurmaEmSala> ensalamento = new ArrayList<TurmaEmSala>();

    // turmas antes de Ordenar
    ArrayList<Turma> turmasAntesOrdenar = new ArrayList<Turma>();
    // salas antes de Ordenar
    ArrayList<Sala> salasAntesOrdenar = new ArrayList<Sala>();

    private <T> List<List<T>> generateCombinations(List<T> elements, int size) {
        List<List<T>> combinations = new ArrayList<>();
        generateCombinationsHelper(elements, size, 0, new ArrayList<>(), combinations);
        return combinations;
    }

    private <T> void generateCombinationsHelper(List<T> elements, int size, int startIndex, List<T> currentCombination,
            List<List<T>> combinations) {
        if (currentCombination.size() == size) {
            combinations.add(new ArrayList<>(currentCombination));
            return;
        }

        for (int i = startIndex; i < elements.size(); i++) {
            currentCombination.add(elements.get(i));
            generateCombinationsHelper(elements, size, i + 1, currentCombination, combinations);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }

    public Ensalamento() {
        this.salas = new ArrayList<Sala>();
        this.turmas = new ArrayList<Turma>();
        this.ensalamento = new ArrayList<TurmaEmSala>();
        // print ensalamento e salas before ordenção

    }

    public void addSala(Sala sala) {
        this.salas.add(sala);
        this.salasAntesOrdenar.add(sala);
    }

    public void addTurma(Turma turma) {
        this.turmas.add(turma);
        this.turmasAntesOrdenar.add(turma);
    }

    public Sala getSala(Turma turma) {
        for (TurmaEmSala turmaEmSala : this.ensalamento) {
            if (turmaEmSala.turma == turma) {
                return turmaEmSala.sala;
            }
        }
        return null;
    }

    public boolean salaDisponivel(Sala sala, int horario) {
        if (sala.acessivel == false) {
            return false;
        }

        for (TurmaEmSala turmaEmSala : this.ensalamento) {
            if (turmaEmSala.sala == sala && turmaEmSala.turma.horarios.contains(horario)) {
                return false;
            }
        }
        return true;
    }

    public boolean salaDisponivel(Sala sala, ArrayList<Integer> horarios) {
        for (int horario : horarios) {
            if (!salaDisponivel(sala, horario)) {
                return false;
            }
        }
        return true;
    }

    public boolean alocar(Turma turma, Sala sala) {
        if (sala.capacidade <= turma.numAlunos) {
            return false;
        }

        if (!sala.acessivel) {
            return false;
        }

        if (salaDisponivel(sala, turma.horarios)) {
            this.ensalamento.add(new TurmaEmSala(turma, sala));
            return true;
        }

        return false;
    }

    public void ordenarTurmasPorNumeroAlunos() {
        Collections.sort(this.turmas, new Comparator<Turma>() {
            @Override
            public int compare(Turma t1, Turma t2) {
                return t1.numAlunos - t2.numAlunos;
            }
        });
    }

    public void ordenarTurmasPorNumeroAlunosDecrescente() {
        Collections.sort(turmas, new Comparator<Turma>() {
            @Override
            public int compare(Turma t1, Turma t2) {
                return Integer.compare(t2.numAlunos, t1.numAlunos);
            }
        });
    }

    public void ordenarSalasPorCapacidade() {
        Collections.sort(salas, new Comparator<Sala>() {
            @Override
            public int compare(Sala s1, Sala s2) {
                return Integer.compare(s1.capacidade, s2.capacidade);
            }
        });
    }

    public void ordenarSalasPorCapacidadeDecrescente() {
        Collections.sort(this.salas, new Comparator<Sala>() {
            @Override
            public int compare(Sala s1, Sala s2) {
                return s2.capacidade - s1.capacidade;
            }
        });
    }

    public int getMinimunDiffPointerBetweenSalaAndTurma(Turma turma) {
        int minDiff = Integer.MAX_VALUE;
        int minDiffPointer = -1;
        for (int i = 0; i < this.salas.size(); i++) {
            Sala sala = this.salas.get(i);
            int diff = sala.capacidade - turma.numAlunos;
            if (diff >= 0 && diff < minDiff && sala.acessivel == true) {
                minDiff = diff;
                minDiffPointer = i;
            }
        }
        return minDiffPointer;
    }

    public boolean refinedFirstFit(Turma turma) {
        for (Sala sala : salas) {
            if (sala.acessivel && sala.capacidade >= turma.numAlunos && salaDisponivel(sala, turma.horarios)) {
                alocar(turma, sala);
                return true;
            }
        }
        return false;
    }

    public boolean worstFit(Turma turma) {
        Sala selectedSala = null;
        int maxDiff = -1;

        for (Sala sala : salas) {
            if (sala.acessivel && sala.capacidade >= turma.numAlunos && salaDisponivel(sala, turma.horarios)) {
                int diff = sala.capacidade - turma.numAlunos;
                if (diff > maxDiff) {
                    maxDiff = diff;
                    selectedSala = sala;
                }
            }
        }

        if (selectedSala != null) {
            alocar(turma, selectedSala);
            return true;
        }

        return false;
    }

    public boolean firstFit(Turma turma) {
        for (Sala sala : salas) {
            if (sala.acessivel && sala.capacidade >= turma.numAlunos && salaDisponivel(sala, turma.horarios)) {
                alocar(turma, sala);
                return true;
            }
        }
        return false;
    }

    public boolean bestFit(Turma turma) {
        Sala selectedSala = null;
        int minDiff = Integer.MAX_VALUE;

        for (Sala sala : salas) {
            if (sala.acessivel && sala.capacidade >= turma.numAlunos && salaDisponivel(sala, turma.horarios)) {
                int diff = sala.capacidade - turma.numAlunos;
                if (diff < minDiff) {
                    minDiff = diff;
                    selectedSala = sala;
                }
            }
        }

        if (selectedSala != null) {
            alocar(turma, selectedSala);
            return true;
        }

        return false;
    }

    public boolean firstFitMinTimeSlots(Turma turma) {
        Sala selectedSala = null;
        int minTimeSlots = Integer.MAX_VALUE;

        for (Sala sala : salas) {
            if (sala.acessivel && sala.capacidade >= turma.numAlunos && salaDisponivel(sala, turma.horarios)) {
                int availableTimeSlots = countAvailableTimeSlots(sala, turma.horarios);
                if (availableTimeSlots < minTimeSlots) {
                    minTimeSlots = availableTimeSlots;
                    selectedSala = sala;
                }
            }
        }

        if (selectedSala != null) {
            alocar(turma, selectedSala);
            return true;
        }

        return false;
    }

    public boolean firstFitMaxTimeSlots(Turma turma) {
        Sala selectedSala = null;
        int maxTimeSlots = -1;

        for (Sala sala : salas) {
            if (sala.acessivel && sala.capacidade >= turma.numAlunos && salaDisponivel(sala, turma.horarios)) {
                int availableTimeSlots = countAvailableTimeSlots(sala, turma.horarios);
                if (availableTimeSlots > maxTimeSlots) {
                    maxTimeSlots = availableTimeSlots;
                    selectedSala = sala;
                }
            }
        }

        if (selectedSala != null) {
            alocar(turma, selectedSala);
            return true;
        }

        return false;
    }

    public void ordenarTurmasPorNumeroAlunosDecrescente(Turma turma) {
        Collections.sort(turmas, new Comparator<Turma>() {
            @Override
            public int compare(Turma t1, Turma t2) {
                return Integer.compare(t2.numAlunos, t1.numAlunos);
            }
        });
    }

    public boolean bestFitMinTimeSlots(Turma turma) {
        Sala selectedSala = null;
        int minDiff = Integer.MAX_VALUE;
        int minTimeSlots = Integer.MAX_VALUE;

        for (Sala sala : salas) {
            if (sala.acessivel && sala.capacidade >= turma.numAlunos && salaDisponivel(sala, turma.horarios)) {
                int diff = sala.capacidade - turma.numAlunos;
                int availableTimeSlots = countAvailableTimeSlots(sala, turma.horarios);
                if (diff < minDiff || (diff == minDiff && availableTimeSlots < minTimeSlots)) {
                    minDiff = diff;
                    minTimeSlots = availableTimeSlots;
                    selectedSala = sala;
                }
            }
        }

        if (selectedSala != null) {
            alocar(turma, selectedSala);
            return true;
        }

        return false;
    }

    private int countAvailableTimeSlots(Sala sala, ArrayList<Integer> horarios) {
        int availableTimeSlots = 0;

        for (Integer horario : horarios) {
            if (salaDisponivel(sala, horario)) {
                availableTimeSlots++;
            }
        }

        return availableTimeSlots;
    }

    public void ordenarTurmasPorQuantidadeHorarios() {
        Collections.sort(turmas, new Comparator<Turma>() {
            @Override
            public int compare(Turma t1, Turma t2) {
                return Integer.compare(t1.horarios.size(), t2.horarios.size());
            }
        });
    }

    public void ordenarTurmasPorQuantidadeHorarios(Turma turma) {
        Collections.sort(turmas, new Comparator<Turma>() {
            @Override
            public int compare(Turma t1, Turma t2) {
                return Integer.compare(t1.horarios.size(), t2.horarios.size());
            }
        });
    }

    public void ordenarTurmasPorQuantidadeHorarios(ArrayList<Turma> turmas) {
        Collections.sort(turmas, new Comparator<Turma>() {
            @Override
            public int compare(Turma t1, Turma t2) {
                return Integer.compare(t1.horarios.size(), t2.horarios.size());
            }
        });
    }

    public boolean firstFitDecreasing(Turma turma) {
        ordenarSalasPorCapacidadeDecrescente();
        return firstFit(turma);
    }

    public void reset() {
        ensalamento.clear();
    }

    public void ordenarTurmasPorPesoHorariosAlunos() {
        Collections.sort(turmas, new Comparator<Turma>() {
            @Override
            public int compare(Turma t1, Turma t2) {
                int horarioComparison = Integer.compare(t1.horarios.size(), t2.horarios.size());

                if (horarioComparison != 0) {
                    return horarioComparison;
                }

                return Integer.compare(t1.numAlunos, t2.numAlunos);
            }
        });
    }

    public void ordenarTurmasPorPesoHorariosAlunos(Turma turma) {
        Collections.sort(turmas, new Comparator<Turma>() {
            @Override
            public int compare(Turma t1, Turma t2) {
                int horarioComparison = Integer.compare(t1.horarios.size(), t2.horarios.size());

                if (horarioComparison != 0) {
                    return horarioComparison;
                }

                return Integer.compare(t1.numAlunos, t2.numAlunos);
            }
        });
    }

    public void ordenarTurmasPorPesoHorariosAlunos(ArrayList<Turma> turmas) {
        Collections.sort(turmas, new Comparator<Turma>() {
            @Override
            public int compare(Turma t1, Turma t2) {
                int horarioComparison = Integer.compare(t1.horarios.size(), t2.horarios.size());

                if (horarioComparison != 0) {
                    return horarioComparison;
                }

                return Integer.compare(t1.numAlunos, t2.numAlunos);
            }
        });
    }

    public void ordenarTurmasPorPesoAlunosHorariosDecrescente() {
        Collections.sort(turmas, new Comparator<Turma>() {
            @Override
            public int compare(Turma t1, Turma t2) {
                int alunosComparison = Integer.compare(t2.numAlunos, t1.numAlunos);

                if (alunosComparison != 0) {
                    return alunosComparison;
                }

                return Integer.compare(t2.horarios.size(), t1.horarios.size());
            }
        });
    }

    public void ordenarTurmasPorPesoAlunosHorariosDecrescente(ArrayList<Turma> turmas) {
        Collections.sort(turmas, new Comparator<Turma>() {
            @Override
            public int compare(Turma t1, Turma t2) {
                int alunosComparison = Integer.compare(t2.numAlunos, t1.numAlunos);

                if (alunosComparison != 0) {
                    return alunosComparison;
                }

                return Integer.compare(t2.horarios.size(), t1.horarios.size());
            }
        });
    }

    // crear matrix com todos as combinações de turmas

    public ArrayList<ArrayList<ArrayList<Integer>>> possibilidades() {
        ArrayList<ArrayList<ArrayList<Integer>>> possibilidadesHorarioTurmaSala = new ArrayList<ArrayList<ArrayList<Integer>>>();

        int[] HORAS = { 8, 10, 12, 14, 16, 18, 20 };

        for (Sala sala : salas) {
            ArrayList<ArrayList<Integer>> possibilidadesHorarioTurma = new ArrayList<ArrayList<Integer>>();
            for (Turma turma : turmas) {

                ArrayList<Integer> possibilidadeHorario = new ArrayList<Integer>(); // Criar novo vetor em cada iteração
                for (int horario : HORAS) {
                    if (turma.horarios.contains(horario) && sala.acessivel == true
                            && sala.capacidade >= turma.numAlunos) {
                        possibilidadeHorario.add(1);
                    } else {
                        possibilidadeHorario.add(0);
                    }
                }
                possibilidadesHorarioTurma.add(possibilidadeHorario);
            }
            possibilidadesHorarioTurmaSala.add(possibilidadesHorarioTurma);
        }

        return possibilidadesHorarioTurmaSala;
    }

    static int avaliar(int[] individuo, ArrayList<ArrayList<ArrayList<Integer>>> possibilidadesHorarioTurmaSala) {
        int total = 0;
        boolean[] salasUtilizadas = new boolean[individuo.length];
    
        for (int t = 0; t < individuo.length; t++) {
            int s = individuo[t];
            ArrayList<ArrayList<Integer>> possibilidadesTurmaSala = possibilidadesHorarioTurmaSala.get(s);
            ArrayList<Integer> possibilidadesHorario = possibilidadesTurmaSala.get(t);
            
            if (possibilidadesHorario.contains(1) && !salasUtilizadas[s]) {
                total++;
                salasUtilizadas[s] = true;
            }
        }
    
        return total;
    }
    

    public ArrayList<ArrayList<Integer>> encontrarMelhorAlocacao(ArrayList<ArrayList<ArrayList<Integer>>> possibilidadesHorarioTurmaSala) {
        int numTurmas = possibilidadesHorarioTurmaSala.get(0).size();
        int numSalas = possibilidadesHorarioTurmaSala.size();

        // Parâmetros do algoritmo genético
        int tamanhoPopulacao = 100;
        int numGeracoes = 1000;
        double taxaMutacao = 0.1;

        Random random = new Random();

        // Inicialização da população
        List<int[]> populacao = new ArrayList<>();
        for (int i = 0; i < tamanhoPopulacao; i++) {
            int[] individuo = new int[numTurmas];
            for (int j = 0; j < numTurmas; j++) {
                individuo[j] = random.nextInt(numSalas);
            }
            populacao.add(individuo);
        }

        // Algoritmo genético
        for (int geracao = 0; geracao < numGeracoes; geracao++) {
            populacao.sort((indiv1, indiv2) -> Integer.compare(avaliar(indiv2, possibilidadesHorarioTurmaSala),
                    avaliar(indiv1, possibilidadesHorarioTurmaSala)));

            // Seleção dos pais (elitismo)
            List<int[]> pais = new ArrayList<>(populacao.subList(0, tamanhoPopulacao / 2));

            // Cruzamento
            List<int[]> filhos = new ArrayList<>();
            for (int i = 0; i < tamanhoPopulacao / 2; i++) {
                int[] pai1 = pais.get(random.nextInt(pais.size()));
                int[] pai2 = pais.get(random.nextInt(pais.size()));
                int pontoCorte = random.nextInt(numTurmas - 1) + 1;
                int[] filho = new int[numTurmas];
                System.arraycopy(pai1, 0, filho, 0, pontoCorte);
                System.arraycopy(pai2, pontoCorte, filho, pontoCorte, numTurmas - pontoCorte);
                filhos.add(filho);
            }

            // Mutação
            for (int[] filho : filhos) {
                if (random.nextDouble() < taxaMutacao) {
                    int turma = random.nextInt(numTurmas);
                    int novaSala = random.nextInt(numSalas);
                    filho[turma] = novaSala;
                }
            }

            // Nova população
            populacao = new ArrayList<>(pais);
            populacao.addAll(filhos);
        }

        // Encontrar o melhor indivíduo
        int[] melhorIndividuo = populacao.get(0);

        // Imprimir a alocação
        for (int turma = 0; turma < melhorIndividuo.length; turma++) {
            int sala = melhorIndividuo[turma];
            if (sala >= 0 && sala < numSalas) {
                System.out.println("Turma " + (turma + 1) + " -> Sala " + (char) ('A' + sala));
            } else {
                System.out.println("Turma " + (turma + 1) + " não alocada");
            }
        }

        //return the pair
        ArrayList<ArrayList<Integer>> pair = new ArrayList<ArrayList<Integer>>();

        for (int turma = 0; turma < melhorIndividuo.length; turma++) {
            int sala = melhorIndividuo[turma];
            if(sala >= 0 && sala < numSalas) {
                ArrayList<Integer> pairTurmaSala = new ArrayList<Integer>();
                pairTurmaSala.add(turma);
                pairTurmaSala.add(sala);
                pair.add(pairTurmaSala);
            }
            else{
                ArrayList<Integer> pairTurmaSala = new ArrayList<Integer>();
                pairTurmaSala.add(turma);
                pairTurmaSala.add(-1);
                pair.add(pairTurmaSala);
            }
        }

        return pair;
    }

    public void alocarTodas() {
        ordenarTurmasPorNumeroAlunosDecrescente();
        ordenarSalasPorCapacidadeDecrescente();

        // iterate possibilidade and print
        ArrayList<ArrayList<ArrayList<Integer>>> possibilidades = possibilidades();
        for (ArrayList<ArrayList<Integer>> possibilidade : possibilidades) {
            System.out.println(possibilidade);
        }
        System.out.println();
        
        //iterate encontrarMelhorAlocacao
        ArrayList<ArrayList<Integer>> pair = encontrarMelhorAlocacao(possibilidades);
        for (ArrayList<Integer> pairTurmaSala : pair) {
            int turma = pairTurmaSala.get(0);
            int sala = pairTurmaSala.get(1);
            if (sala >= 0 && turma != -1) {
                System.out.println("Turma " + (turma) + " -> Sala " + ( sala));
                if(alocar(turmas.get(turma), salas.get(sala))){
                    System.out.println("Alocado");
                }
            }
        }


        
    }

    public int getTheDiffBetweenSalaAndTurma(Turma turma, Sala sala) {
        return sala.capacidade - turma.numAlunos;
    }

    public int getTheDiffBetweenTurmasAlocadasAndTurmas() {
        int total = 0;
        for (TurmaEmSala turmaEmSala : this.ensalamento) {
            total += turmaEmSala.turma.numAlunos;
        }
        return total;
    }

    public int getTotalTurmasAlocadas() {

        return this.ensalamento.size();
    }

    public int getTotalEspacoLivre() {
        int total = 0;
        for (TurmaEmSala turmaEmSala : this.ensalamento) {
            total += turmaEmSala.sala.capacidade - turmaEmSala.turma.numAlunos;
        }
        return total;
    }

    public String relatorioResumoEnsalamento() {
        return "Total de Salas: " + this.salas.size() + "\n" +
                "Total de Turmas: " + this.turmas.size() + "\n" +
                "Turmas Alocadas: " + this.getTotalTurmasAlocadas() + "\n" +
                "Espaços Livres: " + this.getTotalEspacoLivre();
    }

    public String relatorioTurmasPorSala() {
        // String relatorio = relatorioResumoEnsalamento() + "\n\n";
        // for (Sala sala : this.salas) {
        // relatorio += "--- " + sala.getDescricao() + " ---\n\n";
        // for (TurmaEmSala turmaEmSala : this.ensalamento) {
        // if (turmaEmSala.sala == sala) {
        // relatorio += turmaEmSala.turma.getDescricao() + "\n";
        // }
        // }
        // }

        String relatorio = relatorioResumoEnsalamento() + "\n\n";
        for (Sala sala : this.salasAntesOrdenar) {
            relatorio += "--- " + sala.getDescricao() + " ---\n\n";
            for (TurmaEmSala turmaEmSala : this.ensalamento) {
                if (turmaEmSala.sala == sala) {
                    relatorio += turmaEmSala.turma.getDescricao() + "\n";
                }
            }
        }

        return relatorio;
    }

    public String relatorioSalasPorTurma() {
        String relatorio = relatorioResumoEnsalamento() + "\n\n";
        for (Turma turma : this.turmas) {
            relatorio += turma.getDescricao();
            boolean alocada = false;
            for (TurmaEmSala turmaEmSala : this.ensalamento) {
                if (turmaEmSala.turma == turma) {
                    relatorio += "Sala: " + turmaEmSala.sala.getDescricao();
                    alocada = true;
                    break;
                }
            }
            if (!alocada) {
                relatorio += "Sala: SEM SALA\n";
            }
        }
        return relatorio;
    }

}