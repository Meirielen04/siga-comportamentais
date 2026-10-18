package siga;

/** Reação à matrícula. Pronta. */
public class RegistroAuditoria {

    public void registrar(String operacao, String matriculaAluno) {
        System.out.println("   [auditoria] " + operacao + " - " + matriculaAluno);
    }
}
