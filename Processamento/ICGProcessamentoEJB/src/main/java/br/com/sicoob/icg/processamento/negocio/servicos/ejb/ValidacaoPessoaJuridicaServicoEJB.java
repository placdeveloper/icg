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
import br.com.sicoob.icg.comum.negocio.servicos.PessoaJuridicaImportacaoServico;
import br.com.sicoob.icg.negocio.entidades.ImportaArquivo;
import br.com.sicoob.icg.negocio.entidades.PessoaJuridicaImportacao;
import br.com.sicoob.icg.negocio.entidades.vo.PessoaJuridicaVO;
import br.com.sicoob.icg.negocio.enums.SituacaoProcessamentoEnum;
import br.com.sicoob.icg.processamento.negocio.entidades.vo.ValidacaoVO;
import br.com.sicoob.icg.processamento.negocio.enums.ConstantePessoaJuridicaEnum;
import br.com.sicoob.icg.processamento.negocio.mensagem.ICGProcessamentoResourceBundle;
import br.com.sicoob.icg.processamento.negocio.servicos.ValidacaoPessoaJuridicaServico;
import br.com.sicoob.icg.processamento.negocio.validacao.util.Validar;
import br.com.sicoob.icg.processamento.negocio.validacao.util.ValidarPessoaJuridicaUtil;
import br.com.sicoob.icg.processamento.util.Constantes;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Local(ValidacaoPessoaJuridicaServico.class)
public class ValidacaoPessoaJuridicaServicoEJB extends ValidacaoServicoEJB implements ValidacaoPessoaJuridicaServico {

	@EJB
	private PessoaJuridicaImportacaoServico servico;

	protected ValidacaoVO validarJson(ImportaArquivo importaArquivo, Path diretorioArquivo) {
		ValidacaoVO validacao = new ValidacaoVO();
		try (Reader reader = new FileReader(diretorioArquivo.toFile())) {

			Gson gson = new Gson();
			Type collectionType = new TypeToken<List<PessoaJuridicaVO>>() {
			}.getType();
			List<PessoaJuridicaVO> lista = gson.fromJson(reader, collectionType);

			AtomicInteger index = new AtomicInteger();
			index.getAndIncrement();
			for (PessoaJuridicaVO vo : lista) {

				vo.setInstituicao(importaArquivo.getIdInstituicao());
				vo.setLinhaArquivo("OBJETO: " + index.getAndIncrement());
				vo.setCodigoTipoPessoa(vo.getCodigoTipoPessoaJuridica());

				ValidacaoVO validacaoItem = ValidarPessoaJuridicaUtil.validar(vo);

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
				final int indice = index.getAndIncrement();
				int qtdCamposLinha = l.split("\\|").length;
				if (Validar.validaQuantidadeCamposObrigatorios(qtdCamposLinha,
						ConstantePessoaJuridicaEnum.QTD_CAMPOS_OBRIGATORIOS.getCodigo())) {

					String s[] = l.split("\\|");
					PessoaJuridicaVO vo = montarPessoaJuridicaVO(s, importaArquivo);
					vo.setLinhaArquivo("LINHA: " + indice);

					ValidacaoVO validacaoItem = null;
					try {
						validacaoItem = ValidarPessoaJuridicaUtil.validar(vo);
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
									ConstantePessoaJuridicaEnum.QTD_CAMPOS_OBRIGATORIOS.getCodigo()));

				}
			});
			return validacao;

		} catch (IOException e) {
			validacao.setErroValidacao(new StringBuilder().append(e.getMessage()));
			return validacao;
		}
	}

	private PessoaJuridicaVO montarPessoaJuridicaVO(String s[], ImportaArquivo importaArquivo) {
		PessoaJuridicaVO vo = new PessoaJuridicaVO();
		vo.setInstituicao(importaArquivo.getIdInstituicao());
		vo.setCodigoTipoPessoa(vo.getCodigoTipoPessoaJuridica());
		vo.setCpfCnpj(s[ConstantePessoaJuridicaEnum.POSICAO_CAMPO_CNPJ.getCodigo()].trim() == null ? ""
				: s[ConstantePessoaJuridicaEnum.POSICAO_CAMPO_CNPJ.getCodigo()].trim());
		vo.setCodigoAtividadeEconomica(
				s[ConstantePessoaJuridicaEnum.POSICAO_CAMPO_CODATIVIDADEECONOMICA.getCodigo()].trim() == null ? ""
						: s[ConstantePessoaJuridicaEnum.POSICAO_CAMPO_CODATIVIDADEECONOMICA.getCodigo()].trim());
		vo.setCodigoEsferaAdministrativa(
				s[ConstantePessoaJuridicaEnum.POSICAO_CAMPO_CODESFERAADMINISTRATIVA.getCodigo()].trim() == null ? ""
						: s[ConstantePessoaJuridicaEnum.POSICAO_CAMPO_CODESFERAADMINISTRATIVA.getCodigo()].trim());
		vo.setDataConstituicao(
				s[ConstantePessoaJuridicaEnum.POSICAO_CAMPO_DATACONSTITUICAO.getCodigo()].trim() == null ? ""
						: s[ConstantePessoaJuridicaEnum.POSICAO_CAMPO_DATACONSTITUICAO.getCodigo()].trim());
		vo.setNumeroRegistroJuntaComercial(
				s[ConstantePessoaJuridicaEnum.POSICAO_CAMPO_NUMREGISTROJUNTACOMERCIAL.getCodigo()].trim() == null ? ""
						: s[ConstantePessoaJuridicaEnum.POSICAO_CAMPO_NUMREGISTROJUNTACOMERCIAL.getCodigo()].trim());
		vo.setDataRegistroJuntaComercial(
				s[ConstantePessoaJuridicaEnum.POSICAO_CAMPO_DATAREGISTROJUNTACOMERCIAL.getCodigo()].trim() == null ? ""
						: s[ConstantePessoaJuridicaEnum.POSICAO_CAMPO_DATAREGISTROJUNTACOMERCIAL.getCodigo()].trim());
		vo.setNomePessoa(s[ConstantePessoaJuridicaEnum.POSICAO_CAMPO_RAZAO_SOCIAL.getCodigo()].trim() == null ? ""
				: s[ConstantePessoaJuridicaEnum.POSICAO_CAMPO_RAZAO_SOCIAL.getCodigo()].trim());
		vo.setNomeCompleto(
				s[ConstantePessoaJuridicaEnum.POSICAO_CAMPO_RAZAO_SOCIAL_COMPLETO.getCodigo()].trim() == null ? ""
						: s[ConstantePessoaJuridicaEnum.POSICAO_CAMPO_RAZAO_SOCIAL_COMPLETO.getCodigo()].trim());
		vo.setCodigoTipoFormaConstituicao(
				s[ConstantePessoaJuridicaEnum.POSICAO_CAMPO_CODIGOTIPOFORMACONSTITUICAO.getCodigo()].trim() == null ? ""
						: s[ConstantePessoaJuridicaEnum.POSICAO_CAMPO_CODIGOTIPOFORMACONSTITUICAO.getCodigo()].trim());

		return vo;
	}

	private void cadastraRegistroValido(ImportaArquivo importaArquivo, PessoaJuridicaVO vo, ValidacaoVO validacaoItem,
			ValidacaoVO validacao) {
		if (validacaoItem.getRegistroValido()) {
			try {
				incluirPessoaJuridica(importaArquivo, vo);
				validacao.setHouveInclusao(true);

			} catch (BancoobException e) {
				validacaoItem.setErroValidacao(
						new StringBuilder().append(Constantes.MENSAGEM_ERRO_INCLUSAO_DADOS + vo.getLinhaArquivo()));
			}
		}
	}

	private void incluirPessoaJuridica(ImportaArquivo importaArquivo, PessoaJuridicaVO vo) throws BancoobException {
		PessoaJuridicaImportacao objeto = new PessoaJuridicaImportacao();
		objeto.setCodigoAtividadeEconomica(vo.getCodigoAtividadeEconomica());
		objeto.setCodigoEsferaAdministrativa(vo.getCodigoEsferaAdministrativa());
		Date dataConstituicao = Validar.converterParaData(vo.getDataConstituicao());
		objeto.setDataConstituicao(Validar.converterParaDateTimeDB(dataConstituicao));
		Date dataRegistroJuntaComercial = Validar.converterParaData(vo.getDataRegistroJuntaComercial());
		objeto.setDataRegistroJuntaComercial(Validar.converterParaDateTimeDB(dataRegistroJuntaComercial));
		objeto.setIdInstituicao(vo.getInstituicao());
		objeto.setNomeRazaoSocial(vo.getNomePessoa());
		objeto.setNomeRazaoSocialCompleto(vo.getNomeCompleto());
		objeto.setNumeroRegistroJuntaComercial(vo.getNumeroRegistroJuntaComercial());
		objeto.setDescLinhaArquivo(vo.getLinhaArquivo());
		objeto.setNumeroCpfCnpj(vo.getCpfCnpj());
		objeto.setCodSituacaoProcessamento(SituacaoProcessamentoEnum.A_INICIAR.getIdSituacao());
		objeto.setCodigoTipoPessoa(vo.getCodigoTipoPessoa());
		objeto.setIdInstituicao(vo.getInstituicao());
		objeto.setDataHoraProcessamento(null);
		objeto.setImportaArquivo(importaArquivo);
		objeto.setCodigoTipoFormaConstituicao(vo.getCodigoTipoFormaConstituicao());

		servico.incluir(objeto);
	}

}
