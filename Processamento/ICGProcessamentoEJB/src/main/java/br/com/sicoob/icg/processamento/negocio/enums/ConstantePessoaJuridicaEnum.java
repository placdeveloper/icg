package br.com.sicoob.icg.processamento.negocio.enums;

public enum ConstantePessoaJuridicaEnum {
	
	QTD_CAMPOS_OBRIGATORIOS (9),
	TAMANHO_CNPJ (14),
	TAMANHO_RAZAO_SOCIAL (60),
	TAMANHO_RAZAO_SOCIAL_COMPLETO (100),
	TAMANHO_NUMREGISTROJUNTACOMERCIAL (24),
	TAMANHO_CODESFERAADMINISTRATIVA (5),
	TAMANHO_CODIGOTIPOFORMACONSTITUICAO(5),
	TAMANHO_DATA (10),

	POSICAO_CAMPO_CNPJ (0),
	POSICAO_CAMPO_RAZAO_SOCIAL (1),
	POSICAO_CAMPO_RAZAO_SOCIAL_COMPLETO (2),
	POSICAO_CAMPO_CODATIVIDADEECONOMICA (3),
	POSICAO_CAMPO_DATACONSTITUICAO (4),
	POSICAO_CAMPO_NUMREGISTROJUNTACOMERCIAL (5),
	POSICAO_CAMPO_DATAREGISTROJUNTACOMERCIAL (6),
	POSICAO_CAMPO_CODESFERAADMINISTRATIVA (7),
	POSICAO_CAMPO_CODIGOTIPOFORMACONSTITUICAO(8);

	private Integer codigo;

	private ConstantePessoaJuridicaEnum(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public static ConstantePessoaJuridicaEnum obterPorCodigo(Integer codigo) {
		for (ConstantePessoaJuridicaEnum constanteEnderecoEnum : values()) {
			if (constanteEnderecoEnum.codigo.equals(codigo)) {
				return constanteEnderecoEnum;
			}
		}
		return null;
	}

}