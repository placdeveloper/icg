package br.com.sicoob.icg.processamento.negocio.validacao.util;

import java.util.Objects;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.util.MensagemUtil;
import br.com.bancoob.util.StringUtil;
import br.com.sicoob.capes.comum.negocio.enums.TipoDominioEnum;
import br.com.sicoob.icg.negocio.entidades.vo.EnderecoVO;
import br.com.sicoob.icg.processamento.negocio.entidades.vo.ValidacaoVO;
import br.com.sicoob.icg.processamento.negocio.enums.ConstanteComumEnum;
import br.com.sicoob.icg.processamento.negocio.enums.ConstanteEnderecoEnum;
import br.com.sicoob.icg.processamento.negocio.mensagem.ICGProcessamentoResourceBundle;
import br.com.sicoob.icg.processamento.negocio.servicos.util.ConsultasUtil;
import br.com.sicoob.icg.processamento.util.Constantes;

public class ValidarEnderecoUtil {

	private ValidarEnderecoUtil() {
	}

	public static ValidacaoVO validar(EnderecoVO vo) throws BancoobException {

		ValidacaoVO validacaoVO = new ValidacaoVO();
		validacaoVO.setRegistroValido(true);
		validacaoVO.getErroValidacao().append("");

		ValidacaoCampoNumerico validacaoCampoNumerico = new ValidacaoCampoNumerico();
		ValidacaoCampoCEP validacaoCampoCEP = new ValidacaoCampoCEP();
		ValidacaoTamanhoCampo validacaoTamanhoCampo = new ValidacaoTamanhoCampo();

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

		if (Objects.nonNull(vo.getCodigoTipoEndereco()) && StringUtil.isNotBlank(vo.getCodigoTipoEndereco())) {
			if (!ConsultasUtil.ehCodigoDominioValido(vo.getCodigoTipoEndereco().toString(),
					TipoDominioEnum.TIPO_ENDERECO)) {
				validacaoVO.getErroValidacao()
						.append(MensagemUtil
								.getString(Constantes.MENSAGEM_MN007, ICGProcessamentoResourceBundle.getInstance(),
										vo.getLinhaArquivo(),
										MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_TIPO_ENDERECO,
												ICGProcessamentoResourceBundle.getInstance()),
										vo.getCodigoTipoEndereco()));
			}
		} else {
			validacaoVO.getErroValidacao()
					.append(MensagemUtil.getString(Constantes.MENSAGEM_MN009,
							ICGProcessamentoResourceBundle.getInstance(), vo.getLinhaArquivo(),
							MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_TIPO_ENDERECO,
									ICGProcessamentoResourceBundle.getInstance()),
							""));
		}

		validacaoVO.getErroValidacao().append(validacaoCampoCEP.retornaValidacao(vo.getCep(), vo.getLinhaArquivo(),
				Constantes.MENSAGEM_TEXTO_CEP, ConstanteEnderecoEnum.TAMANHO_CODCEP.getCodigo(), true));
		validacaoVO.getErroValidacao()
				.append(validacaoTamanhoCampo.retornaValidacao(vo.getBairro(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_BAIRRO, ConstanteEnderecoEnum.TAMANHO_NOMEBAIRRO.getCodigo(), true));
		validacaoVO.getErroValidacao()
				.append(validacaoTamanhoCampo.retornaValidacao(vo.getLogradouro(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_LOGRADOURO, ConstanteEnderecoEnum.TAMANHO_DESCLOGRADOURO.getCodigo(),
						true));
		validacaoVO.getErroValidacao()
				.append(validacaoTamanhoCampo.retornaValidacao(vo.getComplemento(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_COMPLEMENTO_ENDERECO,
						ConstanteEnderecoEnum.TAMANHO_DESCCOMPLEMENTO.getCodigo(), false));
		validacaoVO.getErroValidacao()
				.append(validacaoTamanhoCampo.retornaValidacao(vo.getNumero(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_NUMERO_ENDERECO, ConstanteEnderecoEnum.TAMANHO_DESCNUMERO.getCodigo(),
						false));
		validacaoVO.getErroValidacao()
				.append(validacaoCampoNumerico.retornaValidacao(vo.getCodigoLocalidade(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_CODIGO_LOCALIDADE,
						ConstanteEnderecoEnum.TAMANHO_CODLOCALIDADE.getCodigo(), true));
		validacaoVO.getErroValidacao()
				.append(validacaoCampoNumerico.retornaValidacao(vo.getCodigoTipoLogradouro(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_CODIGO_TIPO_LOGRADOURO,
						ConstanteEnderecoEnum.TAMANHO_CODTIPOLOGRADOURO.getCodigo(), true));

		if (StringUtil.isNotBlank(validacaoVO.getErroValidacao().toString())) {
			validacaoVO.setRegistroValido(false);
		}

		return validacaoVO;
	}

}