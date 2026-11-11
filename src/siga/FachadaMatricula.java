package siga;

import java.util.ArrayList;
import java.util.List;
public class FachadaMatricula {

    private final List<Matricula> armazem = new ArrayList<>();
    private final PublicadorMatricula publicador;

    public FachadaMatricula(PublicadorMatricula publicador) {
        this.publicador = publicador;
    }

    public Matricula matricular(Aluno aluno, String codigoTurma, boolean turmaLotada, PoliticaDesconto politica) {
        Matricula nova = new Matricula(aluno, codigoTurma, politica, turmaLotada);
        armazem.add(nova);

        publicador.publicar(nova);

        return nova;
    }

    public List<Matricula> listar() {
        return new ArrayList<>(armazem);
    }
}
