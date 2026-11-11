package siga;

import java.util.ArrayList;
import java.util.List;

public class PublicadorMatricula {

    private final List<OuvinteMatricula> ouvintes = new ArrayList<>();

    public void registrar(OuvinteMatricula ouvinte) {
        ouvintes.add(ouvinte);
    }

    public void remover(OuvinteMatricula ouvinte) {
        ouvintes.remove(ouvinte);
    }

    public void publicar(Matricula matricula) {
        for (OuvinteMatricula ouvinte : new ArrayList<>(ouvintes)) {
            try {
                ouvinte.aoMatricular(matricula);
            } catch (RuntimeException e) {
                System.out.println("   [erro] " + ouvinte.getClass().getSimpleName()
                        + ": " + e.getMessage());
            }
        }
    }
}
