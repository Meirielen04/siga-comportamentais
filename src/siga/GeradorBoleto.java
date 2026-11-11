package siga;

public class GeradorBoleto {

    public void emitir(Matricula matricula, double valorBase) {
        double valorCalculado = matricula.calcularValor(valorBase);
        System.out.printf("   [boleto] %s: R$ %.2f%n", matricula.getAluno().getNome(), valorCalculado);
    }
}
