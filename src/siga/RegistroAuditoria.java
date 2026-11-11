package siga;

/** Reação à matrícula. Pronta. */
public class RegistroAuditoria implements OuvinteMatricula {

    @Override public void aoMatricular(Matricula matricula) {
        registrar("MATRICULA", matricula.getAluno().getMatricula());
    }
    
    public void registrar(String operacao, String matriculaAluno) {
        System.out.println("   [auditoria] " + operacao + " - " + matriculaAluno);
    }
}
