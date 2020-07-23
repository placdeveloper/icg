package br.com.sicoob.icg.processamento.negocio.validacao.util;

import java.util.Objects;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.util.MensagemUtil;
import br.com.bancoob.util.StringUtil;
import br.com.sicoob.capes.api.negocio.vo.PessoaVO;
import br.com.sicoob.capes.comum.negocio.enums.TipoDominioEnum;
import br.com.sicoob.icg.negocio.entidades.vo.PessoaFisicaVO;
import br.com.sicoob.icg.processamento.negocio.entidades.vo.ValidacaoVO;
import br.com.sicoob.icg.processamento.negocio.enums.ConstanteComumEnum;
import br.com.sicoob.icg.processamento.negocio.enums.ConstantePessoaFisicaEnum;
import br.com.sicoob.icg.processamento.negocio.mensagem.ICGProcessamentoResourceBundle;
import br.com.sicoob.icg.processamento.negocio.servicos.util.ConsultasUtil;
import br.com.sicoob.icg.processamento.util.Constantes;

public class ValidarPessoaFisicaUtil {

	private ValidarPessoaFisicaUtil() {
	}

	private static PessoaVO pessoaConjugeVO;

	public PessoaVO getPessoaConjugeVO() {
		return pessoaConjugeVO;
	}

	public static ValidacaoVO validar(PessoaFisicaVO vo) throws BancoobException {

		ValidacaoVO validacaoVO = new ValidacaoVO();
		validacaoVO.setRegistroValido(true);

		ValidacaoCampoNumerico validacaoCampoNumerico = new ValidacaoCampoNumerico();
		ValidacaoTamanhoCampo validacaoTamanhoCampo = new ValidacaoTamanhoCampo();
		ValidacaoCampoSimNao validacaoCampoSimNao = new ValidacaoCampoSimNao();
		ValidacaoCampoSexo validacaoCampoSexo = new ValidacaoCampoSexo();
		ValidacaoCampoData validacaoCampoData = new ValidacaoCampoData();
		ValidacaoCampoOcupacaoProfissional validacaoCampoOcupacaoProfissional = new ValidacaoCampoOcupacaoProfissional();

		Boolean conjugeValido = true;

		validacaoVO.getErroValidacao()
				.append(validacaoCampoNumerico.retornaValidacao(vo.getCpfCnpj(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_CPF_CNPJ, ConstanteComumEnum.TAMANHO_CPF.getCodigo(), true));

		validacaoVO.getErroValidacao()
				.append(validacaoTamanhoCampo.retornaValidacao(vo.getNomePessoa(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_NOME_PESSOA_FISICA,
						ConstantePessoaFisicaEnum.TAMANHO_NOMEPESSOA.getCodigo(), true));

		validacaoVO.getErroValidacao()
				.append(validacaoTamanhoCampo.retornaValidacao(vo.getNomeCompleto(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_NOME_COMPLETO_PESSOA_FISICA,
						ConstantePessoaFisicaEnum.TAMANHO_NOMECOMPLETO.getCodigo(), true));

		if (Objects.nonNull(vo.getCodigoAtividadeEconomica())
				&& StringUtil.isNotBlank(vo.getCodigoAtividadeEconomica())) {
			if (!ConsultasUtil.ehCodigoDominioValido(vo.getCodigoAtividadeEconomica().toString(),
					TipoDominioEnum.ATIVIDADE_ECONOMICA)) {
				validacaoVO.getErroValidacao()
						.append(MensagemUtil.getString(Constantes.MENSAGEM_MN007,
								ICGProcessamentoResourceBundle.getInstance(), vo.getLinhaArquivo(),
								MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_ATIVIDADE_ECONOMICA,
										ICGProcessamentoResourceBundle.getInstance()),
								vo.getCodigoAtividadeEconomica()));
			}
		} else {
			validacaoVO.getErroValidacao()
					.append(MensagemUtil.getString(Constantes.MENSAGEM_MN009,
							ICGProcessamentoResourceBundle.getInstance(), vo.getLinhaArquivo(),
							MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_ATIVIDADE_ECONOMICA,
									ICGProcessamentoResourceBundle.getInstance()),
							""));
		}

		validacaoVO.getErroValidacao()
				.append(validacaoCampoSimNao.retornaValidacao(vo.getMenorEmancipado(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_MENOR_EMANCIPADO,
						ConstantePessoaFisicaEnum.TAMANHO_BOLMENOREMANCIPADO.getCodigo(), true));

		validacaoVO.getErroValidacao()
				.append(validacaoTamanhoCampo.retornaValidacao(vo.getMenorEmancipado(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_MENOR_EMANCIPADO,
						ConstantePessoaFisicaEnum.TAMANHO_BOLMENOREMANCIPADO.getCodigo(), true));

		validacaoVO.getErroValidacao()
				.append(validacaoCampoSexo.retornaValidacao(vo.getTipoSexo(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_TIPO_SEXO, ConstantePessoaFisicaEnum.TAMANHO_CODTIPOSEXO.getCodigo(),
						true));

		if (Objects.nonNull(vo.getUniaoEstavel()) && StringUtil.isNotBlank(vo.getUniaoEstavel())) {
			validacaoVO.getErroValidacao()
					.append(validacaoCampoSimNao.retornaValidacao(vo.getUniaoEstavel(), vo.getLinhaArquivo(),
							Constantes.MENSAGEM_TEXTO_CODIGO_UNIAO_ESTAVEL,
							ConstantePessoaFisicaEnum.TAMANHO_BOLUNIAOESTAVEL.getCodigo(), true));

			validacaoVO.getErroValidacao()
					.append(validacaoTamanhoCampo.retornaValidacao(vo.getUniaoEstavel(), vo.getLinhaArquivo(),
							Constantes.MENSAGEM_TEXTO_CODIGO_UNIAO_ESTAVEL,
							ConstantePessoaFisicaEnum.TAMANHO_BOLUNIAOESTAVEL.getCodigo(), true));
		}

		validacaoVO.getErroValidacao()
				.append(validacaoCampoData.retornaValidacao(vo.getDataNascimento(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_DATA_NASCIMENTO,
						ConstantePessoaFisicaEnum.TAMANHO_CAMPO_DATA.getCodigo(), true));

		validacaoVO.getErroValidacao()
				.append(validacaoTamanhoCampo.retornaValidacao(vo.getNomeMae(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_NOME_MAE, ConstantePessoaFisicaEnum.TAMANHO_NOMEMAE.getCodigo(),
						false));

		validacaoVO.getErroValidacao()
				.append(validacaoTamanhoCampo.retornaValidacao(vo.getNomePai(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_NOME_PAI, ConstantePessoaFisicaEnum.TAMANHO_NOMEPAI.getCodigo(),
						false));

		if (Objects.nonNull(vo.getCodigoTipoDocumento()) && StringUtil.isNotBlank(vo.getCodigoTipoDocumento())) {
			if (!ConsultasUtil.ehCodigoDominioValido(vo.getCodigoTipoDocumento().toString(),
					TipoDominioEnum.TIPO_DOCUMENTO_IDENTIFICACAO)) {
				validacaoVO.getErroValidacao()
						.append(MensagemUtil.getString(Constantes.MENSAGEM_MN007,
								ICGProcessamentoResourceBundle.getInstance(), vo.getLinhaArquivo(),
								MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_TIPO_DOCUMENTO_IDENTIFICACAO,
										ICGProcessamentoResourceBundle.getInstance()),
								vo.getCodigoTipoDocumento()));
			}
		}
		validacaoVO.getErroValidacao()
				.append(validacaoTamanhoCampo.retornaValidacao(vo.getNumeroDocumento(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_NUMERO_DOCUMENTO_IDENTIFICACAO,
						ConstantePessoaFisicaEnum.TAMANHO_NUMDOCUMENTOIDENTIFICACAO.getCodigo(), false));

		validacaoVO.getErroValidacao()
				.append(validacaoTamanhoCampo.retornaValidacao(vo.getOrgaoExpedidorDocumento(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_ORGAO_EXPEDIDOR_DOCUMENTO_IDENTIFICACAO,
						ConstantePessoaFisicaEnum.TAMANHO_ORGAOEXPEDIDORDOCUMETO.getCodigo(), false));

		validacaoVO.getErroValidacao()
				.append(validacaoTamanhoCampo.retornaValidacao(vo.getUfOrgaoExpedidorDocumento(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_UF_DOCUMENTO_IDENTIFICACAO,
						ConstantePessoaFisicaEnum.TAMANHO_UFORGEXPDOCUMENTOIDENTIFICACAO.getCodigo(), false));

		validacaoVO.getErroValidacao()
				.append(validacaoCampoData.retornaValidacao(vo.getDataEmissaoDocumento().trim(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_DATA_EMISSAO_DOCUMENTO,
						ConstantePessoaFisicaEnum.TAMANHO_CAMPO_DATA.getCodigo(), false));

		if (Objects.nonNull(vo.getCodigoNacionalidade()) && StringUtil.isNotBlank(vo.getCodigoNacionalidade())) {
			if (!ConsultasUtil.ehCodigoDominioValido(vo.getCodigoNacionalidade().toString(),
					TipoDominioEnum.NACIONALIDADE)) {
				validacaoVO.getErroValidacao()
						.append(MensagemUtil
								.getString(Constantes.MENSAGEM_MN007, ICGProcessamentoResourceBundle.getInstance(),
										vo.getLinhaArquivo(),
										MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_NACIONALIDADE,
												ICGProcessamentoResourceBundle.getInstance()),
										vo.getCodigoNacionalidade()));
			}
		} else {
			validacaoVO.getErroValidacao()
					.append(MensagemUtil.getString(Constantes.MENSAGEM_MN009,
							ICGProcessamentoResourceBundle.getInstance(), vo.getLinhaArquivo(),
							MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_NACIONALIDADE,
									ICGProcessamentoResourceBundle.getInstance()),
							""));
		}

		validacaoVO.getErroValidacao()
				.append(validacaoTamanhoCampo.retornaValidacao(vo.getDescNaturalidade(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_DESCRICAO_LOCAL_NATURALIDADE,
						ConstantePessoaFisicaEnum.TAMANHO_DESCNATURALIDADE.getCodigo(), true));

		validacaoVO.getErroValidacao()
				.append(validacaoCampoNumerico.retornaValidacao(vo.getCodigoNaturalidade(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_CODIGO_LOCAL_NATURALIDADE,
						ConstantePessoaFisicaEnum.TAMANHO_CODIGO_NATURALIDADE.getCodigo(), true));

		if (Objects.nonNull(vo.getCodigoVinculoEmpregaticio())
				&& StringUtil.isNotBlank(vo.getCodigoVinculoEmpregaticio())) {
			if (!ConsultasUtil.ehCodigoDominioValido(vo.getCodigoVinculoEmpregaticio().toString(),
					TipoDominioEnum.VINCULO_EMPREGATICIO)) {
				validacaoVO.getErroValidacao()
						.append(MensagemUtil.getString(Constantes.MENSAGEM_MN007,
								ICGProcessamentoResourceBundle.getInstance(), vo.getLinhaArquivo(),
								MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_VINCULO_EMPREGATICIO,
										ICGProcessamentoResourceBundle.getInstance()),
								vo.getCodigoVinculoEmpregaticio()));
			}
		} else {
			validacaoVO.getErroValidacao()
					.append(MensagemUtil.getString(Constantes.MENSAGEM_MN009,
							ICGProcessamentoResourceBundle.getInstance(), vo.getLinhaArquivo(),
							MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_VINCULO_EMPREGATICIO,
									ICGProcessamentoResourceBundle.getInstance()),
							""));
		}

		validacaoVO.getErroValidacao()
				.append(validacaoCampoOcupacaoProfissional.retornaValidacao(vo.getCodigoOcupacaoProfissional(),
						vo.getLinhaArquivo(), Constantes.MENSAGEM_TEXTO_CODIGO_OCUPACAO_PROFISSIONAL,
						ConstantePessoaFisicaEnum.TAMANHO_IDOCUPACAOPROFISSIONAL.getCodigo(), true));

		if (Objects.nonNull(vo.getCodigoGrauInstrucao()) && StringUtil.isNotBlank(vo.getCodigoGrauInstrucao())) {
			if (!ConsultasUtil.ehCodigoDominioValido(vo.getCodigoGrauInstrucao().toString(),
					TipoDominioEnum.GRAU_INSTRUCAO)) {
				validacaoVO.getErroValidacao()
						.append(MensagemUtil
								.getString(Constantes.MENSAGEM_MN007, ICGProcessamentoResourceBundle.getInstance(),
										vo.getLinhaArquivo(),
										MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_GRAU_INSTRUCAO,
												ICGProcessamentoResourceBundle.getInstance()),
										vo.getCodigoGrauInstrucao()));
			}
		} else {
			validacaoVO.getErroValidacao()
					.append(MensagemUtil.getString(Constantes.MENSAGEM_MN009,
							ICGProcessamentoResourceBundle.getInstance(), vo.getLinhaArquivo(),
							MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_GRAU_INSTRUCAO,
									ICGProcessamentoResourceBundle.getInstance()),
							""));
		}

		if (Objects.nonNull(vo.getCodigoEstadoCivil()) && StringUtil.isNotBlank(vo.getCodigoEstadoCivil())) {
			if (!ConsultasUtil.ehCodigoDominioValido(vo.getCodigoEstadoCivil().toString(),
					TipoDominioEnum.ESTADO_CIVIL)) {
				validacaoVO.getErroValidacao()
						.append(MensagemUtil
								.getString(Constantes.MENSAGEM_MN007, ICGProcessamentoResourceBundle.getInstance(),
										vo.getLinhaArquivo(),
										MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_ESTADOCIVIL,
												ICGProcessamentoResourceBundle.getInstance()),
										vo.getCodigoEstadoCivil()));
			} else {
				if (Objects.nonNull(vo.getCpfConjuge()) && StringUtil.isNotBlank(vo.getCpfConjuge())) {
					if (!Validar.isNumero(vo.getCpfConjuge())) {
						conjugeValido = false;
						validacaoVO.getErroValidacao()
								.append(MensagemUtil
										.getString(Constantes.MENSAGEM_MN002,
												ICGProcessamentoResourceBundle.getInstance(), vo.getLinhaArquivo(),
												MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CPF_CONJUGE,
														ICGProcessamentoResourceBundle.getInstance()),
												vo.getCpfConjuge()));
					}

					if (!ConstantePessoaFisicaEnum.TAMANHO_CPF.getCodigo().equals(vo.getCpfConjuge().length())) {
						conjugeValido = false;
						validacaoVO.getErroValidacao()
								.append(MensagemUtil
										.getString(Constantes.MENSAGEM_MN003,
												ICGProcessamentoResourceBundle.getInstance(), vo.getLinhaArquivo(),
												MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CPF_CONJUGE,
														ICGProcessamentoResourceBundle.getInstance()),
												vo.getCpfConjuge()));
					}

					if (conjugeValido.equals(true)) {
						pessoaConjugeVO = ConsultasUtil.recuperaInstituicaoConjuge(vo.getCpfConjuge(),
								vo.getInstituicao());
						if (Objects.isNull(pessoaConjugeVO)) {
							validacaoVO.getErroValidacao()
									.append(MensagemUtil
											.getString(Constantes.MENSAGEM_MN006,
													ICGProcessamentoResourceBundle.getInstance(), vo.getLinhaArquivo(),
													MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CPF_CONJUGE,
															ICGProcessamentoResourceBundle.getInstance()),
													vo.getCpfConjuge()));
						} else {
							vo.setIdInstituicaoConjuge(pessoaConjugeVO.getIdInstituicao());
						}
					}

				}
			}
		} else {
			validacaoVO.getErroValidacao()
					.append(MensagemUtil.getString(Constantes.MENSAGEM_MN009,
							ICGProcessamentoResourceBundle.getInstance(), vo.getLinhaArquivo(),
							MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_ESTADOCIVIL,
									ICGProcessamentoResourceBundle.getInstance()),
							""));
		}

		validacaoVO.getErroValidacao()
				.append(validacaoCampoNumerico.retornaValidacao(vo.getQuantidadeDependentes(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_QUANTIDADE_DEPENDENTE,
						ConstantePessoaFisicaEnum.TAMANHO_QTDDEPENDENTE.getCodigo(), true));

		if (Objects.nonNull(vo.getCodigoRegimeCasamento()) && StringUtil.isNotBlank(vo.getCodigoRegimeCasamento())) {
			if (!ConsultasUtil.ehCodigoDominioValido(vo.getCodigoRegimeCasamento().toString(),
					TipoDominioEnum.REGIME_CASAMENTO)) {
				validacaoVO.getErroValidacao()
						.append(MensagemUtil
								.getString(Constantes.MENSAGEM_MN007, ICGProcessamentoResourceBundle.getInstance(),
										vo.getLinhaArquivo(),
										MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_REGIME_CASAMENTO,
												ICGProcessamentoResourceBundle.getInstance()),
										vo.getCodigoRegimeCasamento()));
			}
		} else {
			validacaoVO.getErroValidacao()
					.append(MensagemUtil.getString(Constantes.MENSAGEM_MN009,
							ICGProcessamentoResourceBundle.getInstance(), vo.getLinhaArquivo(),
							MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_REGIME_CASAMENTO,
									ICGProcessamentoResourceBundle.getInstance()),
							""));
		}

		if (StringUtil.isNotBlank(validacaoVO.getErroValidacao().toString())) {
			validacaoVO.setRegistroValido(false);
		}

		return validacaoVO;

	}
}