package br.com.sicoob.icg.processamento.negocio.validacao.util;

import java.util.Objects;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.util.MensagemUtil;
import br.com.bancoob.util.StringUtil;
import br.com.sicoob.capes.comum.negocio.enums.TipoDominioEnum;
import br.com.sicoob.icg.negocio.entidades.vo.FonteRendaVO;
import br.com.sicoob.icg.processamento.negocio.entidades.vo.ValidacaoVO;
import br.com.sicoob.icg.processamento.negocio.enums.ConstanteComumEnum;
import br.com.sicoob.icg.processamento.negocio.enums.ConstanteFonteRendaEnum;
import br.com.sicoob.icg.processamento.negocio.mensagem.ICGProcessamentoResourceBundle;
import br.com.sicoob.icg.processamento.negocio.servicos.util.ConsultasUtil;
import br.com.sicoob.icg.processamento.util.Constantes;

public class ValidarFonteRendaUtil {

	private ValidarFonteRendaUtil() {
	}

	public static ValidacaoVO validar(FonteRendaVO vo) throws BancoobException {

		ValidacaoVO validacaoVO = new ValidacaoVO();
		validacaoVO.setRegistroValido(true);
		validacaoVO.getErroValidacao().append("");

		ValidacaoCampoNumerico validacaoCampoNumerico = new ValidacaoCampoNumerico();
		ValidacaoCampoSimNao validacaoCampoBoleano = new ValidacaoCampoSimNao();
		ValidacaoTamanhoCampo validacaoTamanhoCampo = new ValidacaoTamanhoCampo();
		ValidacaoCampoData validacaoCampoData = new ValidacaoCampoData();
		ValidacaoCampoMontetario validacaoCampoMontetario = new ValidacaoCampoMontetario();

		validacaoVO.getErroValidacao()
				.append(validacaoCampoNumerico.retornaValidacao(vo.getCpfCnpj(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_CPF_CNPJ, ConstanteComumEnum.TAMANHO_CPF_CNPJ.getCodigo(), true));

		if (Objects.nonNull(vo.getCodigoTipoPessoa()) && StringUtil.isNotBlank(vo.getCodigoTipoPessoa())) {
			if (!ConsultasUtil.ehCodigoDominioValido(vo.getCodigoTipoPessoa().toString(),
					TipoDominioEnum.TIPO_PESSOA)) {
				validacaoVO.getErroValidacao()
						.append(MensagemUtil
								.getString(Constantes.MENSAGEM_MN007, ICGProcessamentoResourceBundle.getInstance(),
										vo.getLinhaArquivo(),
										MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_TIPO_PESSOA,
												ICGProcessamentoResourceBundle.getInstance()),
										vo.getCodigoTipoPessoa()));
			}
		} else {
			validacaoVO.getErroValidacao()
					.append(MensagemUtil.getString(Constantes.MENSAGEM_MN009,
							ICGProcessamentoResourceBundle.getInstance(), vo.getLinhaArquivo(),
							MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_TIPO_PESSOA,
									ICGProcessamentoResourceBundle.getInstance()),
							""));
		}

		if (Objects.nonNull(vo.getCodigoTipoFonteRenda()) && StringUtil.isNotBlank(vo.getCodigoTipoFonteRenda())) {
			if (!ConsultasUtil.ehCodigoDominioValido(vo.getCodigoTipoFonteRenda().toString(),
					TipoDominioEnum.TIPO_FONTE_RENDA)) {
				validacaoVO.getErroValidacao()
						.append(MensagemUtil
								.getString(Constantes.MENSAGEM_MN007, ICGProcessamentoResourceBundle.getInstance(),
										vo.getLinhaArquivo(),
										MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_TIPO_RENDA,
												ICGProcessamentoResourceBundle.getInstance()),
										vo.getCodigoTipoFonteRenda()));
			}
		} else {
			validacaoVO.getErroValidacao()
					.append(MensagemUtil.getString(Constantes.MENSAGEM_MN009,
							ICGProcessamentoResourceBundle.getInstance(), vo.getLinhaArquivo(),
							MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_TIPO_RENDA,
									ICGProcessamentoResourceBundle.getInstance()),
							""));
		}

		validacaoVO.getErroValidacao()
				.append(validacaoCampoBoleano.retornaValidacao(vo.getRendaFixa(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_RENDA_FIXA_VARIAVEL,
						ConstanteFonteRendaEnum.TAMANHO_BOLRENDAFIXA.getCodigo(), true));

		validacaoVO.getErroValidacao()
				.append(validacaoTamanhoCampo.retornaValidacao(vo.getRendaFixa(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_RENDA_FIXA_VARIAVEL,
						ConstanteFonteRendaEnum.TAMANHO_BOLRENDAFIXA.getCodigo(), true));

		validacaoVO.getErroValidacao()
				.append(validacaoCampoBoleano.retornaValidacao(vo.getBolSimplesNacional(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_CODIGO_SIMPLES_NACIONAL,
						ConstanteFonteRendaEnum.TAMANHO_BOLSIMPLESNACIONAL.getCodigo(), true));

		validacaoVO.getErroValidacao()
				.append(validacaoTamanhoCampo.retornaValidacao(vo.getBolSimplesNacional(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_CODIGO_SIMPLES_NACIONAL,
						ConstanteFonteRendaEnum.TAMANHO_BOLSIMPLESNACIONAL.getCodigo(), true));

		validacaoVO.getErroValidacao()
				.append(validacaoCampoData.retornaValidacao(vo.getDataValidadeRenda(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_DATA_VALIDADE_RENDA,
						ConstanteFonteRendaEnum.TAMANHO_DATA_VALIDADE.getCodigo(), true));

		validacaoVO.getErroValidacao()
				.append(validacaoCampoMontetario.retornaValidacao(vo.getValorReceitaBrutaMensal(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_VALOR_RECEITA_BRUTA_MENSAL,
						ConstanteFonteRendaEnum.TAMANHO_VALORRECEITABRUTAMENSAL.getCodigo(), true));

		validacaoVO.getErroValidacao()
				.append(validacaoTamanhoCampo.retornaValidacao(vo.getValorReceitaBrutaMensal(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_VALOR_RECEITA_BRUTA_MENSAL,
						ConstanteFonteRendaEnum.TAMANHO_VALORRECEITABRUTAMENSAL.getCodigo(), true));

		if (StringUtil.isNotBlank(validacaoVO.getErroValidacao().toString())) {
			validacaoVO.setRegistroValido(false);
		}

		return validacaoVO;
	}
}