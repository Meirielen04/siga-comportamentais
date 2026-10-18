package siga;

/**
 * DESLIZE 1 (parte B) — a MESMA cadeia de condicionais, escrita de novo.
 *
 * E já divergente: quando a política do egresso foi criada, ninguém
 * atualizou este trecho. O mesmo aluno recebe um valor na matrícula e
 * outro no boleto.
 */
public class GeradorBoleto {

    public double calcularValorDoBoleto(Aluno aluno, double valorBase) {
        if (aluno.isBolsista()) {
            return valorBase * 0.50;
        } else if (aluno.isServidor()) {
            return valorBase * 0.80;
        }
        return valorBase;   // o desconto de egresso nunca chegou aqui
    }

    public void emitir(Aluno aluno, double valorBase) {
        double valor = calcularValorDoBoleto(aluno, valorBase);
        System.out.printf("   [boleto] %s: R$ %.2f%n", aluno.getNome(), valor);
    }
}
