package br.com.sicoob.icg.processamento.negocio.validacao.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.persistencia.types.DateTimeDB;
import br.com.bancoob.util.StringUtil;
import br.com.sicoob.capes.api.negocio.delegates.CAPESApiFabricaDelegates;
import br.com.sicoob.capes.api.negocio.delegates.PessoaDelegate;
import br.com.sicoob.capes.api.negocio.vo.PessoaVO;
import br.com.sicoob.capes.comum.negocio.enums.TipoPessoaEnum;
import br.com.sicoob.icg.processamento.negocio.excecao.ConversaoInvalidaException;

public final class Validar {

	private Validar() {
	}

	private static PessoaDelegate pessoaDelegate = CAPESApiFabricaDelegates.getInstance().criarPessoaDelegate();

	public static Boolean validaQuantidadeCamposObrigatorios(int tamanho, int qtdCamposObrigatorios) {
		return tamanho == qtdCamposObrigatorios;
	}

	public static Boolean validaTamanhoCampoVazio(Integer tamanhoCampo) {
		return tamanhoCampo == 0;
	}

	public static Boolean validaTamanhoCampo(Integer tamanhoCampo, Integer tamanhoValido) {
		return (tamanhoCampo > tamanhoValido);
	}

	public static Boolean validarCampoValor(String str) {
		if (StringUtil.isNotBlank(str) || isNumero(str)) {
			BigDecimal valor = new BigDecimal(str);
			if ((valor).compareTo(BigDecimal.ZERO) == 1) {
				return true;
			}
		}
		return false;
	}

	public static Short converterParaShort(String valor) {
		return Short.parseShort(valor);
	}

	public static boolean converterParaBoolen(String valor) {
		return Boolean.parseBoolean(valor);
	}

	public static Boolean isBigDecimal(String valor) {
		if (casasDecimaisValida(valor) && validaQuantidadePontoEVirgula(valor)) {
			Locale Local = new Locale("pt", "BR");
			String pattern = "#,##0.00";
			DecimalFormat formatter = new DecimalFormat(pattern, new DecimalFormatSymbols(Local));
			try {
				formatter.setParseBigDecimal(true);
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public static BigDecimal converterParaBigDecimal(String valor) throws ConversaoInvalidaException {
		if ((valor != null) && (isBigDecimal(valor))) {
			Locale Local = new Locale("pt", "BR");
			String pattern = "#,##0.00";
			DecimalFormat formatter = new DecimalFormat(pattern, new DecimalFormatSymbols(Local));
			formatter.setParseBigDecimal(true);
			try {
				BigDecimal decimal = (BigDecimal) formatter.parse(valor);
				return decimal;
			} catch (ParseException e) {
				throw new ConversaoInvalidaException("Erro ao converterParaBigDecimal");
			}

		} else {
			return BigDecimal.ZERO;
		}
	}

	public static Character converterParaCharacter(String valor) {
		return valor.charAt(0);
	}

	public static Date converterParaData(String valor) {
		Date date = null;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(valor);
		} catch (ParseException e) {
			return null;
		}
		return date;
	}

	public static DateTimeDB converterParaDateTimeDB(Date data) {
		if (data != null) {
			return new DateTimeDB(data.getTime());
		}
		return null;
	}

	public static Integer converterParaInteger(String valor) {
		return Integer.parseInt(valor);
	}

	public static Long converterParaLong(String valor) {
		return Long.parseLong(valor);
	}

	public static boolean isNumero(String valor) {

		boolean isNumero = true;
		try {
			Double.parseDouble(valor);

		} catch (NumberFormatException e) {
			isNumero = false;
		}
		return isNumero;
	}

	public static Boolean validarCampoData(String data) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.setLenient(false);
			sdf.parse(data);
			return true;
		} catch (ParseException ex) {
			return false;
		}
	}

	public static Boolean validarCampoBoolean(String valor) {

		try {
			Boolean.parseBoolean(valor);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static Boolean possuiErroValidacao(StringBuilder erroValidacao) {
		return StringUtil.isNotBlank(erroValidacao.toString());
	}

	public static Boolean verificaCadastroCPFCNPJ(String cpfCnpj, Integer idInstituicao) throws BancoobException {
		PessoaVO pessoaVO = pessoaDelegate.obterPorCpfCnpjInstituicao(cpfCnpj, idInstituicao);

		if (Objects.nonNull(pessoaVO)) {
			return StringUtil.isNotBlank(pessoaVO.getCpfCnpj());
		} else {
			return false;
		}
	}

	public static Boolean ehEmailValido(String email) {
		boolean ehValido = false;
		if (email != null && email.length() > 0) {
			String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			if (matcher.matches()) {
				ehValido = true;
			}
		}
		return ehValido;
	}

	public static Boolean ehTipoPessoaValido(String valor) {
		return ((valor != null) && (TipoPessoaEnum.PESSOA_FISICA.getCodigo().toString().equals(valor)
				|| TipoPessoaEnum.PESSOA_JURIDICA.getCodigo().toString().equals(valor)));
	}

	public static String retornaExtensaoArquivo(String valor) {
		return valor.substring(valor.indexOf('.') + 1, valor.length());
	}

	public static Boolean casasDecimaisValida(String valor) {

		Integer casasDecimaisPermitidas = 2;
		Integer casasDecimaisParametro = valor.substring(valor.lastIndexOf(',') + 1, valor.length()).length();
		Integer qtdCaractestesParametro = valor.length();

		if (Validar.isNumero(valor.substring(valor.lastIndexOf(',') + 1, valor.length()))) {
			if (casasDecimaisParametro > casasDecimaisPermitidas
					&& !qtdCaractestesParametro.equals(casasDecimaisParametro)) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	public static Boolean validaQuantidadePontoEVirgula(String valor) {

		int qtdVirgula = (valor.split(",").length - 1);
		int qtdpontos = (valor.split("\\.").length);

		return !(qtdVirgula > 1 || qtdpontos > 1);
	}

}
