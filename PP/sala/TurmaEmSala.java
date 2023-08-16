public class TurmaEmSala{
    Turma turma;
    Sala sala;

    public TurmaEmSala(){
        this.turma = new Turma();
        this.sala = new Sala();
    }

    public TurmaEmSala(Turma turma, Sala sala){
        this.turma = turma;
        this.sala = sala;
    }


}