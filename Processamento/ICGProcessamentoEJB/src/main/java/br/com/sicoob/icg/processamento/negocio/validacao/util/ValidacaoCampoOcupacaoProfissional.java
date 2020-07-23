package br.com.sicoob.icg.processamento.negocio.validacao.util;

import java.util.List;
import java.util.Objects;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.util.MensagemUtil;
import br.com.sicoob.capes.api.negocio.delegates.CAPESApiFabricaDelegates;
import br.com.sicoob.capes.api.negocio.filtros.FiltroOcupacaoProfissional;
import br.com.sicoob.capes.api.negocio.vo.OcupacaoProfissionalVO;
import br.com.sicoob.icg.processamento.negocio.mensagem.ICGProcessamentoResourceBundle;
import br.com.sicoob.icg.processamento.util.Constantes;

public class ValidacaoCampoOcupacaoProfissional implements Validacao {

	public Boolean ehNumerico(String valor) {
		try {
			Long.parseLong(valor);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public StringBuilder retornaValidacao(String valor, String linhaArquivo, String campo,
			Integer tamanhoCampoPermitido, Boolean obrigatorio) {
		StringBuilder retorno = new StringBuilder();
		boolean ocupacaoProfissionalValido = true;
		if (!ehNumerico(valor)) {
			ocupacaoProfissionalValido = false;
			retorno.append(MensagemUtil.getString(Constantes.MENSAGEM_MN007,
					ICGProcessamentoResourceBundle.getInstance(), linhaArquivo,
					MensagemUtil.getString(campo, ICGProcessamentoResourceBundle.getInstance()), valor));
		}

		if (Validar.validaTamanhoCampo(valor.length(), tamanhoCampoPermitido)) {
			ocupacaoProfissionalValido = false;
			retorno.append(MensagemUtil.getString(Constantes.MENSAGEM_MN003,
					ICGProcessamentoResourceBundle.getInstance(), linhaArquivo,
					MensagemUtil.getString(campo, ICGProcessamentoResourceBundle.getInstance()), valor));
		}

		try {
			if ((ocupacaoProfissionalValido) && (!validarOcupacaoProfissional(valor))) {
				retorno.append(MensagemUtil.getString(Constantes.MENSAGEM_MN007,
						ICGProcessamentoResourceBundle.getInstance(), linhaArquivo,
						MensagemUtil.getString(campo, ICGProcessamentoResourceBundle.getInstance()), valor));
			}
		} catch (BancoobException e) {
			retorno.append(MensagemUtil.getString(e.getMessage()));
		}

		return retorno;
	}

	private static Boolean validarOcupacaoProfissional(String codigo) throws BancoobException {
		FiltroOcupacaoProfissional filtroOcupacaoProfissional = new FiltroOcupacaoProfissional();
		filtroOcupacaoProfissional.setCodigo(codigo);
		filtroOcupacaoProfissional.setAtivo(true);
		List<OcupacaoProfissionalVO> ocupacaoProfissionalVO = CAPESApiFabricaDelegates.getInstance()
				.criarOcupacaoProfissionalDelegate().pesquisar(filtroOcupacaoProfissional);
		if (Objects.isNull(ocupacaoProfissionalVO)) {
			return false;
		}

		return true;
	}
}
