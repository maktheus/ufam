
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Ensalamento1 {
    
    ArrayList<Sala> salas = new ArrayList<Sala>();
    ArrayList<Turma> turmas = new ArrayList<Turma>();
    ArrayList<TurmaEmSala> ensalamento = new ArrayList<TurmaEmSala>();

    public Ensalamento1() {
        this.salas = new ArrayList<Sala>();
        this.turmas = new ArrayList<Turma>();
        this.ensalamento = new ArrayList<TurmaEmSala>();
        //print ensalamento e salas before ordenção	
     
    }

    public void addSala(Sala sala) {
        this.salas.add(sala);
    }

    public void addTurma(Turma turma) {
        this.turmas.add(turma);
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

        // Verifica se a sala está disponível no horário

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
        // Verificar quantidade de Alunos
        if (sala.acessivel == false) {
            return false;
        }

        if (sala.capacidade < turma.numAlunos) {
            return false;
        }
        // Verificar se a sala é acessível

        // Verificar se a sala está disponível no horário
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
        Collections.sort(this.turmas, new Comparator<Turma>() {
            @Override
            public int compare(Turma t1, Turma t2) {
                return t2.numAlunos - t1.numAlunos;
            }
        });
    }

    public void ordenarSalasPorCapacidade() {
        Collections.sort(this.salas, new Comparator<Sala>() {
            @Override
            public int compare(Sala s1, Sala s2) {
                return s1.capacidade - s2.capacidade;
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

    public void alocarTodas() {
        ordenarTurmasPorNumeroAlunosDecrescente();
        ordenarSalasPorCapacidadeDecrescente();
    
        for (Turma turma : this.turmas) {
            for (Sala sala : this.salas) {
                if (this.alocar(turma, sala)) {
                    break;
                }
            }
        }
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
        String relatorio = relatorioResumoEnsalamento() + "\n\n";
        for (Sala sala : this.salas) {
            relatorio += "--- " + sala.getDescricao() + " ---\n";
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