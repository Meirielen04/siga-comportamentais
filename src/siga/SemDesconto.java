package siga;

public class SemDesconto implements PoliticaDesconto {
    
    @Override 
    public double aplicar(double valorBase) {
        return valorBase;
    }

    @Override 
    public String getDescricao() {
        return "Sem desconto";
    }
}
