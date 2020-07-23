package br.com.sicoob.icg.processamento.negocio.servicos.ejb;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.util.MensagemUtil;
import br.com.sicoob.icg.comum.negocio.servicos.TelefoneImportacaoServico;
import br.com.sicoob.icg.negocio.entidades.ImportaArquivo;
import br.com.sicoob.icg.negocio.entidades.TelefonePessoaImportacao;
import br.com.sicoob.icg.negocio.entidades.vo.TelefoneVO;
import br.com.sicoob.icg.negocio.enums.SituacaoProcessamentoEnum;
import br.com.sicoob.icg.processamento.negocio.entidades.vo.ValidacaoVO;
import br.com.sicoob.icg.processamento.negocio.enums.ConstanteTelefoneEnum;
import br.com.sicoob.icg.processamento.negocio.mensagem.ICGProcessamentoResourceBundle;
import br.com.sicoob.icg.processamento.negocio.servicos.ValidacaoTelefoneServico;
import br.com.sicoob.icg.processamento.negocio.validacao.util.Validar;
import br.com.sicoob.icg.processamento.negocio.validacao.util.ValidarTelefoneUtil;
import br.com.sicoob.icg.processamento.util.Constantes;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Local(ValidacaoTelefoneServico.class)
public class ValidacaoTelefoneServicoEJB extends ValidacaoServicoEJB implements ValidacaoTelefoneServico {

	@Inject
	@Default
	private TelefoneImportacaoServico servico;

	protected ValidacaoVO validarJson(ImportaArquivo importaArquivo, Path diretorioArquivo) {
		ValidacaoVO validacao = new ValidacaoVO();
		try (Reader reader = new FileReader(diretorioArquivo.toFile())) {

			Gson gson = new Gson();
			Type collectionType = new TypeToken<List<TelefoneVO>>() {
			}.getType();
			List<TelefoneVO> lista = gson.fromJson(reader, collectionType);

			AtomicInteger index = new AtomicInteger();
			index.getAndIncrement();
			for (TelefoneVO vo : lista) {

				vo.setInstituicao(importaArquivo.getIdInstituicao());
				vo.setLinhaArquivo("OBJETO: " + index.getAndIncrement());

				ValidacaoVO validacaoItem = ValidarTelefoneUtil.validar(vo);

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
						ConstanteTelefoneEnum.QTD_CAMPOS_OBRIGATORIOS.getCodigo())) {

					String s[] = l.split("\\|");
					TelefoneVO vo = montarTelefoneVO(s, importaArquivo);
					vo.setLinhaArquivo("LINHA: " + indice);

					ValidacaoVO validacaoItem = null;
					try {
						validacaoItem = ValidarTelefoneUtil.validar(vo);
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
									ConstanteTelefoneEnum.QTD_CAMPOS_OBRIGATORIOS.getCodigo()));

				}
			});
			return validacao;

		} catch (IOException e) {
			validacao.setErroValidacao(new StringBuilder().append(e.getMessage()));
			return validacao;
		}
	}

	private TelefoneVO montarTelefoneVO(String s[], ImportaArquivo importaArquivo) {
		TelefoneVO vo = new TelefoneVO();
		vo.setInstituicao(importaArquivo.getIdInstituicao());
		vo.setCpfCnpj(s[ConstanteTelefoneEnum.POSICAO_CAMPO_CPFCNPJ.getCodigo()].trim() == null ? ""
				: s[ConstanteTelefoneEnum.POSICAO_CAMPO_CPFCNPJ.getCodigo()].trim());
		vo.setCodigoTipoPessoa(s[ConstanteTelefoneEnum.POSICAO_CAMPO_CODTIPOPESSOA.getCodigo()].trim() == null ? ""
				: s[ConstanteTelefoneEnum.POSICAO_CAMPO_CODTIPOPESSOA.getCodigo()].trim());
		vo.setCodigoTipoTelefone(s[ConstanteTelefoneEnum.POSICAO_CAMPO_CODTIPOTELEFONE.getCodigo()].trim() == null ? ""
				: s[ConstanteTelefoneEnum.POSICAO_CAMPO_CODTIPOTELEFONE.getCodigo()].trim());
		vo.setDdd(s[ConstanteTelefoneEnum.POSICAO_CAMPO_NUMDDD.getCodigo()].trim() == null ? ""
				: s[ConstanteTelefoneEnum.POSICAO_CAMPO_NUMDDD.getCodigo()].trim());
		vo.setTelefone(s[ConstanteTelefoneEnum.POSICAO_CAMPO_NUMTELEFONE.getCodigo()].trim() == null ? ""
				: s[ConstanteTelefoneEnum.POSICAO_CAMPO_NUMTELEFONE.getCodigo()].trim());
		vo.setRamal(s[ConstanteTelefoneEnum.POSICAO_CAMPO_NUMRAMAL.getCodigo()].trim() == null ? ""
				: s[ConstanteTelefoneEnum.POSICAO_CAMPO_NUMRAMAL.getCodigo()].trim());
		vo.setDescObservacao(s[ConstanteTelefoneEnum.POSICAO_CAMPO_DESCOBSERVACAO.getCodigo()].trim() == null ? ""
				: s[ConstanteTelefoneEnum.POSICAO_CAMPO_DESCOBSERVACAO.getCodigo()].trim());

		return vo;

	}

	private void cadastraRegistroValido(ImportaArquivo importaArquivo, TelefoneVO vo, ValidacaoVO validacaoItem,
			ValidacaoVO validacao) {
		if (validacaoItem.getRegistroValido()) {
			try {
				if (Validar.verificaCadastroCPFCNPJ(vo.getCpfCnpj(), importaArquivo.getIdInstituicao())) {
					incluirTelefone(importaArquivo, vo);
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

	private void incluirTelefone(ImportaArquivo importaArquivo, TelefoneVO vo) throws BancoobException {
		TelefonePessoaImportacao objeto = new TelefonePessoaImportacao();
		objeto.setCodigoTipoTelefone(Validar.converterParaLong(vo.getCodigoTipoTelefone()));
		objeto.setDescricaoObservacao(vo.getDescObservacao());
		objeto.setNumeroDdd(vo.getDdd());
		objeto.setNumeroRamal(vo.getRamal());
		objeto.setNumeroTelefone(vo.getTelefone());
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
