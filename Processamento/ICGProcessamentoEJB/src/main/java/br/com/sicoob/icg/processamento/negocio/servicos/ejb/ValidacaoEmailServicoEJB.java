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

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.util.MensagemUtil;
import br.com.sicoob.icg.comum.negocio.servicos.EmailPessoaImportacaoServico;
import br.com.sicoob.icg.negocio.entidades.EmailPessoaImportacao;
import br.com.sicoob.icg.negocio.entidades.ImportaArquivo;
import br.com.sicoob.icg.negocio.entidades.vo.EmailVO;
import br.com.sicoob.icg.negocio.enums.SituacaoProcessamentoEnum;
import br.com.sicoob.icg.processamento.negocio.entidades.vo.ValidacaoVO;
import br.com.sicoob.icg.processamento.negocio.enums.ConstanteEmailEnum;
import br.com.sicoob.icg.processamento.negocio.mensagem.ICGProcessamentoResourceBundle;
import br.com.sicoob.icg.processamento.negocio.servicos.ValidacaoEmailServico;
import br.com.sicoob.icg.processamento.negocio.validacao.util.Validar;
import br.com.sicoob.icg.processamento.negocio.validacao.util.ValidarEmailUtil;
import br.com.sicoob.icg.processamento.util.Constantes;

@Stateless
@Local(ValidacaoEmailServico.class)
public class ValidacaoEmailServicoEJB extends ValidacaoServicoEJB implements ValidacaoEmailServico {

	@EJB
	private EmailPessoaImportacaoServico servico;

	protected ValidacaoVO validarJson(ImportaArquivo importaArquivo, Path diretorioArquivo) {
		ValidacaoVO validacao = new ValidacaoVO();
		try (Reader reader = new FileReader(diretorioArquivo.toFile())) {

			Gson gson = new Gson();
			Type collectionType = new TypeToken<List<EmailVO>>() {
			}.getType();
			List<EmailVO> lista = gson.fromJson(reader, collectionType);

			AtomicInteger index = new AtomicInteger();
			index.getAndIncrement();
			for (EmailVO vo : lista) {

				vo.setInstituicao(importaArquivo.getIdInstituicao());
				vo.setLinhaArquivo("OBJETO: " + index.getAndIncrement());

				ValidacaoVO validacaoItem = ValidarEmailUtil.validar(vo);

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
						ConstanteEmailEnum.QTD_CAMPOS_OBRIGARIOS.getCodigo())) {

					String s[] = l.split("\\|");
					EmailVO vo = montarEmailVO(s, importaArquivo);
					vo.setLinhaArquivo("LINHA: " + indice);

					ValidacaoVO validacaoItem = null;
					try {
						validacaoItem = ValidarEmailUtil.validar(vo);
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
									ConstanteEmailEnum.QTD_CAMPOS_OBRIGARIOS.getCodigo()));
				}
			});
			return validacao;

		} catch (IOException e) {
			validacao.setErroValidacao(new StringBuilder().append(e.getMessage()));
			return validacao;
		}
	}

	private EmailVO montarEmailVO(String s[], ImportaArquivo importaArquivo) {
		EmailVO vo = new EmailVO();
		vo.setInstituicao(importaArquivo.getIdInstituicao());
		vo.setCpfCnpj(s[ConstanteEmailEnum.POSICAO_CAMPO_CPFCNPJ.getCodigo()].trim() == null ? ""
				: s[ConstanteEmailEnum.POSICAO_CAMPO_CPFCNPJ.getCodigo()].trim());
		vo.setCodigoTipoPessoa(s[ConstanteEmailEnum.POSICAO_CAMPO_CODTIPOPESSOA.getCodigo()].trim() == null ? ""
				: s[ConstanteEmailEnum.POSICAO_CAMPO_CODTIPOPESSOA.getCodigo()].trim());
		vo.setCodigoTipoEmail(s[ConstanteEmailEnum.POSICAO_CAMPO_CODTIPOEMAIL.getCodigo()].trim() == null ? ""
				: s[ConstanteEmailEnum.POSICAO_CAMPO_CODTIPOEMAIL.getCodigo()].trim());
		vo.setEmail(s[ConstanteEmailEnum.POSICAO_CAMPO_DESCEMAIL.getCodigo()].trim() == null ? ""
				: s[ConstanteEmailEnum.POSICAO_CAMPO_DESCEMAIL.getCodigo()].trim());

		return vo;
	}

	private void cadastraRegistroValido(ImportaArquivo importaArquivo, EmailVO vo, ValidacaoVO validacaoItem,
			ValidacaoVO validacao) {
		if (validacaoItem.getRegistroValido()) {
			try {
				if (Validar.verificaCadastroCPFCNPJ(vo.getCpfCnpj(), importaArquivo.getIdInstituicao())) {
					incluirEmail(importaArquivo, vo);
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

	private void incluirEmail(ImportaArquivo importaArquivo, EmailVO vo) throws BancoobException {
		EmailPessoaImportacao objeto = new EmailPessoaImportacao();
		objeto.setCodigoTipoEmail(vo.getCodigoTipoEmail());
		objeto.setDescLinhaArquivo(vo.getLinhaArquivo());
		objeto.setDescricaoEmail(vo.getEmail());
		objeto.setNumeroCpfCnpj(vo.getCpfCnpj());
		objeto.setCodSituacaoProcessamento(SituacaoProcessamentoEnum.A_INICIAR.getIdSituacao());
		objeto.setCodigoTipoPessoa(vo.getCodigoTipoPessoa());
		objeto.setIdInstituicao(vo.getInstituicao());
		objeto.setDataHoraProcessamento(null);
		objeto.setImportaArquivo(importaArquivo);

		servico.incluir(objeto);
	}

}
