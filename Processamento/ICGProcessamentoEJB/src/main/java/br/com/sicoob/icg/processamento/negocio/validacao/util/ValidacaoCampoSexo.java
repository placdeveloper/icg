package br.com.sicoob.icg.processamento.negocio.validacao.util;

import java.util.Objects;

import br.com.bancoob.util.MensagemUtil;
import br.com.sicoob.capes.comum.negocio.enums.TipoSexoEnum;
import br.com.sicoob.icg.processamento.negocio.mensagem.ICGProcessamentoResourceBundle;
import br.com.sicoob.icg.processamento.util.Constantes;

public class ValidacaoCampoSexo implements Validacao {

	public Boolean ehSexoValido(String data) {

		Character sexo = data.charAt(0);
		
		return TipoSexoEnum.MASCULINO.getCodigo().equals(sexo) || TipoSexoEnum.FEMININO.getCodigo().equals(sexo);
		
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
			if (!ehSexoValido(valor)) {
				retorno.append(MensagemUtil.getString(Constantes.MENSAGEM_MN007,
						ICGProcessamentoResourceBundle.getInstance(), linhaArquivo,
						MensagemUtil.getString(campo, ICGProcessamentoResourceBundle.getInstance()), valor));
			}
		}
		return retorno;
	}
}
