package br.com.sicoob.icg.processamento.negocio.enums;

public enum OpcaoSimNaoEnum {

	SIM(true, "SIM", "1"),

	NAO(false, "NÃO", "0");

	private Boolean codigo;

	private String descricao;

	private String cod;

	private OpcaoSimNaoEnum(Boolean codigo, String descricao, String cod) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.cod = cod;
	}

	public Boolean getCodigo() {
		return codigo;
	}

	public String getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static OpcaoSimNaoEnum obterPorCod(String criterio) {
		for (OpcaoSimNaoEnum OpcaoSimNaoEnum : values()) {
			if (OpcaoSimNaoEnum.cod.equals(criterio)) {
				return OpcaoSimNaoEnum;
			}
		}
		return null;
	}
}
