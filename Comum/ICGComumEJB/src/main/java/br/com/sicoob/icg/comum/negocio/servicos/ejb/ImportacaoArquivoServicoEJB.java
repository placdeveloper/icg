package br.com.sicoob.icg.comum.negocio.servicos.ejb;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.negocio.dto.ConsultaDto;
import br.com.bancoob.persistencia.dao.BancoobCrudDaoIF;
import br.com.bancoob.persistencia.types.DateTimeDB;
import br.com.sicoob.icg.comum.negocio.servicos.interfaces.ImportacaoArquivoServicoLocal;
import br.com.sicoob.icg.comum.negocio.servicos.interfaces.ImportacaoArquivoServicoRemote;
import br.com.sicoob.icg.comum.persistencia.dao.ImportaArquivoDAO;
import br.com.sicoob.icg.negocio.entidades.ImportaArquivo;
import br.com.sicoob.icg.negocio.entidades.vo.ErroProcessamentoVO;
import br.com.sicoob.icg.negocio.enums.SituacaoProcessamentoEnum;
import br.com.sicoob.icg.negocio.enums.TipoAtualizacaoEnum;

@Stateless
@Local({ ImportacaoArquivoServicoLocal.class })
@Remote({ ImportacaoArquivoServicoRemote.class })
public class ImportacaoArquivoServicoEJB extends ICGCrudComumServicoEJB<ImportaArquivo>
		implements ImportacaoArquivoServicoLocal, ImportacaoArquivoServicoRemote {

	@Inject
	@Default
	private ImportaArquivoDAO dao;

	@Override
	public List<ImportaArquivo> listaArquivosImportados(ImportaArquivo criterios) throws BancoobException {
		return dao.listaArquivosImportados(criterios);
	}

	@Override
	public ImportaArquivo incluir(ImportaArquivo objeto) throws BancoobException {
		objeto.setDataImportacao(new DateTimeDB());
		return super.incluir(objeto);
	}

	@Override
	public void excluir(Serializable chave) throws BancoobException {
		ImportaArquivo importaArquivo = obter(chave);
		super.excluir(chave);
		removerArquivos(importaArquivo);
	}

	/**
	 * 
	 * @param importaArquivo
	 */
	public void removerArquivos(ImportaArquivo importaArquivo) {
		File f = new File(importaArquivo.getDiretorioArquivo());
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (File file : files) {
				if (file.getName().equals(importaArquivo.getNomeArquivoDiretorio())) {
					excluirArquivo(file);
				}
			}
		}
	}

	/**
	 * 
	 * @param file
	 */
	private void excluirArquivo(File file) {
		file.delete();
	}

	@Override
	public List<ErroProcessamentoVO> listaErroProcessamento(ImportaArquivo criterios) throws BancoobException {
		return dao.listaErroProcessamento(criterios);
	}

	@Override
	protected BancoobCrudDaoIF<ImportaArquivo> getDAO() {
		return dao;
	}

	@Override
	public List<ImportaArquivo> listarArquivosImportadosNaoIniciados(TipoAtualizacaoEnum tipoArquivo)
			throws BancoobException {
		ConsultaDto<ImportaArquivo> criterios = new ConsultaDto<>();
		ImportaArquivo filtro = new ImportaArquivo();
		filtro.setSituacaoProcessamentoEnum(SituacaoProcessamentoEnum.A_INICIAR);
		filtro.setTipoAtualizacaoEnum(tipoArquivo);
		criterios.setFiltro(filtro);
		return listar(criterios);
	}

	@Override
	public List<ImportaArquivo> listarArquivosImportadosNaoIniciados() throws BancoobException {
		return listarArquivosImportadosNaoIniciados(null);
	}

	@Override
	public List<ImportaArquivo> listarArquivosImportadosPendentesProcessamento() throws BancoobException {
		ConsultaDto<ImportaArquivo> criterios = new ConsultaDto<>();
		ImportaArquivo filtro = new ImportaArquivo();
		List<String> listaSituacao = new ArrayList<>();
		listaSituacao.add(SituacaoProcessamentoEnum.EM_PROCESSAMENTO.getIdSituacao());
		listaSituacao.add(SituacaoProcessamentoEnum.VALIDADO_PARCIALMENTE.getIdSituacao());
		listaSituacao.add(SituacaoProcessamentoEnum.ARQUIVO_VALIDADO.getIdSituacao());
		filtro.setListaCodigoSituacaoProcessamento(listaSituacao);
		criterios.setFiltro(filtro);
		return listar(criterios);
	}
}
