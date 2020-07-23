package br.com.sicoob.icg.processamento.negocio.servicos.ejb.steps;

import javax.ejb.EJB;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.icg.comum.negocio.servicos.interfaces.ImportacaoArquivoServicoLocal;
import br.com.sicoob.icg.negocio.entidades.ImportaArquivo;
import br.com.sicoob.icg.processamento.negocio.servicos.ProcessamentoServico;
import br.com.sicoob.sws.api.contexto.ContextoExecucao;
import br.com.sicoob.sws.api.execucao.RetornoExecucao;
import br.com.sicoob.sws.api.parametro.Parametro;

public abstract class ProcessarAtualizacaoArquivoStepEJB extends ICGStepBase {

	@EJB
	private ImportacaoArquivoServicoLocal importaAqruivoServico;

	public RetornoExecucao executarStep(ContextoExecucao contexto) throws BancoobException {
		Parametro parametroIdImportaArquivo = contexto.getParametrosStep().get("idImportaArquivo");
		final Long idImportaArquivo = parametroIdImportaArquivo.getValor();
		final ImportaArquivo arquivo = importaAqruivoServico.obter(idImportaArquivo);
		getServicoAtualizacao().iniciaAtualizacao(arquivo);
		return sucesso();
	}
	
	protected abstract ProcessamentoServico getServicoAtualizacao(); 

}