package siga;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== SIGA - Aula 12: Strategy e Observer ===");

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
        System.out.println("2) Reacoes a matricula, via publicador (Observer)");

        PublicadorMatricula publicador = new PublicadorMatricula();
        publicador.registrar(new NotificadorEmail());
        publicador.registrar(new PainelVagas());
        publicador.registrar(new AvisoCoordenacao());
        publicador.registrar(new RegistroAuditoria());
        publicador.registrar(new EstatisticaOcupacao());

        FachadaMatricula fachada = new FachadaMatricula(publicador);
        fachada.matricular(bolsista, "DSM-2A", false, politicaBolsista);
        System.out.println();
        System.out.println("   Agora com a turma lotada, e o aviso a coordenação falhando:");
        fachada.matricular(egresso, "DSM-2B", true, politicaEgresso);
        System.out.println("   >> CORRIGIDO: a falha do aviso foi isolada pelo publicador,");
        System.out.println("      e a auditoria aconteceu mesmo assim.");
    }
}
