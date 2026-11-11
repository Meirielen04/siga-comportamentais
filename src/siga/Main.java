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

        PoliticaDesconto politicaBolsista = new DescontoBolsista();

        Aluno egresso = new Aluno("2024003", "Ana Pereira");
        egresso.setEgresso(true);

        PoliticaDesconto politicaEgresso = new DescontoEgresso();

        double valorBase = 800.00;
        GeradorBoleto gerador = new GeradorBoleto();

        System.out.println();
        System.out.println("1) Desconto calculado por uma política (Strategy)");

        Matricula matriculaBolsista = new Matricula(bolsista, "DSM-2A", politicaBolsista);
        Matricula matriculaEgresso = new Matricula(egresso, "DSM-2A", politicaEgresso);

        System.out.printf("   %-14s matricula: R$ %.2f%n",
                matriculaBolsista.getAluno().getNome(), matriculaBolsista.calcularValor(valorBase));
        gerador.emitir(matriculaBolsista, valorBase);

        System.out.printf("   %-14s matricula: R$ %.2f%n",
                matriculaEgresso.getAluno().getNome(),
                matriculaEgresso.calcularValor(valorBase));
        gerador.emitir(matriculaEgresso, valorBase);

        System.out.println("   >> CORRIGIDO: matricula e boleto usam a mesma politica,");
        System.out.println("      então Ana Pereira paga R$720,00 nos dois");

        System.out.println();
        System.out.println("2) Reacoes a matricula, todas dentro da fachada");
        FachadaMatricula fachada = new FachadaMatricula();
        fachada.matricular(bolsista, "DSM-2A", false, politicaBolsista);

        System.out.println();
        System.out.println("   Agora com a turma lotada, e o aviso a coordenacao falhando:");
        try {
            fachada.matricular(egresso, "DSM-2B", true, politicaEgresso);
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
