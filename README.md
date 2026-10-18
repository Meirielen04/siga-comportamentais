# SIGA — Atividade de padrões comportamentais: Strategy e Observer (código inicial)

**Técnicas de Programação II (TP2) · Aula 12** — CST em Desenvolvimento de Software Multiplataforma · Fatec de Porto Ferreira

Este é o **código inicial** da atividade prática da Aula 12. Ele compila e executa — e, desta vez, a saída já mostra um aluno pagando **dois valores diferentes** pela mesma matrícula. São **dois deslizes**, e cada um dos padrões da aula resolve um deles.

## Estrutura do projeto

```
siga-comportamentais/
└── src/
    └── siga/
        ├── Aluno.java              (entidade de domínio; pronta)
        ├── Matricula.java          (cadeia de condicionais — deslize 1A)
        ├── GeradorBoleto.java      (a mesma cadeia, divergente — deslize 1B)
        ├── FachadaMatricula.java   (todas as reações embutidas — deslize 2)
        ├── NotificadorEmail.java   (reação; pronta)
        ├── PainelVagas.java        (reação; pronta)
        ├── RegistroAuditoria.java  (reação; pronta)
        ├── AvisoCoordenacao.java   (reação que falha de propósito; pronta)
        └── Main.java               (demonstra os dois deslizes)
```

## Como compilar e executar

Pré-requisito: JDK 17 ou superior (`java -version` para verificar).

```bash
# 1. Compilar (a saída vai para a pasta "bin")
javac -encoding UTF-8 -d bin src/siga/*.java

# 2. Executar
java -cp bin siga.Main
```

Execute antes de ler o restante deste arquivo: a saída **mostra os dois deslizes acontecendo**.

## Os dois deslizes propositais

| # | Deslize | Onde | Por que é um problema |
|---|---|---|---|
| 1 | **Cadeia de condicionais duplicada e divergente** | `Matricula.calcularValor` e `GeradorBoleto.calcularValorDoBoleto` | É a situação-problema da Aula 3, agora em dois lugares. Quando a política do egresso foi criada, ninguém atualizou o gerador de boletos: **Ana Pereira paga R$ 720,00 na matrícula e R$ 800,00 no boleto**. Cada nova política obriga a alterar e retestar duas classes que já funcionavam. |
| 2 | **Reações embutidas na fachada** | `FachadaMatricula.matricular` | A fachada conhece as quatro reações a uma matrícula, e a quinta será a quinta alteração nesta classe. Pior: como as chamadas são sequenciais e desprotegidas, a **falha do aviso à coordenação impede a auditoria**, que vinha depois dela. |

> **Atenção ao que NÃO é deslize.** A fachada orquestrar os *passos* da matrícula é papel legítimo dela, estabelecido na Aula 10 — isso não deve ser desfeito. O problema é a lista de *reações* ao fato já consumado. Ao final, a fachada continua existindo, e menor.

## Sua tarefa

Siga as etapas da ficha de atividade prática:

1. **Localizar a divergência.** Identifique as duas cadeias de condicionais e descreva neste README em que ponto exato elas discordam, e qual aluno é afetado.
2. **Criar as estratégias.** Escreva a interface `PoliticaDesconto`, com `aplicar(double valorBase)` e `getDescricao()`, e as quatro políticas concretas: `SemDesconto`, `DescontoBolsista` (50%), `DescontoServidor` (20%) e `DescontoEgresso` (10%). Cada percentual deve aparecer **uma vez só** no projeto.
3. **Injetar a estratégia.** Refatore `Matricula` para receber uma `PoliticaDesconto` e delegar a ela, e faça `GeradorBoleto` usar a mesma política da matrícula, em vez da sua própria cadeia. Ao terminar, **nenhum `if` de desconto** deve existir em nenhuma das duas classes.
4. **Criar o Observer.** Escreva a interface `OuvinteMatricula`, com `aoMatricular(Matricula)`, e a classe `PublicadorMatricula`, com `registrar`, `remover` e `publicar`. Converta as quatro reações em ouvintes. O laço de `publicar` deve percorrer uma **cópia** da lista e **proteger** a chamada de cada ouvinte, de modo que a falha do aviso à coordenação não impeça a auditoria.
5. **Provar que funciona.** Acrescente uma quinta reação — a estatística de ocupação do curso — sem alterar uma única linha da `FachadaMatricula`.

## Critério de sucesso

Ao final, verifique que: (a) Ana Pereira paga **o mesmo valor** na matrícula e no boleto; (b) cada percentual de desconto aparece em **um único lugar** do projeto; (c) a palavra `if` não aparece em nenhuma regra de desconto; (d) a quinta reação custou **uma classe nova e uma linha de registro**, sem tocar na fachada; e (e) com o aviso à coordenação falhando, a **auditoria continua acontecendo**.

> O item (e) é o que distingue uma implementação de livro de uma que sobrevive em produção. Repare que ele exige, em `publicar`, um `catch` que registra e segue adiante — a única situação em toda a disciplina em que isso é a escolha certa, porque a falha de um ouvinte é problema dele, não dos outros.

## Uma armadilha frequente na etapa 3

É comum o aluno criar as quatro políticas corretamente e, em seguida, escrever dentro de `Matricula` algo assim:

```java
// ✗ Isto apenas mudou o if de lugar
if (aluno.isBolsista()) {
    this.politica = new DescontoBolsista();
} else if (aluno.isServidor()) {
    ...
}
```

A matrícula voltou a decidir. Quem escolhe a política é **quem monta a matrícula** — no `Main`, ou em uma classe de fábrica, se a equipe preferir. A `Matricula` apenas recebe e delega.

## Padrão de entrega

Conforme a ficha de atividade prática: identificadores em português, um arquivo `.java` por classe pública, código formatado, entrega no repositório Git com README e commits descritivos. O uso de IA para gerar o código é proibido nesta atividade (ver seção 5.3 da ficha).

> **Projeto Integrador:** a Etapa 2 vence na **próxima aula (21/10)** e exige um padrão estrutural e um comportamental, ambos justificados. O que você produzir aqui pode ser aproveitado — o Strategy costuma ser a escolha mais segura, porque quase todo projeto tem uma regra que varia. Para o Observer, confirme antes que há de fato mais de uma reação ao acontecimento: uma lista de observadores com um elemento é uma lista que não precisava existir.
