package siga;

public interface PoliticaDesconto {
    double aplicar(double valorBase);
    String getDescricao();
}