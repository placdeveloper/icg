package br.com.sicoob.icg.comum.persistencia.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.negocio.contexto.InformacoesUsuario;
import br.com.bancoob.persistencia.comandos.Comando;
import br.com.bancoob.persistencia.excecao.PersistenciaException;
import br.com.bancoob.persistencia.types.DateTimeDB;
import br.com.sicoob.icg.comum.persistencia.dao.ImportaArquivoDAO;
import br.com.sicoob.icg.negocio.entidades.ImportaArquivo;
import br.com.sicoob.icg.negocio.entidades.vo.ErroProcessamentoVO;
import br.com.sicoob.icg.negocio.enums.SituacaoProcessamentoEnum;
import br.com.sicoob.icg.negocio.enums.TipoAtualizacaoEnum;

/**
 * 
 * @author Pablo.Andrade
 *
 */
public class ImportaArquivoDAOImpl extends ICGComumCrudDao<ImportaArquivo> implements ImportaArquivoDAO {

	/**
	 * contrutor padrão
	 */
	public ImportaArquivoDAOImpl() {
		super(ImportaArquivo.class, "PESQUISAR_IMPORTA_ARQUIVO");
	}

	public List<ImportaArquivo> listaArquivosImportados(ImportaArquivo criterios) {
		Connection conx = null;
		Comando comando = null;
		ResultSet rset = null;
		List<ImportaArquivo> listaRetorno = new ArrayList<ImportaArquivo>();

		try {
			conx = estabelecerConexao();
			comando = getComando("LISTA_TODOS_ARQUIVOS_IMPORTADOS");
			comando.adicionarVariavel("idInstituicao", InformacoesUsuario.getInstance().getIdInstituicao());
			if (Objects.nonNull(criterios)) {
				comando.adicionarVariavel("nomeArquivo", criterios.getNomeArquivo());
				comando.adicionarVariavel("codigoTipoArquivo", criterios.getCodigoTipoArquivo());
				comando.adicionarVariavel("situacaoProcessamento", criterios.getCodigoSituacaoProcessamento());
				comando.adicionarVariavel("dataInicio", criterios.getDataHoraImportacaoFiltroInicio());
				comando.adicionarVariavel("dataFim", criterios.getDataHoraImportacaoFiltroFim());
			}
			comando.configurar();

			rset = comando.executarConsulta(conx);
			while (rset.next()) {
				ImportaArquivo importacaoArquivo = new ImportaArquivo();
				importacaoArquivo.setDataImportacao(new DateTimeDB(rset.getDate("DATAHORAIMPORTACAO").getTime()));
				importacaoArquivo.setDescricaoErro(rset.getString("DESCERROPROCESSSAMENTO"));
				importacaoArquivo.setDiretorioArquivo(rset.getString("DESCDIRETORIO"));
				importacaoArquivo.setIdImportaArquivo(rset.getLong("IDIMPORTAARQUIVO"));
				importacaoArquivo.setIdInstituicao(rset.getInt("IDINSTITUICAO"));
				importacaoArquivo.setNomeArquivo(rset.getString("NOMEARQUIVO"));
				importacaoArquivo.setNomeArquivoDiretorio(rset.getString("NOMEARQUIVODIRETORIO"));
				importacaoArquivo.setSituacaoProcessamentoEnum(
						SituacaoProcessamentoEnum.getSituacaoPeloID(rset.getString("CODSITUACAOPROCESSAMENTO")));
				importacaoArquivo.setTipoAtualizacaoEnum(
						TipoAtualizacaoEnum.getTipoAtualizacaoPeloID(rset.getString("CODTIPOARQUIVOIMPORTACAO")));
				importacaoArquivo.setUsuarioResponsavel(rset.getString("IDUSUARIO"));
				listaRetorno.add(importacaoArquivo);
			}

		} catch (SQLException excecao) {
			throw new PersistenciaException(excecao);
		} finally {
			fecharComando(comando);
			fecharConexao(conx);
			fecharResultSet(rset);
		}

		return listaRetorno;
	}

	@Override
	public List<ErroProcessamentoVO> listaErroProcessamento(ImportaArquivo criterios) throws BancoobException {
		Connection conx = null;
		Comando comando = null;
		ResultSet rset = null;
		List<ErroProcessamentoVO> listaRetorno = new ArrayList<ErroProcessamentoVO>();

		try {
			conx = estabelecerConexao();
			comando = getComando("LISTA_ERROS_PROCESSAMENTO");
			if (Objects.nonNull(criterios)) {
				comando.adicionarVariavel("situacaoProcessamento",
						SituacaoProcessamentoEnum.ERRO_PROCESSAMENTO.getIdSituacao());
				comando.adicionarVariavel("codigoTipoArquivo", criterios.getCodigoTipoArquivo());
				comando.adicionarVariavel("idImportaArquivo", criterios.getIdImportaArquivo());
			}
			comando.configurar();

			rset = comando.executarConsulta(conx);
			while (rset.next()) {
				ErroProcessamentoVO ErroProcessamentoVO = new ErroProcessamentoVO();
				ErroProcessamentoVO.setPessoa(rset.getString("NUMCPFCNPJ"));
				ErroProcessamentoVO.setDataProcessamento(new DateTimeDB(rset.getDate("DATAHORAPROCESSADO").getTime()));
				ErroProcessamentoVO.setDescricaoErro(rset.getString("DESCERROPROCESSAMENTO"));
				ErroProcessamentoVO.setSituacaoProcessamento(rset.getString("CODSITUACAOPROCESSAMENTO"));
				ErroProcessamentoVO.setLinhaArquivo(rset.getString("DESCLINHAARQUIVO"));
				listaRetorno.add(ErroProcessamentoVO);
			}

		} catch (SQLException excecao) {
			throw new PersistenciaException(excecao);
		} finally {
			fecharComando(comando);
			fecharConexao(conx);
			fecharResultSet(rset);
		}

		return listaRetorno;
	}
}
