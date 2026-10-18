package siga;

/**
 * Demonstra os dois deslizes acontecendo. Execute e observe a saída:
 * é dela que partem as etapas da ficha de atividade prática.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== SIGA - Aula 12: Strategy e Observer (codigo inicial) ===");

        Aluno bolsista = new Aluno("2024001", "Maria Silva");
        bolsista.setBolsista(true);

        Aluno egresso = new Aluno("2024003", "Ana Pereira");
        egresso.setEgresso(true);

        double valorBase = 800.00;
        GeradorBoleto gerador = new GeradorBoleto();

        System.out.println();
        System.out.println("1) Desconto calculado em DOIS lugares diferentes");
        for (Aluno a : new Aluno[] { bolsista, egresso }) {
            Matricula m = new Matricula(a, "DSM-2A");
            System.out.printf("   %-14s matricula: R$ %.2f%n",
                    a.getNome(), m.calcularValor(valorBase));
            gerador.emitir(a, valorBase);
        }
        System.out.println("   >> DESLIZE 1: Ana Pereira paga R$ 720,00 na matricula");
        System.out.println("      e R$ 800,00 no boleto. A mesma cadeia de condicionais");
        System.out.println("      existe nas duas classes, e o desconto de egresso");
        System.out.println("      nunca chegou ao gerador de boletos.");

        System.out.println();
        System.out.println("2) Reacoes a matricula, todas dentro da fachada");
        FachadaMatricula fachada = new FachadaMatricula();
        fachada.matricular(bolsista, "DSM-2A", false);

        System.out.println();
        System.out.println("   Agora com a turma lotada, e o aviso a coordenacao falhando:");
        try {
            fachada.matricular(egresso, "DSM-2B", true);
        } catch (IllegalStateException e) {
            System.out.println("   [erro] " + e.getMessage());
        }
        System.out.println("   >> DESLIZE 2: a falha de UMA reacao impediu a auditoria,");
        System.out.println("      que vinha depois dela. E a quinta reacao sera a quinta");
        System.out.println("      alteracao nesta mesma classe.");

        System.out.println();
        System.out.println("Sua tarefa: ver o README.md e a ficha de atividade pratica.");
    }
}
