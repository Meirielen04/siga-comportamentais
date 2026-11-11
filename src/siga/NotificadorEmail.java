package siga;

/** Reação à matrícula. Pronta (apenas imprime no console). */
public class NotificadorEmail implements OuvinteMatricula {

    @Override 
    public void aoMatricular(Matricula matricula) {
        enviarConfirmacao(matricula);
    }
    
    public void enviarConfirmacao(Matricula matricula) {
        System.out.println("   [e-mail] confirmacao para "
                + matricula.getAluno().getNome());
    }
}
