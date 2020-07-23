package br.com.sicoob.icg.comum.persistencia.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.bancoob.negocio.entidades.BancoobEntidade;
import br.com.bancoob.persistencia.dao.BancoobCrudDao;
import br.com.bancoob.persistencia.dao.BancoobCrudDaoIF;
import br.com.bancoob.persistencia.excecao.PersistenciaException;
import br.com.sicoob.icg.comum.persistencia.ICGComumDatasource;
import br.com.sicoob.icg.comum.util.Constantes;

public abstract class ICGComumCrudDao<T extends BancoobEntidade> extends BancoobCrudDao<T>
		implements BancoobCrudDaoIF<T> {

	public ICGComumCrudDao(Class<T> classe, String nomeComandoPesquisar) {
		this(Constantes.DATASOURCE_ICG, Constantes.ARQUIVO_QUERIES, Constantes.NOME_COLECAO_COMANDOS,
				classe, nomeComandoPesquisar);
		this.classe = classe;
	}

	public ICGComumCrudDao(String datasource, String arquivoQueries, String nomeColecaoComandos, Class<T> classe,
			String nomeComandoPesquisar) {
		super(datasource, arquivoQueries, nomeColecaoComandos, (Class<T>) classe, nomeComandoPesquisar);
		this.classe = classe;
		this.nomeComandoPesquisar = nomeComandoPesquisar;
	}

	private Class<T> classe;

	private String nomeComandoPesquisar;

	@Override
	protected Connection estabelecerConexao() {
		try {
			ICGComumDatasource datasource = new ICGComumDatasource(getNomeDatasource(), System.getProperties());
			return datasource.getConnection();
		} catch (SQLException excecao) {
			throw new PersistenciaException(excecao);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EntityManager getEntityManager() {
		EntityManager entityManager = super.getEntityManager();
		return entityManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PersistenceContext(unitName = "emImportaCadastro")
	public void setEntityManager(EntityManager manager) {
		super.setEntityManager(manager);
	}

	protected Class<T> getClasse() {
		return classe;
	}

	protected void fecharResultSet(ResultSet rset) {
		if (rset != null) {
			try {
				rset.close();
			} catch (SQLException e) {
				getLogger().erro(e, "Falha no fechamento do ResultSet");
			}
		}
	}

	protected String getNomeComandoPesquisar() {
		return nomeComandoPesquisar;
	}

}