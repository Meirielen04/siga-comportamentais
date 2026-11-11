package siga;

import java.util.HashMap;
import java.util.Map;

public class EstatisticaOcupacao implements OuvinteMatricula {

    private final Map<String, Integer> matriculasPorTurma = new HashMap<>();

    @Override
    public void aoMatricular(Matricula matricula) {
        String turma = matricula.getCodigoTurma();
        int total = matriculasPorTurma.getOrDefault(turma, 0) + 1;
        matriculasPorTurma.put(turma, total);

        System.out.println("   [estatistica] " + turma + ": " + total + " matricula(s)");
    }

    public int getTotal(String codigoTurma) {
        return matriculasPorTurma.getOrDefault(codigoTurma, 0);
    }
}
