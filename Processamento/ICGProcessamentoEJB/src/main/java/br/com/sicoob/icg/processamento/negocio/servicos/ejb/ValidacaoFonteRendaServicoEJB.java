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
import br.com.sicoob.icg.comum.negocio.servicos.FonteRendaImportacaoServico;
import br.com.sicoob.icg.negocio.entidades.FonteRendaImportacao;
import br.com.sicoob.icg.negocio.entidades.ImportaArquivo;
import br.com.sicoob.icg.negocio.entidades.vo.FonteRendaVO;
import br.com.sicoob.icg.negocio.enums.SituacaoProcessamentoEnum;
import br.com.sicoob.icg.processamento.negocio.entidades.vo.ValidacaoVO;
import br.com.sicoob.icg.processamento.negocio.enums.ConstanteFonteRendaEnum;
import br.com.sicoob.icg.processamento.negocio.mensagem.ICGProcessamentoResourceBundle;
import br.com.sicoob.icg.processamento.negocio.servicos.ValidacaoFonteRendaServico;
import br.com.sicoob.icg.processamento.negocio.validacao.util.Validar;
import br.com.sicoob.icg.processamento.negocio.validacao.util.ValidarFonteRendaUtil;
import br.com.sicoob.icg.processamento.util.Constantes;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Local(ValidacaoFonteRendaServico.class)
public class ValidacaoFonteRendaServicoEJB extends ValidacaoServicoEJB implements ValidacaoFonteRendaServico {

	@EJB
	private FonteRendaImportacaoServico servico;

	protected ValidacaoVO validarJson(ImportaArquivo importaArquivo, Path diretorioArquivo) {
		ValidacaoVO validacao = new ValidacaoVO();
		try (Reader reader = new FileReader(diretorioArquivo.toFile())) {

			Gson gson = new Gson();
			Type collectionType = new TypeToken<List<FonteRendaVO>>() {
			}.getType();
			List<FonteRendaVO> lista = gson.fromJson(reader, collectionType);

			AtomicInteger index = new AtomicInteger();
			index.getAndIncrement();
			for (FonteRendaVO vo : lista) {

				vo.setInstituicao(importaArquivo.getIdInstituicao());
				vo.setLinhaArquivo("OBJETO: " + index.getAndIncrement());

				ValidacaoVO validacaoItem = ValidarFonteRendaUtil.validar(vo);

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
						ConstanteFonteRendaEnum.QTD_CAMPOS_OBRIGATORIOS.getCodigo())) {

					String s[] = l.split("\\|");
					FonteRendaVO vo = montarFonteRendaVO(s, importaArquivo);
					vo.setLinhaArquivo("LINHA: " + indice);

					ValidacaoVO validacaoItem = null;
					try {
						validacaoItem = ValidarFonteRendaUtil.validar(vo);
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
									ConstanteFonteRendaEnum.QTD_CAMPOS_OBRIGATORIOS.getCodigo()));
				}
			});
			return validacao;

		} catch (IOException e) {
			validacao.setErroValidacao(new StringBuilder().append(e.getMessage()));
			return validacao;
		}
	}

	private FonteRendaVO montarFonteRendaVO(String s[], ImportaArquivo importaArquivo) {
		FonteRendaVO vo = new FonteRendaVO();

		vo.setInstituicao(importaArquivo.getIdInstituicao());
		vo.setCpfCnpj(s[ConstanteFonteRendaEnum.POSICAO_CAMPO_CPFCNPJ.getCodigo()].trim() == null ? ""
				: s[ConstanteFonteRendaEnum.POSICAO_CAMPO_CPFCNPJ.getCodigo()].trim());
		vo.setCodigoTipoPessoa(s[ConstanteFonteRendaEnum.POSICAO_CAMPO_CODTIPOPESSOA.getCodigo()].trim() == null ? ""
				: s[ConstanteFonteRendaEnum.POSICAO_CAMPO_CODTIPOPESSOA.getCodigo()].trim());
		vo.setCodigoTipoFonteRenda(
				s[ConstanteFonteRendaEnum.POSICAO_CAMPO_CODTIPOFONTERENDA.getCodigo()].trim() == null ? ""
						: s[ConstanteFonteRendaEnum.POSICAO_CAMPO_CODTIPOFONTERENDA.getCodigo()].trim());
		vo.setRendaFixa(s[ConstanteFonteRendaEnum.POSICAO_CAMPO_BOLRENDAFIXA.getCodigo()].trim() == null ? ""
				: s[ConstanteFonteRendaEnum.POSICAO_CAMPO_BOLRENDAFIXA.getCodigo()].trim());
		vo.setBolSimplesNacional(
				s[ConstanteFonteRendaEnum.POSICAO_CAMPO_BOLSIMPLESNACIONAL.getCodigo()].trim() == null ? ""
						: s[ConstanteFonteRendaEnum.POSICAO_CAMPO_BOLSIMPLESNACIONAL.getCodigo()].trim());
		vo.setBolSimplesNacional(
				s[ConstanteFonteRendaEnum.POSICAO_CAMPO_BOLSIMPLESNACIONAL.getCodigo()].trim() == null ? ""
						: s[ConstanteFonteRendaEnum.POSICAO_CAMPO_BOLSIMPLESNACIONAL.getCodigo()].trim());
		vo.setDataValidadeRenda(
				s[ConstanteFonteRendaEnum.POSICAO_CAMPO_DATAVALIDADERENDA.getCodigo()].trim() == null ? ""
						: s[ConstanteFonteRendaEnum.POSICAO_CAMPO_DATAVALIDADERENDA.getCodigo()].trim());
		vo.setValorReceitaBrutaMensal(
				s[ConstanteFonteRendaEnum.POSICAO_CAMPO_VALORRECEITABRUTAMENSAL.getCodigo()].trim() == null ? ""
						: s[ConstanteFonteRendaEnum.POSICAO_CAMPO_VALORRECEITABRUTAMENSAL.getCodigo()].trim());

		return vo;
	}

	private void cadastraRegistroValido(ImportaArquivo importaArquivo, FonteRendaVO vo, ValidacaoVO validacaoItem,
			ValidacaoVO validacao) {
		if (validacaoItem.getRegistroValido()) {
			try {
				if (Validar.verificaCadastroCPFCNPJ(vo.getCpfCnpj(), importaArquivo.getIdInstituicao())) {
					incluirFonteRenda(importaArquivo, vo);
					validacao.setHouveInclusao(true);
				} else {
					validacao.getErroValidacao().append(MensagemUtil.getString(Constantes.MENSAGEM_MN004,
							ICGProcessamentoResourceBundle.getInstance(), vo.getLinhaArquivo(), vo.getCpfCnpj()));
				}

			} catch (BancoobException e) {
				validacaoItem.setErroValidacao(
						new StringBuilder().append(Constantes.MENSAGEM_ERRO_INCLUSAO_DADOS + vo.getLinhaArquivo()));
			}
		}
	}

	private void incluirFonteRenda(ImportaArquivo importaArquivo, FonteRendaVO vo) throws BancoobException {
		FonteRendaImportacao objeto = new FonteRendaImportacao();
		objeto.setCodigoTipoFonteRenda(vo.getCodigoTipoFonteRenda());
		Date dataValidadeRenda = Validar.converterParaData(vo.getDataValidadeRenda());
		objeto.setDataValidadeRenda(Validar.converterParaDateTimeDB(dataValidadeRenda));
		objeto.setRendaFixa(Validar.converterParaLong(vo.getRendaFixa()));
		objeto.setSimplesNacional(Validar.converterParaLong(vo.getBolSimplesNacional()));
		objeto.setValorReceitaBrutaMensal(Validar.converterParaBigDecimal(vo.getValorReceitaBrutaMensal()));
		objeto.setCodSituacaoProcessamento(vo.getSituacaoProcessamento());
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
