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
import br.com.sicoob.icg.comum.negocio.servicos.EnderecoPessoaImportacaoServico;
import br.com.sicoob.icg.negocio.entidades.EnderecoPessoaImportacao;
import br.com.sicoob.icg.negocio.entidades.ImportaArquivo;
import br.com.sicoob.icg.negocio.entidades.vo.EnderecoVO;
import br.com.sicoob.icg.negocio.enums.SituacaoProcessamentoEnum;
import br.com.sicoob.icg.processamento.negocio.entidades.vo.ValidacaoVO;
import br.com.sicoob.icg.processamento.negocio.enums.ConstanteEnderecoEnum;
import br.com.sicoob.icg.processamento.negocio.mensagem.ICGProcessamentoResourceBundle;
import br.com.sicoob.icg.processamento.negocio.servicos.ValidacaoEnderecoServico;
import br.com.sicoob.icg.processamento.negocio.validacao.util.Validar;
import br.com.sicoob.icg.processamento.negocio.validacao.util.ValidarEnderecoUtil;
import br.com.sicoob.icg.processamento.util.Constantes;

@Stateless
@Local(ValidacaoEnderecoServico.class)
public class ValidacaoEnderecoServicoEJB extends ValidacaoServicoEJB implements ValidacaoEnderecoServico {

	@EJB
	private EnderecoPessoaImportacaoServico servico;

	@Override
	protected ValidacaoVO validarCSV(ImportaArquivo importaArquivo, Path diretorioArquivo) {
		ValidacaoVO validacao = new ValidacaoVO();
		try {
			AtomicInteger index = new AtomicInteger();
			index.getAndIncrement();
			Files.readAllLines(diretorioArquivo, StandardCharsets.ISO_8859_1).stream().forEach(l -> {
				final int indice = index.getAndIncrement();
				int qtdCamposLinha = l.split("\\|").length;
				if (Validar.validaQuantidadeCamposObrigatorios(qtdCamposLinha,
						ConstanteEnderecoEnum.QTD_CAMPOS_OBRIGATORIOS.getCodigo())) {

					String s[] = l.split("\\|");
					EnderecoVO vo = montarEnderecoVO(s, importaArquivo);
					vo.setLinhaArquivo("LINHA: " + indice);

					ValidacaoVO validacaoItem = null;
					try {
						validacaoItem = ValidarEnderecoUtil.validar(vo);
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
									ConstanteEnderecoEnum.QTD_CAMPOS_OBRIGATORIOS.getCodigo()));
				}
			});
			return validacao;

		} catch (IOException e) {
			validacao.setErroValidacao(new StringBuilder().append(e.getMessage()));
			return validacao;
		}
	}

	@Override
	protected ValidacaoVO validarJson(ImportaArquivo importaArquivo, Path diretorioArquivo) {
		ValidacaoVO validacao = new ValidacaoVO();
		try (Reader reader = new FileReader(diretorioArquivo.toFile())) {

			Gson gson = new Gson();
			Type collectionType = new TypeToken<List<EnderecoVO>>() {
			}.getType();
			List<EnderecoVO> lista = gson.fromJson(reader, collectionType);

			AtomicInteger index = new AtomicInteger();
			index.getAndIncrement();
			for (EnderecoVO vo : lista) {

				vo.setInstituicao(importaArquivo.getIdInstituicao());
				vo.setLinhaArquivo("OBJETO: " + index.getAndIncrement());

				ValidacaoVO validacaoItem = ValidarEnderecoUtil.validar(vo);

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

	public EnderecoVO montarEnderecoVO(String s[], ImportaArquivo importaArquivo) {
		EnderecoVO vo = new EnderecoVO();
		vo.setInstituicao(importaArquivo.getIdInstituicao());
		vo.setCpfCnpj(s[ConstanteEnderecoEnum.POSICAO_CAMPO_CPFCNPJ.getCodigo()].trim() == null ? ""
				: s[ConstanteEnderecoEnum.POSICAO_CAMPO_CPFCNPJ.getCodigo()].trim());
		vo.setCodigoTipoPessoa(s[ConstanteEnderecoEnum.POSICAO_CAMPO_CODTIPOPESSOA.getCodigo()].trim() == null ? ""
				: s[ConstanteEnderecoEnum.POSICAO_CAMPO_CODTIPOPESSOA.getCodigo()].trim());
		vo.setCep(s[ConstanteEnderecoEnum.POSICAO_CAMPO_CODCEP.getCodigo()].trim() == null ? ""
				: s[ConstanteEnderecoEnum.POSICAO_CAMPO_CODCEP.getCodigo()].trim());
		vo.setCodigoTipoEndereco(s[ConstanteEnderecoEnum.POSICAO_CAMPO_CODTIPOENDERECO.getCodigo()].trim() == null ? ""
				: s[ConstanteEnderecoEnum.POSICAO_CAMPO_CODTIPOENDERECO.getCodigo()].trim());
		vo.setBairro(s[ConstanteEnderecoEnum.POSICAO_CAMPO_NOMEBAIRRO.getCodigo()].trim() == null ? ""
				: s[ConstanteEnderecoEnum.POSICAO_CAMPO_NOMEBAIRRO.getCodigo()].trim());
		vo.setLogradouro(s[ConstanteEnderecoEnum.POSICAO_CAMPO_DESCLOGRADOURO.getCodigo()].trim() == null ? ""
				: s[ConstanteEnderecoEnum.POSICAO_CAMPO_DESCLOGRADOURO.getCodigo()].trim());
		vo.setNumero(s[ConstanteEnderecoEnum.POSICAO_CAMPO_DESCNUMERO.getCodigo()].trim() == null ? ""
				: s[ConstanteEnderecoEnum.POSICAO_CAMPO_DESCNUMERO.getCodigo()].trim());
		vo.setComplemento(s[ConstanteEnderecoEnum.POSICAO_CAMPO_DESCCOMPLEMENTO.getCodigo()].trim() == null ? ""
				: s[ConstanteEnderecoEnum.POSICAO_CAMPO_DESCCOMPLEMENTO.getCodigo()].trim());
		vo.setCodigoLocalidade(s[ConstanteEnderecoEnum.POSICAO_CAMPO_CODTIPOLOCALIDADE.getCodigo()].trim() == null ? ""
				: s[ConstanteEnderecoEnum.POSICAO_CAMPO_CODTIPOLOCALIDADE.getCodigo()].trim());
		vo.setCodigoTipoLogradouro(
				s[ConstanteEnderecoEnum.POSICAO_CAMPO_CODTIPOLOGRADOURO.getCodigo()].trim() == null ? ""
						: s[ConstanteEnderecoEnum.POSICAO_CAMPO_CODTIPOLOGRADOURO.getCodigo()].trim());

		return vo;
	}

	private void cadastraRegistroValido(ImportaArquivo importaArquivo, EnderecoVO vo, ValidacaoVO validacaoItem,
			ValidacaoVO validacao) {
		if (validacaoItem.getRegistroValido()) {
			try {
				if (Validar.verificaCadastroCPFCNPJ(vo.getCpfCnpj(), importaArquivo.getIdInstituicao())) {
					incluirEndereco(importaArquivo, vo);
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

	private void incluirEndereco(ImportaArquivo importaArquivo, EnderecoVO vo) throws BancoobException {
		EnderecoPessoaImportacao objeto = new EnderecoPessoaImportacao();
		objeto.setCodigoCep(vo.getCep());
		objeto.setCodigoLocalidade(Validar.converterParaLong(vo.getCodigoLocalidade()));
		objeto.setCodigoTipoEndereco(vo.getCodigoTipoEndereco());
		objeto.setCoodigoTipoLogradouro(Validar.converterParaLong(vo.getCodigoTipoLogradouro()));
		objeto.setDescricaoComplemento(vo.getComplemento());
		objeto.setDescricaoLogradouro(vo.getLogradouro());
		objeto.setNomeBairro(vo.getBairro());
		objeto.setNumero(vo.getNumero());
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
