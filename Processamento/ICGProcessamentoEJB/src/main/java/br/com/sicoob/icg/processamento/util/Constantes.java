package br.com.sicoob.icg.processamento.util;

public final class Constantes {
	
		private Constantes() {
		}
		
		public static final String DATASOURCE_ICG = "jdbc/BancoobImportacaoCadastroGeralDS";

		public static final String ARQUIVO_QUERIES_PROCESSAMENTO = "icg.processamento.queries.xml";

		public static final String NOME_COLECAO_COMANDOS = "COMANDOS_ICG_PROCESSAMENTO";
		
		public static final Integer ICG_UNIDADE_INST = 9999;
		public static final String ICG_USUARIO_APROVACAO = "USRICG";
		public static final String CODIGO_NACIONALIDADE = "10";
		public static final int TAMANHO_CAMPO_DATA = 10;
		
		public static final String MENSAGEM_MN001 = "MN001";
		public static final String MENSAGEM_MN002 = "MN002";
		public static final String MENSAGEM_MN003 = "MN003";
		public static final String MENSAGEM_MN004 = "MN004";
		public static final String MENSAGEM_MN005 = "MN005";
		public static final String MENSAGEM_MN006 = "MN006";
		public static final String MENSAGEM_MN007 = "MN007";
		public static final String MENSAGEM_MN008 = "MN008";
		public static final String MENSAGEM_MN009 = "MN009";
		public static final String MENSAGEM_MN010 = "MN010";
		public static final String MENSAGEM_MN011 = "MN011";
		public static final String MENSAGEM_MN012 = "MN012";
		public static final String MENSAGEM_MN013 = "MN013";
		public static final String MENSAGEM_MN014 = "MN014";
		
		public static final String MENSAGEM_ERRO_CAPES = "ERRO GERADO PELO CAPES: ";
		public static final String MENSAGEM_PESSOA_NAO_CADASTRADA_CAPES = "PESSOA NÃO CADASTRADA NO CAPES";
		public static final String MENSAGEM_ALTERACAO_CAPES = "ALTERANDO REGISTRO NO CAPES: ";
		public static final String MENSAGEM_INCLUINDO_CAPES = "INCLUINDO REGISTRO NO CAPES: ";
		
		public static final String MENSAGEM_TEXTO_CPF = "texto.cpf";
		public static final String MENSAGEM_TEXTO_NOME_PESSOA_FISICA = "texto.nomePessoaFisica";
		public static final String MENSAGEM_TEXTO_NOME_COMPLETO_PESSOA_FISICA = "texto.nomeCompletoPessoaFisica";
		public static final String MENSAGEM_TEXTO_MENOR_EMANCIPADO = "texto.menorEmancipado";
		public static final String MENSAGEM_TEXTO_TIPO_SEXO = "texto.tipoSexo";
		public static final String MENSAGEM_TEXTO_CODIGO_UNIAO_ESTAVEL = "texto.codigoUniaoEstavel";
		public static final String MENSAGEM_TEXTO_DATA_NASCIMENTO = "texto.dataNascimento";
		public static final String MENSAGEM_TEXTO_NOME_MAE = "texto.nomeMae";
		public static final String MENSAGEM_TEXTO_NOME_PAI = "texto.nomePai";
		public static final String MENSAGEM_TEXTO_CODIGO_TIPO_DOCUMENTO_IDENTIFICACAO = "texto.codigoTipoDocumentoIdentificacao";
		public static final String MENSAGEM_TEXTO_NUMERO_DOCUMENTO_IDENTIFICACAO = "texto.numeroDocumentoIdentificacao";
		public static final String MENSAGEM_TEXTO_ORGAO_EXPEDIDOR_DOCUMENTO_IDENTIFICACAO = "texto.orgaoExpedidorDocumentoIdentificacao";
		public static final String MENSAGEM_TEXTO_UF_DOCUMENTO_IDENTIFICACAO = "texto.ufDocumentoIdentificacao";
		public static final String MENSAGEM_TEXTO_DATA_EMISSAO_DOCUMENTO = "texto.dataEmissaoDocumento";
		public static final String MENSAGEM_TEXTO_CODIGO_NACIONALIDADE = "texto.codigoNacionalidade";
		public static final String MENSAGEM_TEXTO_DESCRICAO_LOCAL_NATURALIDADE = "texto.descricaoLocalNaturalidade";
		public static final String MENSAGEM_TEXTO_CODIGO_LOCAL_NATURALIDADE = "texto.codigoLocalNaturalidade";
		public static final String MENSAGEM_TEXTO_CODIGO_VINCULO_EMPREGATICIO = "texto.codigoVinculoEmpregaticio";
		public static final String MENSAGEM_TEXTO_CODIGO_OCUPACAO_PROFISSIONAL = "texto.codigoOcupacaoProfissional";
		public static final String MENSAGEM_TEXTO_CODIGO_GRAU_INSTRUCAO = "texto.codigoGrauInstrucao";
		public static final String MENSAGEM_TEXTO_CODIGO_ESTADOCIVIL = "texto.codigoEstadoCivil";
		public static final String MENSAGEM_TEXTO_CPF_CONJUGE = "texto.cpfConjuge";
		public static final String MENSAGEM_TEXTO_QUANTIDADE_DEPENDENTE = "texto.quantidadeDependente";
		public static final String MENSAGEM_TEXTO_CODIGO_REGIME_CASAMENTO = "texto.codigoRegimeCasamento";
		public static final String MENSAGEM_TEXTO_CPF_CNPJ = "texto.cpfcnpj";
		public static final String MENSAGEM_TEXTO_CODIGO_TIPO_PESSOA = "texto.codigoTipoPessoa";
		public static final String MENSAGEM_TEXTO_CODIGO_TIPO_EMAIL = "texto.codigoTipoEmail";
		public static final String MENSAGEM_TEXTO_EMAIL = "texto.email";
		public static final String MENSAGEM_TEXTO_BAIRRO = "texto.bairro";
		public static final String MENSAGEM_TEXTO_LOGRADOURO = "texto.logradouro";
		public static final String MENSAGEM_TEXTO_COMPLEMENTO_ENDERECO = "texto.complementoEndereco";
		public static final String MENSAGEM_TEXTO_NUMERO_ENDERECO = "texto.numeroEndereco";
		public static final String MENSAGEM_TEXTO_CODIGO_LOCALIDADE = "texto.codigoLocalidade";
		public static final String MENSAGEM_TEXTO_CODIGO_TIPO_LOGRADOURO = "texto.codigoTipoLogradouro";
		public static final String MENSAGEM_TEXTO_CODIGO_TIPO_RENDA = "texto.codigoTipoRenda";
		public static final String MENSAGEM_TEXTO_RENDA_FIXA_VARIAVEL = "texto.rendaFixaVariavel";
		public static final String MENSAGEM_TEXTO_CODIGO_SIMPLES_NACIONAL = "texto.codigoSimplesNacional";
		public static final String MENSAGEM_TEXTO_DATA_VALIDADE_RENDA = "texto.dataValidadeRenda";
		public static final String MENSAGEM_TEXTO_VALOR_RECEITA_BRUTA_MENSAL = "texto.valorReceitaBrutalMensal";
		public static final String MENSAGEM_TEXTO_NOME_PESSOA_JURIDICA = "texto.nomePessoaJuridica";
		public static final String MENSAGEM_TEXTO_NOME_COMPLETO_PESSOA_JURIDICA = "texto.nomeCompletoPessoaJuridica";
		public static final String MENSAGEM_TEXTO_ESFERA_ADMINISTRATIVA = "texto.esferaAdministrativa";
		public static final String MENSAGEM_TEXTO_ATIVIDADE_ECONOMICA = "texto.atividadeEconomica";
		public static final String MENSAGEM_TEXTO_NUMERO_REGISTRO_ORGAO_COMPETENTE = "texto.numeroRegistroOrgaoCompetente";
		public static final String MENSAGEM_TEXTO_DATA_CONSTITUICAO = "texto.dataConstituicao";
		public static final String MENSAGEM_TEXTO_DATA_REGISTRO = "texto.dataRegistro";
		public static final String MENSAGEM_TEXTO_CODIGO_TIPO_TELEFONE = "texto.codigoTipoTelefone";
		public static final String MENSAGEM_TEXTO_DDD_TELEFONE = "texto.dddTelefone";
		public static final String MENSAGEM_TEXTO_NUMERO_TELEFONE = "texto.numeroTelefone";
		public static final String MENSAGEM_TEXTO_RAMAL_TELEFONE = "texto.ramalTelefone";
		public static final String MENSAGEM_TEXTO_OBSERVACAO_TELEFONE = "texto.observacaoTelefone";
		public static final String MENSAGEM_TEXTO_CODIGO_TIPO_ENDERECO = "texto.codigoTipoEndereco";
		public static final String MENSAGEM_TEXTO_CEP = "texto.cep";
		public static final String MENSAGEM_TEXTO_CODIGO_TIPO_FORMA_CONSTITUICAO = "texto.codigoTipoFormaConstituicao";

		public static final String MENSAGEM_ERRO_INCLUSAO_DADOS = "Erro na inclusão dos dados";

		public static final String NOME_COLUNA_IDIMPORTAARQUIVO = "IDIMPORTAARQUIVO";
		public static final String NOME_COLUNA_NUMCPFCNPJ = "NUMCPFCNPJ";
		public static final String NOME_COLUNA_CODTIPOPESSOA = "CODTIPOPESSOA";
		public static final String NOME_COLUNA_IDINSTITUICAO = "IDINSTITUICAO";
		public static final String NOME_COLUNA_CODSITUACAOPROCESSAMENTO = "CODSITUACAOPROCESSAMENTO";
		public static final String NOME_COLUNA_IDPESSOA = "IDPESSOA";
		public static final String NOME_COLUNA_CODATIVIDADEECONOMICA = "CODATIVIDADEECONOMICA";
		public static final String NOME_COLUNA_IDINSTITUICAOPESSOA = "IDINSTITUICAO";
		
}
