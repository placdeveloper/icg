package br.com.sicoob.icg.util {
	import mx.controls.dataGridClasses.DataGridColumn;
	
	import br.com.bancoob.componentes.grid.ColunaGrid;
	import br.com.bancoob.tipos.IDateTime;
	import br.com.bancoob.util.FormatUtil;
	import br.com.bancoob.util.reflexao.ReflectionUtils;
	
	
	/**
	 * Classe que contém métodos utilitários para as tabelas paginadas.
	 * 
	 * @author alyssonrctis
	 */
	public class FormatadorUtil{
		
		public function FormatadorUtil(){
		
		}

		//**********
		// Serviços:
		//**********		
		/**
		 * Método usado para formatar atributo do tipo Booleano
		 * */
        public static function defaultBooleanSituacaoFunction(objeto:Object, 
        		coluna:DataGridColumn):String {     
			var valor:Object = ReflectionUtils.recuperarPropriedade(objeto, 
					coluna.dataField);
        	if (valor){
        		return "ATIVO";	
        	}else {
        		return "INATIVO";
        	}
        }
		
		/**
		 * Método usado para formatar atributo do tipo Booleano
		 * */
		public static function defaultLabelFunction(objeto:Object, coluna:DataGridColumn):String {     
			return (objeto != null)?objeto.toString():"";
		}
        
		/**
		 * Método usado para formatar atributo do tipo Booleano
		 * */
        public static function defaultPasswordHiddenFunction(objeto:Object, 
        		coluna:DataGridColumn):String {     
			var valor:Object = ReflectionUtils.recuperarPropriedade(objeto, 
					coluna.dataField);
			var retornoSenhaOculta: String = "";
			for(var i: int = 0; i < valor.toString().length; i++){
				retornoSenhaOculta += "*";
			}
			return retornoSenhaOculta;
        }
        
		/**
		 * Método usado para formatar atributo do tipo Operação
		 * */
        public static function defaultTipoOperacaoFunction(objeto:Object, 
        		coluna:DataGridColumn):String {  
   
			var valor:Object = ReflectionUtils.recuperarPropriedade(objeto, 
					coluna.dataField);
			var codTipoOperacao: int = Number(valor);
			
        	if (codTipoOperacao == ConstantesUtil.TIPO_OPERACAO_INCLUSAO){
        		return "INCLUSÃO";	
        	}else if (codTipoOperacao == ConstantesUtil.TIPO_OPERACAO_ALTERACAO){
        		return "ALTERAÇÃO";
        	}else{
        		return "EXCLUSÃO";
        	}
        }
        
        /**
		 * Formata um Date ou IDateTime para dd/MM/yyyy
		 * */
        public static function defaultDateLabelXMLFunction(objeto:Object, 
        		coluna:DataGridColumn):String {     
			var valor:Object = ReflectionUtils.recuperarPropriedade(objeto, 
					coluna.dataField);
			
			var strDataFormatada: String = "";
			if(valor != null){
				if(valor is IDateTime) {
					strDataFormatada = Funcoes.formataData((valor as IDateTime).data.toString());
				} else {
					strDataFormatada = Funcoes.formataData(valor.toString());
				}
			}
			return strDataFormatada;
        }
		
		/**
		 * Formata um Date ou IDateTime para MM/yyyy.
		 * */
		public static function defaultDateLabelXMLMMyyyyFunction(objeto:Object, 
																 coluna:DataGridColumn):String {     
			var valor:Object = ReflectionUtils.recuperarPropriedade(objeto, 
				(coluna as ColunaGrid).propriedade);
			
			var strDataFormatada: String = "";
			if(valor != null){
				if(valor is IDateTime) {
					strDataFormatada = Funcoes.formataData((valor as IDateTime).data.toString(),"MM/yyyy");
				} else {
					strDataFormatada = Funcoes.formataData(valor.toString(),"MM/yyyy");
				}
			}
			return strDataFormatada;
		}
		
		/**
		 * Formata um Número para data formato com porcentagem e duas casas decimais.
		 * */
		public static function defaultPercentLabelXMLFunction(objeto:Object, 
																 coluna:DataGridColumn):String {     
			var valor:String = String(ReflectionUtils.recuperarPropriedade(objeto, 
				ColunaGrid(coluna).propriedade));
			if(valor == null || valor == ""){
				return "";
			}
			return Funcoes.formataPorcentagem(Number(valor));
		}
		
		/**
		 * Método usado para formatar atributo do tipo Booleano
		 * */
        public static function defaultBooleanLabelFunction(objeto:Object, 
        		coluna:DataGridColumn):String {     
			var valor:Object = ReflectionUtils.recuperarPropriedade(objeto, 
					coluna.dataField);
			var retorno:String = (String(valor) == "true")? "Sim": "Não";
    		return retorno;
        }
        
		/**
		 * Método usado para formatar atributo do tipo Booleano
		 * 
		 * */
        public static function labelOcorrenciaAnotacaoFunction(objeto:Object, 
        		coluna:DataGridColumn):String {     
			var valor:Object = ReflectionUtils.recuperarPropriedade(objeto, 
					coluna.dataField);
			var retorno:String = (Boolean(valor) == true)? "CONSTA": "NADA CONSTA";
    		return retorno;
        }
        
        /**
		 * Formata um Date ou IDateTime para dd/MM/yyyy  HH:mm:ss
		 * */
        public static function defaultDateHoraComSegundoLabelXMLFunction(objeto:Object, 
        		coluna:DataGridColumn):String {     
			var valor:Object = ReflectionUtils.recuperarPropriedade(objeto, 
					coluna.dataField);
			
			var strDataFormatada: String = "";
			if(valor != null){
				if(valor is IDateTime) {
					strDataFormatada = Funcoes.formataData((valor as IDateTime).data.toString(), "DD/MM/YYYY  JJ:NN:SS");
				} else {
					strDataFormatada = Funcoes.formataData(valor.toString());
				}
			}
			return strDataFormatada;
        }

		/**
		 * Formata um Date para HH:mm:ss
		 * */
		public static function defaultDateHoraHHmmssXMLFunction(objeto:Object, 
																		 coluna:DataGridColumn):String {     
			var valor:Object = ReflectionUtils.recuperarPropriedade(objeto, 
				coluna.dataField);
			
			var strDataFormatada: String = "";
			if(valor != null){
				strDataFormatada = Funcoes.formataData(valor.toString(),"JJ:NN:SS");
			}
			return strDataFormatada;
		}
		
		public static function defaultCNPJLabelFunction(objeto:Object, 
														coluna:DataGridColumn):String {     
			var valor:Object = ReflectionUtils.recuperarPropriedade(objeto, 
				ColunaGrid(coluna).propriedade);
			if(valor == null || valor == ""){
				return "";
			}
			return FormatUtil.formataCPFCNPJ(String(valor)); 
		}
		
		/**
		 * Método usado para formatar atributos com caracteres 'S' e 'N'
		 * retornando 'Sim' ou 'Não'
		 * 
		 * */
		public static function defaultOcorrenciaSNFunction(objeto:Object, 
														   coluna:DataGridColumn):String {     
			var valor:Object = ReflectionUtils.recuperarPropriedade(objeto, 
				coluna.dataField);
			
			var retorno:String = (String(valor) == "S")? "SIM": "NÃO";
			return retorno;
		}
		
		/**
		 * Método usado para formatar CEP
		 * 
		 * */
		public static function defaultCEPLabelFunction(objeto:Object, 
														   coluna:DataGridColumn):String {     
			var valor:Object = ReflectionUtils.recuperarPropriedade(objeto, 
				ColunaGrid(coluna).propriedade);
			if(valor == null || valor == ""){
				return "";
			}
			return FormatUtil.formataCEP(String(valor));
		}
	}

}