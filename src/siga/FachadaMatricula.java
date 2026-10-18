package siga;

import java.util.ArrayList;
import java.util.List;

/**
 * DESLIZE 2 — a fachada conhece TODAS as reações a uma matrícula,
 * e a lista cresce a cada pedido da secretaria.
 *
 * A orquestração dos PASSOS (validar, gravar) é papel legítimo da fachada,
 * estabelecido na Aula 10 — isso não deve ser desfeito. O problema é a
 * lista de REAÇÕES ao fato já consumado: a quinta reação será a quinta
 * alteração nesta classe, que nada tem a ver com auditoria nem com painel.
 *
 * Repare também que a falha do AvisoCoordenacao derruba tudo o que viria
 * depois dele.
 */
public class FachadaMatricula {

    private final List<Matricula> armazem = new ArrayList<>();

    private final NotificadorEmail notificadorEmail = new NotificadorEmail();
    private final PainelVagas painelVagas = new PainelVagas();
    private final RegistroAuditoria registroAuditoria = new RegistroAuditoria();
    private final AvisoCoordenacao avisoCoordenacao = new AvisoCoordenacao();

    public Matricula matricular(Aluno aluno, String codigoTurma, boolean turmaLotada) {
        Matricula nova = new Matricula(aluno, codigoTurma);
        armazem.add(nova);

        notificadorEmail.enviarConfirmacao(nova);
        painelVagas.atualizar(codigoTurma);
        if (turmaLotada) {
            avisoCoordenacao.enviar(codigoTurma);
        }
        registroAuditoria.registrar("MATRICULA", aluno.getMatricula());

        return nova;
    }

    public List<Matricula> listar() {
        return new ArrayList<>(armazem);
    }
}
