package br.com.sicoob.icg.processamento.negocio.servicos.ejb.jobs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.commons.collections.CollectionUtils;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.icg.comum.negocio.servicos.interfaces.ImportacaoArquivoServicoLocal;
import br.com.sicoob.icg.negocio.entidades.ImportaArquivo;
import br.com.sicoob.icg.negocio.enums.TipoAtualizacaoEnum;
import br.com.sicoob.sws.api.contexto.ContextoExecucao;
import br.com.sicoob.sws.api.dominio.TipoParametro;
import br.com.sicoob.sws.api.execucao.Step;
import br.com.sicoob.sws.api.execucao.VerificacaoDependencias;
import br.com.sicoob.sws.api.parametro.Parametro;
import br.com.sicoob.sws.api.servico.JobServico;

@Stateless
@Remote(JobServico.class)
public class ProcessarAtualizacaoArquivoJobEJB extends ICGJobBase {
	
	private static final String NOME_JNDI_SERVICO_TELEFONE = "icg/processamento/ProcessarAtualizacaoArquivoTelefoneStepRemote";
	private static final String NOME_JNDI_SERVICO_EMAIL = "icg/processamento/ProcessarAtualizacaoArquivoEmailStepRemote";
	private static final String NOME_JNDI_SERVICO_ENDERECO = "icg/processamento/ProcessarAtualizacaoArquivoEnderecoStepRemote";
	private static final String NOME_JNDI_SERVICO_FONTE_RENDA = "icg/processamento/ProcessarAtualizacaoArquivoFonteRendaStepRemote";
	private static final String NOME_JNDI_SERVICO_PESSOA_FISICA = "icg/processamento/ProcessarAtualizacaoArquivoPessoaFisicaStepRemote";
	private static final String NOME_JNDI_SERVICO_PESSOA_JURIDICA = "icg/processamento/ProcessarAtualizacaoArquivoPessoaJuridicaStepRemote";

	@EJB
	private ImportacaoArquivoServicoLocal arquivoServicoLocal;

	@Override
	protected List<Step> executarObterSteps(ContextoExecucao contexto) throws BancoobException {
		List<Step> steps = new ArrayList<Step>();
		List<ImportaArquivo> listaArquivos = arquivoServicoLocal.listarArquivosImportadosPendentesProcessamento();
		for (ImportaArquivo arquivo : listaArquivos) {
			final String jndi;
			if (arquivo.getCodigoTipoArquivo().equals(TipoAtualizacaoEnum.TELEFONE.getIdTipoMigracao())) {
				jndi = NOME_JNDI_SERVICO_TELEFONE;
			} else if (arquivo.getCodigoTipoArquivo().equals(TipoAtualizacaoEnum.EMAIL.getIdTipoMigracao())) {
				jndi = NOME_JNDI_SERVICO_EMAIL;
			} else if (arquivo.getCodigoTipoArquivo().equals(TipoAtualizacaoEnum.ENDERECO.getIdTipoMigracao())) {
				jndi = NOME_JNDI_SERVICO_ENDERECO;
			} else if (arquivo.getCodigoTipoArquivo().equals(TipoAtualizacaoEnum.FONTERENDA.getIdTipoMigracao())) {
				jndi = NOME_JNDI_SERVICO_FONTE_RENDA;
			} else if (arquivo.getCodigoTipoArquivo().equals(TipoAtualizacaoEnum.PESSOAFISICA.getIdTipoMigracao())) {
				jndi = NOME_JNDI_SERVICO_PESSOA_FISICA;
			} else if (arquivo.getCodigoTipoArquivo().equals(TipoAtualizacaoEnum.PESSOAJURIDICA.getIdTipoMigracao())) {
				jndi = NOME_JNDI_SERVICO_PESSOA_JURIDICA;
			} else {
				continue;
			}
			Step step = ejb(jndi);
			Parametro parametro = new Parametro("idImportaArquivo", arquivo.getIdImportaArquivo(), TipoParametro.LONGO);
			step.comParametros(parametro);
			steps.add(step);
		}

		return steps;
	}

	@Override
	protected VerificacaoDependencias executarVerificarDependencias(ContextoExecucao contexto) throws BancoobException {
		List<ImportaArquivo> lista = arquivoServicoLocal.listarArquivosImportadosPendentesProcessamento();
		if(CollectionUtils.isNotEmpty(lista)) {
			return sucesso();
		}
		return finalizarFluxo("Não possuem aquivos pendentes para processar");
	}

}