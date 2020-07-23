package br.com.sicoob.icg.negocio.enums;

/**
 * 
 * @author Pablo.Andrade
 *
 */
public enum TipoPessoaEnum {

	/** O atributo PESSOA_FISICA. */
	PESSOA_FISICA((short)0, "Pessoa Física"),
	
	/** O atributo PESSOA_JURIDICA. */
	PESSOA_JURIDICA((short)1, "Pessoa Jurídica");

	/** O atributo codigo. */
	private Short codigo;
	
	/** O atributo descricao. */
	private String descricao;
		/**
	 * Construtor do Enum.
	 * @param codigo O identificador do tipo de pessoa.
	 * @param descricao A descrição tipo de pessoa.
	 */
	private TipoPessoaEnum(Short codigo, String descricao) {
		this.descricao = descricao;
		this.codigo = codigo;
	}

	/**
	 * @return the codigo
	 */
	public Short getCodigo() {
		return codigo;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
	
		
	/**
	 * Recupera documento.
	 * 
	 * @return documento
	 */
	public String getDocumento() {
		return PESSOA_FISICA.equals(this) ? "CPF" : "CNPJ";
	}
	

	/**
	 * Verifica se � pessoa fisica.
	 * 
	 * @param codigo
	 *            the codigo
	 * @return true, se for pessoa fisica
	 */
	public static boolean isPessoaFisica(Short codigo) {
		return PESSOA_FISICA.getCodigo().equals(codigo);
	}
	
	/**
	 * Verifica se � pessoa juridica.
	 * 
	 * @param codigo
	 *            the codigo
	 * @return true, se for pessoa juridica
	 */
	public static boolean isPessoaJuridica(Short codigo) {
		return PESSOA_JURIDICA.getCodigo().equals(codigo);
	}
	
	/**
	 * Value of.
	 * 
	 * @param codigo
	 *            the codigo
	 * @return tipo pessoa enum
	 */
	public static TipoPessoaEnum valueOf(Short codigo) {
		TipoPessoaEnum value = null;
		TipoPessoaEnum[] values = values();
		for (int i = 0; (i < values.length) && (value == null); i++) {
			TipoPessoaEnum tipo = values[i];
			if (tipo.codigo.equals(codigo)) {
				value = tipo;
			}
		}
		return value;
	}
}