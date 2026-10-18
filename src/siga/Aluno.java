package siga;

/** Entidade de domínio. Pronta — não precisa ser alterada nesta atividade. */
public class Aluno {

    private final String matricula;
    private final String nome;
    private boolean bolsista;
    private boolean servidor;
    private boolean egresso;

    public Aluno(String matricula, String nome) {
        this.matricula = matricula;
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public boolean isBolsista() {
        return bolsista;
    }

    public void setBolsista(boolean bolsista) {
        this.bolsista = bolsista;
    }

    public boolean isServidor() {
        return servidor;
    }

    public void setServidor(boolean servidor) {
        this.servidor = servidor;
    }

    public boolean isEgresso() {
        return egresso;
    }

    public void setEgresso(boolean egresso) {
        this.egresso = egresso;
    }
}
