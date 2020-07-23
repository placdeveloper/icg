package br.com.sicoob.icg.negocio.entidades.vo;

import br.com.bancoob.negocio.vo.BancoobVo;

/**
 * A Classe DownloadDominioVO.
 */
public class DownloadDominioVO extends BancoobVo {
	
	/** A constante serialVersionUID. */
	private static final long serialVersionUID = -4284840137582469701L;

	/** O atributo nome. */
	private String nome;
	
	/** O atributo bytes. */
	private byte[] bytes;

	/**
	 * Recupera o valor de nome.
	 *
	 * @return o valor de nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Define o valor de nome.
	 *
	 * @param nome o novo valor de nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Recupera o valor de bytes.
	 *
	 * @return o valor de bytes
	 */
	public byte[] getBytes() {
		return bytes.clone();
	}

	/**
	 * Define o valor de bytes.
	 *
	 * @param bytes o novo valor de bytes
	 */
	public void setBytes(byte[] bytes) {
		this.bytes = bytes.clone();
	}

}