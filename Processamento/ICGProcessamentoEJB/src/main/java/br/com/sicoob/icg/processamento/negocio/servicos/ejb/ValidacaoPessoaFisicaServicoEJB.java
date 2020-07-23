package br.com.sicoob.icg.processamento.negocio.servicos.ejb;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.util.MensagemUtil;
import br.com.sicoob.capes.comum.negocio.enums.TipoPessoaEnum;
import br.com.sicoob.icg.comum.negocio.servicos.PessoaFisicaImportacaoServico;
import br.com.sicoob.icg.negocio.entidades.ImportaArquivo;
import br.com.sicoob.icg.negocio.entidades.PessoaFisicaImportacao;
import br.com.sicoob.icg.negocio.entidades.vo.PessoaFisicaVO;
import br.com.sicoob.icg.negocio.enums.SituacaoProcessamentoEnum;
import br.com.sicoob.icg.processamento.negocio.entidades.vo.ValidacaoVO;
import br.com.sicoob.icg.processamento.negocio.enums.ConstantePessoaFisicaEnum;
import br.com.sicoob.icg.processamento.negocio.mensagem.ICGProcessamentoResourceBundle;
import br.com.sicoob.icg.processamento.negocio.servicos.ValidacaoPessoaFisicaServico;
import br.com.sicoob.icg.processamento.negocio.validacao.util.Validar;
import br.com.sicoob.icg.processamento.negocio.validacao.util.ValidarPessoaFisicaUtil;
import br.com.sicoob.icg.processamento.util.Constantes;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Local(ValidacaoPessoaFisicaServico.class)
public class ValidacaoPessoaFisicaServicoEJB extends ValidacaoServicoEJB implements ValidacaoPessoaFisicaServico {

	@EJB
	private PessoaFisicaImportacaoServico servico;

	protected ValidacaoVO validarJson(ImportaArquivo importaArquivo, Path diretorioArquivo) {
		ValidacaoVO validacao = new ValidacaoVO();
		try (Reader reader = new FileReader(diretorioArquivo.toFile())) {

			Gson gson = new Gson();
			Type collectionType = new TypeToken<List<PessoaFisicaVO>>() {
			}.getType();
			List<PessoaFisicaVO> lista = gson.fromJson(reader, collectionType);

			AtomicInteger index = new AtomicInteger();
			index.getAndIncrement();
			for (PessoaFisicaVO vo : lista) {

				vo.setInstituicao(importaArquivo.getIdInstituicao());
				vo.setLinhaArquivo("OBJETO: " + index.getAndIncrement());
				vo.setCodigoTipoPessoa(vo.getCodigoTipoPessoaFisica());

				ValidacaoVO validacaoItem = ValidarPessoaFisicaUtil.validar(vo);

				validacao.getErroValidacao().append(validacaoItem.getErroValidacao());

				cadastraRegistroValido(importaArquivo, vo, validacaoItem, validacao);
			}
			return validacao;

		} catch (FileNotFoundException e) {
			validacao.setErroValidacao(new StringBuilder().append(e.getMessage()));
			return validacao;
		} catch (IllegalStateException | IOException | JsonSyntaxException e) {
			validacao.setErroValidacao(new StringBuilder().append(
					MensagemUtil.getString(Constantes.MENSAGEM_MN010, ICGProcessamentoResourceBundle.getInstance())));
			return validacao;
		} catch (BancoobException e) {
			validacao.setErroValidacao(new StringBuilder().append(e.getMessage()));
			return validacao;
		}
	}

	protected ValidacaoVO validarCSV(ImportaArquivo importaArquivo, Path diretorioArquivo) {
		ValidacaoVO validacao = new ValidacaoVO();
		try {
			AtomicInteger index = new AtomicInteger();
			index.getAndIncrement();
			Files.readAllLines(diretorioArquivo, StandardCharsets.ISO_8859_1).stream().forEach(l -> {
				int qtdCamposLinha = l.split("\\|").length;
				final int indice = index.getAndIncrement();
				if (Validar.validaQuantidadeCamposObrigatorios(qtdCamposLinha,
						ConstantePessoaFisicaEnum.QTD_CAMPOS_OBRIGATORIOS.getCodigo())) {

					String s[] = l.split("\\|");
					PessoaFisicaVO vo = montarPessoaFisicaVO(s, importaArquivo);
					vo.setLinhaArquivo("LINHA: " + indice);

					ValidacaoVO validacaoItem = null;
					try {
						validacaoItem = ValidarPessoaFisicaUtil.validar(vo);
					} catch (BancoobException e) {
						validacao.setErroValidacao(new StringBuilder()
								.append(Constantes.MENSAGEM_ERRO_INCLUSAO_DADOS + vo.getLinhaArquivo()));
					}
					validacao.getErroValidacao().append(validacaoItem.getErroValidacao());
					cadastraRegistroValido(importaArquivo, vo, validacaoItem, validacao);
				} else {
					validacao.getErroValidacao()
							.append(MensagemUtil.getString(Constantes.MENSAGEM_MN012,
									ICGProcessamentoResourceBundle.getInstance(), "LINHA: " + indice, qtdCamposLinha,
									ConstantePessoaFisicaEnum.QTD_CAMPOS_OBRIGATORIOS.getCodigo()));
				}
			});
			return validacao;

		} catch (IOException e) {
			validacao.setErroValidacao(new StringBuilder().append(e.getMessage()));
			return validacao;
		}
	}

	private PessoaFisicaVO montarPessoaFisicaVO(String s[], ImportaArquivo importaArquivo) {
		PessoaFisicaVO vo = new PessoaFisicaVO();
		vo.setInstituicao(importaArquivo.getIdInstituicao());
		vo.setCodigoTipoPessoa(TipoPessoaEnum.PESSOA_FISICA.getCodigo().toString());
		vo.setCodigoNacionalidade(s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_CODNACIONALIDADE.getCodigo()].trim());
		vo.setCpfCnpj(s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_CPF.getCodigo()].trim());
		vo.setNomePessoa(s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_NOMEPESSOA.getCodigo()].trim());
		vo.setNomeCompleto(s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_NOMECOMPLETO.getCodigo()].trim());
		vo.setCodigoAtividadeEconomica(
				s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_CODATIVIDADEECONOMICA.getCodigo()].trim());
		vo.setMenorEmancipado(s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_BOLMENOREMANCIPADO.getCodigo()].trim());
		vo.setNomeMae(s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_NOMEMAE.getCodigo()].trim());
		vo.setNomePai(s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_NOMEPAI.getCodigo()].trim());
		vo.setCodigoTipoDocumento(
				s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_CODTIPODOCUMENTOIDENTIFICACAO.getCodigo()].trim());
		vo.setNumeroDocumento(s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_NUMDOCUMENTOIDENTIFICACAO.getCodigo()].trim());
		vo.setOrgaoExpedidorDocumento(
				s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_ORGAOEXPEDIDORDOCUMETO.getCodigo()].trim());
		vo.setUfOrgaoExpedidorDocumento(
				s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_SIGLAUFORGEXPDOCUMENTOIDENTIFICACAO.getCodigo()].trim());
		vo.setTipoSexo(s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_CODTIPOSEXO.getCodigo()].trim());
		vo.setUniaoEstavel(s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_BOLUNIAOESTAVEL.getCodigo()].trim());
		vo.setCodigoEstadoCivil(s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_CODESTADOCIVIL.getCodigo()].trim());
		vo.setDescNaturalidade(s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_DESCNATURALIDADE.getCodigo()].trim());
		vo.setCodigoNaturalidade(s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_CODIGO_NATURALIDADE.getCodigo()].trim());
		vo.setCodigoVinculoEmpregaticio(
				s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_CODVINCULOEMPREGATICIO.getCodigo()].trim() == null ? ""
						: s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_CODVINCULOEMPREGATICIO.getCodigo()].trim());
		vo.setCodigoOcupacaoProfissional(
				s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_IDOCUPACAOPROFISSIONAL.getCodigo()].trim() == null ? ""
						: s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_IDOCUPACAOPROFISSIONAL.getCodigo()].trim());
		vo.setCodigoGrauInstrucao(s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_CODGRAUINSTRUCAO.getCodigo()].trim());
		vo.setDataNascimento(s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_DATANASCIMENTO.getCodigo()].trim());
		vo.setDataEmissaoDocumento(s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_DATAEMISSAODOCUMENTO.getCodigo()].trim());
		vo.setQuantidadeDependentes(
				s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_QTDDEPENDENTE.getCodigo()].trim() == null ? ""
						: s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_QTDDEPENDENTE.getCodigo()].trim());
		vo.setCodigoRegimeCasamento(
				s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_CODREGIMECASAMENTO.getCodigo()].trim() == null ? ""
						: s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_CODREGIMECASAMENTO.getCodigo()].trim());
		vo.setCpfConjuge(s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_CPFCONJUGE.getCodigo()].trim() == null ? ""
				: s[ConstantePessoaFisicaEnum.POSICAO_CAMPO_CPFCONJUGE.getCodigo()].trim());
		return vo;
	}

	private void cadastraRegistroValido(ImportaArquivo importaArquivo, PessoaFisicaVO vo, ValidacaoVO validacaoItem,
			ValidacaoVO validacao) {
		if (validacaoItem.getRegistroValido()) {
			try {
				incluirPessoaFisica(importaArquivo, vo);
				validacao.setHouveInclusao(true);

			} catch (BancoobException e) {
				validacaoItem.setErroValidacao(
						new StringBuilder().append(Constantes.MENSAGEM_ERRO_INCLUSAO_DADOS + vo.getLinhaArquivo()));
			}
		}
	}

	private void incluirPessoaFisica(ImportaArquivo importaArquivo, PessoaFisicaVO vo) throws BancoobException {
		PessoaFisicaImportacao objeto = new PessoaFisicaImportacao();
		objeto.setCodigoAtividadeEconomica(vo.getCodigoAtividadeEconomica());
		objeto.setCodigoEstadoCivil(vo.getCodigoEstadoCivil());
		objeto.setCodigoGrauInstrucao(vo.getCodigoGrauInstrucao());
		objeto.setCodigoNacionalidade(vo.getCodigoNacionalidade());
		objeto.setCodigoNaturalidade(Validar.converterParaLong(vo.getCodigoNaturalidade()));
		objeto.setCodigoOcupacaoProfissional(Validar.converterParaLong(vo.getCodigoOcupacaoProfissional()));
		objeto.setCodigoRegimeCasamento(vo.getCodigoRegimeCasamento());
		objeto.setCodigoTipoDocumentoIdentificacao(vo.getCodigoTipoDocumento());
		objeto.setCodigoVinculoEmpregaticio(vo.getCodigoVinculoEmpregaticio());
		objeto.setCodigpTipoSexo(vo.getTipoSexo());
		Date dataEmissaoDocumentoIdentificacao = Validar.converterParaData(vo.getDataEmissaoDocumento());
		objeto.setDataEmissaoDocumentoIdentificacao(Validar.converterParaDateTimeDB(dataEmissaoDocumentoIdentificacao));
		Date dataNascimento = Validar.converterParaData(vo.getDataNascimento());
		objeto.setDataNascimento(Validar.converterParaDateTimeDB(dataNascimento));
		objeto.setDescricaoNaturalidade(vo.getDescNaturalidade());
		objeto.setDescricaoOrgaoExpedidorDocumentoIdentificacao(vo.getOrgaoExpedidorDocumento());
		objeto.setIdInstituicao(vo.getInstituicao());
		objeto.setMenorEmancipado(Validar.converterParaLong(vo.getMenorEmancipado()));
		objeto.setNomeCompleto(vo.getNomeCompleto());
		objeto.setNomeMae(vo.getNomeMae());
		objeto.setNomepai(vo.getNomePai());
		objeto.setNomePessoa(vo.getNomePessoa());
		objeto.setNumeroCpfConjuge(vo.getCpfConjuge());
		if (vo.getIdInstituicaoConjuge() != null) {
			objeto.setIdInstituicaoConjuge(Validar.converterParaLong(vo.getIdInstituicaoConjuge().toString()));
		}
		objeto.setNumeroDocumentoIdentificacao(vo.getNumeroDocumento());
		objeto.setQuantidadeDependente(Validar.converterParaLong(vo.getQuantidadeDependentes()));
		objeto.setSiglaUfOrgaoExpedidorDocumentoIdentificacao(vo.getUfOrgaoExpedidorDocumento());
		objeto.setUniaoEestavel(Validar.converterParaLong(vo.getUniaoEstavel()));
		objeto.setDescLinhaArquivo(vo.getLinhaArquivo());
		objeto.setNumeroCpfCnpj(vo.getCpfCnpj());
		objeto.setCodSituacaoProcessamento(SituacaoProcessamentoEnum.A_INICIAR.getIdSituacao());
		objeto.setCodigoTipoPessoa(vo.getCodigoTipoPessoa());
		objeto.setIdInstituicao(vo.getInstituicao());
		objeto.setDataHoraProcessamento(null);
		objeto.setImportaArquivo(importaArquivo);

		servico.incluir(objeto);
	}
}
