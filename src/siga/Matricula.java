package siga;

public class Matricula {

    private final Aluno aluno;
    private final String codigoTurma;
    private final PoliticaDesconto politica;
    private final boolean turmaLotada;

    public Matricula(Aluno aluno, String codigoTurma, PoliticaDesconto politica) {
        this(aluno, codigoTurma, politica, false);
    }

    public Matricula(Aluno aluno, String codigoTurma, PoliticaDesconto politica, boolean turmaLotada) {
        this.aluno = aluno;
        this.codigoTurma = codigoTurma;
        this.politica = politica;
        this.turmaLotada = turmaLotada;
    }

    public boolean isTurmaLotada() {
        return turmaLotada;
    }

    public PoliticaDesconto getPolitica() {
        return politica;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public String getCodigoTurma() {
        return codigoTurma;
    }

    public double calcularValor(double valorBase) {
        return politica.aplicar(valorBase);
    }

    @Override
    public String toString() {
        return aluno.getNome() + " em " + codigoTurma;
    }
}
