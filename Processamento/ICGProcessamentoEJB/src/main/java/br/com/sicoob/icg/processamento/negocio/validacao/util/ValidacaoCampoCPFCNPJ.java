package br.com.sicoob.icg.processamento.negocio.validacao.util;

import java.util.Objects;

import br.com.bancoob.util.MensagemUtil;
import br.com.sicoob.icg.processamento.negocio.enums.ConstantePessoaFisicaEnum;
import br.com.sicoob.icg.processamento.negocio.enums.ConstantePessoaJuridicaEnum;
import br.com.sicoob.icg.processamento.negocio.mensagem.ICGProcessamentoResourceBundle;
import br.com.sicoob.icg.processamento.util.Constantes;

public class ValidacaoCampoCPFCNPJ implements Validacao {

	private Boolean ehNumerico(String valor) {
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

		if (Objects.isNull(valor) || valor.isEmpty()) {
			retorno.append(MensagemUtil.getString(Constantes.MENSAGEM_MN009,
					ICGProcessamentoResourceBundle.getInstance(), linhaArquivo,
					MensagemUtil.getString(campo, ICGProcessamentoResourceBundle.getInstance()), valor));
		} else {
			if (!ehNumerico(valor)) {
				retorno.append(MensagemUtil.getString(Constantes.MENSAGEM_MN002,
						ICGProcessamentoResourceBundle.getInstance(), linhaArquivo,
						MensagemUtil.getString(campo, ICGProcessamentoResourceBundle.getInstance()), valor));
			}

			if (valor.length() != ConstantePessoaFisicaEnum.TAMANHO_CPF.getCodigo()
					&& valor.length() != ConstantePessoaJuridicaEnum.TAMANHO_CNPJ.getCodigo()) {
				retorno.append(MensagemUtil.getString(Constantes.MENSAGEM_MN007,
						ICGProcessamentoResourceBundle.getInstance(), linhaArquivo,
						MensagemUtil.getString(campo, ICGProcessamentoResourceBundle.getInstance()), valor));
			}

		}
		return retorno;
	}

}
