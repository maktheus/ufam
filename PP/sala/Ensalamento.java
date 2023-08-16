import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
        ordenarSalasPorCapacidade();
    }

    public void addTurma(Turma turma) {
        this.turmas.add(turma);
        this.turmasAntesOrdenar.add(turma);
        ordenarTurmasPorNumeroAlunosDecrescente();
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
        if (sala.capacidade < turma.numAlunos) {
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

    // public int getMinimunDiffPointerBetweenTurmaAndSala(Sala sala) {
    // int minDiff = Integer.MAX_VALUE;
    // int minDiffPointer = -1;
    // for (int i = 0; i < this.turmas.size(); i++) {
    // Turma turma = this.turmas.get(i);
    // int diff = sala.capacidade - turma.numAlunos;
    // if (diff >= 0 && diff < minDiff && sala.acessivel == true &&
    // salaDisponivel(sala, turma.horarios) == true ) {
    // minDiff = diff;
    // minDiffPointer = i;
    // }
    // }
    // return minDiffPointer;
    // }

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

    public void alocarTodas() {
        // ordenarTurmasPorNumeroAlunosDecrescente();
        // ordenarTurmasPorNumeroAlunosDecrescente();
        // ordenarTurmasPorPesoAlunosHorariosDecrescente();

        // for (Turma turma : this.turmas) {
        // // best fit
        // bestFit(turma);

        // }

        ArrayList<Turma> turmasA = new ArrayList<>();
        ArrayList<Turma> turmasB = new ArrayList<>();
        ArrayList<Turma> turmasC = new ArrayList<>();
        ArrayList<Turma> turmasD = new ArrayList<>();

        // ordenar por quantidade de horarios
        int numTurmas = turmas.size();
        // int batchSize = (int) Math.ceil((double) numTurmas / 4);
        int count = 1;
        for (int i = 0; i < numTurmas; i++) {
            count++;
            Turma turma = turmas.get(i);
            if (count == 1) {
                // bestfit
                ordenarSalasPorCapacidade();
                bestFit(turma);
            } else if (count == 2) {
                // worst fit
                // ordenar sala
                ordenarSalasPorCapacidadeDecrescente();
                bestFit(turma);
            }

            if (count == 2) {
                count = 1;
            }
        }

        for (Turma turma : turmasA) {
            for (Sala sala : salas) {
                if (sala.acessivel && sala.capacidade >= turma.numAlunos && salaDisponivel(sala, turma.horarios)) {
                    alocar(turma, sala);
                    break;
                }
            }
        }

        for (Turma turma : turmasB) {
            for (Sala sala : salas) {
                if (sala.acessivel && sala.capacidade >= turma.numAlunos && salaDisponivel(sala, turma.horarios)) {
                    alocar(turma, sala);
                    break;
                }
            }
        }

        for (Turma turma : turmasC) {
            for (Sala sala : salas) {
                if (sala.acessivel && sala.capacidade >= turma.numAlunos && salaDisponivel(sala, turma.horarios)) {
                    alocar(turma, sala);
                    break;
                }
            }
        }

        for (Turma turma : turmasD) {
            for (Sala sala : salas) {
                if (sala.acessivel && sala.capacidade >= turma.numAlunos && salaDisponivel(sala, turma.horarios)) {
                    alocar(turma, sala);
                    break;
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