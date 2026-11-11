# SIGA — Atividade de padrões comportamentais: Strategy e Observer

**Técnicas de Programação II (TP2) · Aula 12** — CST em Desenvolvimento de Software Multiplataforma · Fatec de Porto Ferreira

Este é o **código final** da atividade prática da Aula 12. O código inicial compilava e executava, mas a saída mostrava um aluno pagando **dois valores diferentes** pela mesma matrícula e uma falha em uma reação impedindo as reações seguintes. Eram **dois deslizes**, e cada um foi resolvido com um dos padrões da aula: o **Strategy** corrigiu o cálculo de desconto e o **Observer** desacoplou as reações à matrícula.

## Estrutura do projeto

```
siga-comportamentais/
└── src/
    └── siga/
        ├── Aluno.java                (entidade de domínio; não foi alterada)
        │
        │   Strategy — política de desconto
        ├── PoliticaDesconto.java     (interface: aplicar e getDescricao)
        ├── SemDesconto.java          (sem desconto)
        ├── DescontoBolsista.java     (50%)
        ├── DescontoServidor.java     (20%)
        ├── DescontoEgresso.java      (10%)
        ├── Matricula.java            (recebe a política e delega a ela)
        ├── GeradorBoleto.java        (usa a mesma política da matrícula)
        │
        │   Observer — reações à matrícula
        ├── OuvinteMatricula.java     (interface: aoMatricular)
        ├── PublicadorMatricula.java  (registrar, remover e publicar)
        ├── FachadaMatricula.java     (orquestra os passos e publica o evento)
        ├── NotificadorEmail.java     (ouvinte)
        ├── PainelVagas.java          (ouvinte)
        ├── AvisoCoordenacao.java     (ouvinte que falha de propósito)
        ├── RegistroAuditoria.java    (ouvinte)
        ├── EstatisticaOcupacao.java  (ouvinte; quinta reação, da etapa 5)
        │
        └── Main.java                 (monta as políticas e os ouvintes e demonstra as correções)
```

## Como compilar e executar

Pré-requisito: JDK 17 ou superior (`java -version` para verificar).

```bash
# 1. Compilar (a saída vai para a pasta "bin")
javac -encoding UTF-8 -d bin src/siga/*.java

# 2. Executar
java -cp bin siga.Main
```

Saída obtida com o código final:

```
=== SIGA - Aula 12: Strategy e Observer ===

1) Desconto calculado por uma política (Strategy)
   Maria Silva    matricula: R$ 400,00
   [boleto] Maria Silva: R$ 400,00
   Ana Pereira    matricula: R$ 720,00
   [boleto] Ana Pereira: R$ 720,00
   >> CORRIGIDO: matricula e boleto usam a mesma politica,
      então Ana Pereira paga R$720,00 nos dois

2) Reacoes a matricula, via publicador (Observer)
   [e-mail] confirmacao para Maria Silva
   [painel] vagas de DSM-2A atualizadas
   [auditoria] MATRICULA - 2024001
   [estatistica] DSM-2A: 1 matricula(s)

   Agora com a turma lotada, e o aviso a coordenação falhando:
   [e-mail] confirmacao para Ana Pereira
   [painel] vagas de DSM-2B atualizadas
   [erro] AvisoCoordenacao: servico de aviso indisponivel para DSM-2B
   [auditoria] MATRICULA - 2024003
   [estatistica] DSM-2B: 1 matricula(s)
   >> CORRIGIDO: a falha do aviso foi isolada pelo publicador,
      e a auditoria aconteceu mesmo assim.
```

## Os dois deslizes que existiam no código inicial

| # | Deslize | Onde estava | Por que era um problema | Como foi resolvido |
|---|---|---|---|---|
| 1 | **Cadeia de condicionais duplicada e divergente** | `Matricula.calcularValor` e `GeradorBoleto.calcularValorDoBoleto` | Quando a política do egresso foi criada, ninguém atualizou o gerador de boletos: **Ana Pereira pagava R$ 720,00 na matrícula e R$ 800,00 no boleto**. Cada nova política obrigava a alterar e retestar duas classes. | **Strategy**: cada desconto passou a ser uma classe que implementa `PoliticaDesconto`, e as duas classes passaram a delegar à mesma política. |
| 2 | **Reações embutidas na fachada** | `FachadaMatricula.matricular` | A fachada conhecia as quatro reações a uma matrícula, e cada nova reação exigia alterá-la. Como as chamadas eram sequenciais e desprotegidas, a **falha do aviso à coordenação impedia a auditoria**, que vinha depois dela. | **Observer**: as reações viraram ouvintes de `PublicadorMatricula`, e a fachada passou apenas a publicar a matrícula. |

### Etapa 1 — Localização da divergência

A divergência estava entre o método `calcularValor` em `Matricula.java` e o método `calcularValorDoBoleto` em `GeradorBoleto.java`. Em ambas as classes, esses métodos calculavam duas condições iguais: a de retornar desconto de 50% (R$ 400) para aluno bolsista e a de desconto de 20% (R$ 640) para servidor. Mesmo duplicado, o `GeradorBoleto.java` não aplicava o desconto para aluno egresso, como fazia `Matricula.java`. Com isso, a Ana Pereira era cobrada R$ 720 na matrícula, mas na hora de pagar o boleto o valor vinha cheio, R$ 800. Faltava o `valorBase * 0.90` no gerador. Isso aconteceu porque, quando criaram a política de egresso, só atualizaram uma das cópias.

## Critério de sucesso

Ao final, verificou-se que:

- (a) Ana Pereira passou a pagar **o mesmo valor**, R$ 720,00, na matrícula e no boleto;
- (b) cada percentual de desconto ficou em **um único lugar** do projeto;
- (c) a palavra `if` não aparece em nenhuma regra de desconto;
- (d) a quinta reação custou **uma classe nova e uma linha de registro**, sem tocar na fachada;
- (e) com o aviso à coordenação falhando, a **auditoria continuou acontecendo**, como mostra a saída acima.


