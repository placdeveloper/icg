package br.com.sicoob.icg.cadastro.fachada;

import java.util.List;
import java.util.Map;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.negocio.contexto.InformacoesUsuario;
import br.com.bancoob.sisbrweb.dto.RequisicaoReqDTO;
import br.com.bancoob.sisbrweb.dto.RetornoDTO;
import br.com.bancoob.sisbrweb.fachada.BancoobFachada;
import br.com.bancoob.sisbrweb.seguranca.RemoteService;
import br.com.sicoob.icg.comum.negocio.delegates.ICGCadastroFabricaDelegates;
import br.com.sicoob.icg.comum.negocio.delegates.ImportaArquivoDelegate;
import br.com.sicoob.icg.negocio.entidades.ImportaArquivo;
import br.com.sicoob.icg.negocio.entidades.vo.ErroProcessamentoVO;
import br.com.sicoob.icg.negocio.enums.SituacaoProcessamentoEnum;
import br.com.sicoob.icg.negocio.enums.TipoAtualizacaoEnum;

@RemoteService
public class ImportaArquivoFachada extends BancoobFachada {

	private static final String IMPORTAARQUIVO = "importacaoArquivoVO";

	private static final String MENSAGEM = "mensagem"; 
	private static final String TIPOMENSAGEM = "tipoMensagem"; 
	
	private ImportaArquivoDelegate delegateImportaArquivo = ICGCadastroFabricaDelegates.getInstance()
			.criarImportaArquivoDelegate();

	protected ImportaArquivo obterEntidade(RequisicaoReqDTO dto) {
		return (ImportaArquivo) dto.getDados().get(IMPORTAARQUIVO);
	}

	public RetornoDTO obterDefinicoes(RequisicaoReqDTO dto) throws BancoobException {
		RetornoDTO retorno = new RetornoDTO();
		Map<String, Object> dados = retorno.getDados();
		dados.put("listaArquivosImportados", listaArquivosImportados(dto));
		dados.put("listaTipoAtualizacao", listarTipoAtualizacao());
		dados.put("listaSituacoProcessamento", listarSituacaoProcessamento());

		return retorno;
	}

	public RetornoDTO retornoListaArquivosImportados(RequisicaoReqDTO dto) throws BancoobException {
		RetornoDTO retorno = new RetornoDTO();
		Map<String, Object> dados = retorno.getDados();
		dados.put("listaArquivosImportados", listaArquivosImportados(dto));
		return retorno;
	}

	private List<ImportaArquivo> listaArquivosImportados(RequisicaoReqDTO dto) throws BancoobException {
		ImportaArquivo importaArquivo = obterEntidade(dto);
		return delegateImportaArquivo.listaArquivosImportados(importaArquivo);
	}

	private Object[] listarTipoAtualizacao() {
		return TipoAtualizacaoEnum.values();
	}

	private Object[] listarSituacaoProcessamento() {
		return SituacaoProcessamentoEnum.values();
	}

	public RetornoDTO importarArquivo(RequisicaoReqDTO dto) throws BancoobException {
		RetornoDTO retorno = new RetornoDTO();
		try {
			ImportaArquivo importaArquivo = obterEntidade(dto);
			importaArquivo.setCodigoSituacaoProcessamento(SituacaoProcessamentoEnum.A_INICIAR.getIdSituacao());
			importaArquivo.setIdInstituicao(Integer.parseInt(InformacoesUsuario.getInstance().getIdInstituicao()));
			importaArquivo = delegateImportaArquivo.incluir(importaArquivo);

			retorno.getDados().put("importaArquivo", importaArquivo);
			retorno.getDados().put(TIPOMENSAGEM, "SUCESSO");
			retorno.getDados().put(MENSAGEM, "Arquivo enviado para processamento.");
		} catch (BancoobException e) {
			retorno.getDados().put(TIPOMENSAGEM, "ERROR");
			retorno.getDados().put(MENSAGEM, e.getMessage());
		}

		return retorno;
	}

	public RetornoDTO excluirArquivoImportado(RequisicaoReqDTO dto) throws BancoobException {
		RetornoDTO retorno = new RetornoDTO();
		try {
			ImportaArquivo importaArquivo = obterEntidade(dto);
			delegateImportaArquivo.excluir(importaArquivo.getIdImportaArquivo());
			retorno.getDados().put(TIPOMENSAGEM, "EXCLUIDO_SUCESSO");
			retorno.getDados().put(MENSAGEM, "Arquivo excluído com sucesso.");
		} catch (BancoobException e) {
			retorno.getDados().put(TIPOMENSAGEM, "ERROR");
			retorno.getDados().put(MENSAGEM, e.getMessage());
		}

		return retorno;
	}

	public RetornoDTO retornoListaErrosProcessamento(RequisicaoReqDTO dto) throws BancoobException {
		RetornoDTO retorno = new RetornoDTO();
		Map<String, Object> dados = retorno.getDados();
		dados.put("listaErrosProcessamento", listaErroProcessamento(dto));

		return retorno;
	}

	private List<ErroProcessamentoVO> listaErroProcessamento(RequisicaoReqDTO dto) throws BancoobException {
		ImportaArquivo importaArquivo = obterEntidade(dto);
		return delegateImportaArquivo.listaErroProcessamento(importaArquivo);
	}

}
