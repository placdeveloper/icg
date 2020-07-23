package br.com.sicoob.icg.processamento.negocio.servicos.ejb;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.icg.comum.negocio.servicos.interfaces.ImportacaoArquivoServicoLocal;
import br.com.sicoob.icg.processamento.negocio.servicos.ProcessamentoPessoaServico;
import br.com.sicoob.icg.processamento.negocio.servicos.ejb.steps.ICGStepTest;
import br.com.sicoob.icg.processamento.negocio.servicos.ejb.steps.ProcessarAtualizacaoArquivoPessoaFisicaStepEJB;
import br.com.sicoob.sws.api.contexto.ContextoExecucao;
import br.com.sicoob.sws.api.dominio.ResultadoExecucao;
import br.com.sicoob.sws.api.dominio.TipoParametro;
import br.com.sicoob.sws.api.execucao.RetornoExecucao;
import br.com.sicoob.sws.api.parametro.Parametro;

public class ProcessarPessoaFisicaServicoEJBTest extends ICGStepTest {

	@Mock
	private ProcessamentoPessoaServico servico;

	@Mock
	private ImportacaoArquivoServicoLocal arquivoServicoLocal;

	@InjectMocks
	private ProcessarAtualizacaoArquivoPessoaFisicaStepEJB ejb;

	@Before
	public void setUp() throws BancoobException {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void iniciarTest() throws BancoobException {

		ContextoExecucao contextoExecucao = criarContextoExecucao();
		Map<String, Parametro> mapStepParametro = new HashMap<>();
		Parametro parametro = new Parametro("idImportaArquivo", 25, TipoParametro.LONGO);
		mapStepParametro.put("idImportaArquivo", parametro);
		contextoExecucao.setParametrosStep(mapStepParametro);
		RetornoExecucao retorno = ejb.executarStep(contextoExecucao);
		Assert.assertEquals(ResultadoExecucao.SUCESSO, retorno.getResultado());

	}

}
