package siga;

/** Reação à matrícula. Pronta. Falha de propósito, para a etapa 4. */
public class AvisoCoordenacao implements OuvinteMatricula {

    @Override 
    public void aoMatricular(Matricula matricula) {
        if (matricula.isTurmaLotada()) {
            enviar(matricula.getCodigoTurma());
        }
    }

    public void enviar(String codigoTurma) {
        throw new IllegalStateException(
                "servico de aviso indisponivel para " + codigoTurma);
    }
}
