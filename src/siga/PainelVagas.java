package siga;

/** Reação à matrícula. Pronta. */
public class PainelVagas implements OuvinteMatricula {

    @Override
    public void aoMatricular(Matricula matricula) {
        atualizar(matricula.getCodigoTurma());
    }

    public void atualizar(String codigoTurma) {
        System.out.println("   [painel] vagas de " + codigoTurma + " atualizadas");
    }
}
