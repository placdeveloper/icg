package br.com.sicoob.icg.util {
	import br.com.bancoob.util.FormataData;
	import br.com.bancoob.util.FormataNumero;
	
	import mx.collections.ListCollectionView;
	import mx.collections.Sort;
	import mx.collections.SortField;
	import mx.formatters.DateFormatter;
		
	public class Funcoes
	{
		public static const MASCARA_PARSE_DATA:String = "ddMMyyyy";
		public static const MASCARA_PARSE_DATA_MES:String = "MMyyyy";
		public static const MASCARA_PARSE_DATA_MES_INVERTIDO:String = "yyyyMM";
		
		public static function formatTelDDD(strTel:String):String{
			if(strTel == null || strTel == ""){
				return null;
			}
			var tel:String="(ddd)NUM-num";
			var i:int = 0;
			tel = tel.replace("ddd",strTel.substring(i,i+=2));
			if(strTel.length == 11){
				tel = tel.replace("NUM",strTel.substring(i,i+=5));	
			}else{
				tel = tel.replace("NUM",strTel.substring(i,i+=4));
			}
			tel = tel.replace("num",strTel.substring(i,i+=4));
			return tel;
		}
		
		public static function formatTel(strTel:String):String{
			if(strTel == null || strTel == ""){
				return null;
			}
			var tel:String="NUM-num";
			var i:int = 0;
			if(strTel.length == 9){
				tel = tel.replace("NUM",strTel.substring(i,i+=5));	
			}else{
				tel = tel.replace("NUM",strTel.substring(i,i+=4));
			}
			tel = tel.replace("num",strTel.substring(i,i+=4));
			return tel;
		}

		public static function dataParse(strData:String, mascara:String=MASCARA_PARSE_DATA):Date{
			var data:Date = null;
			if(strData != null && strData.length == mascara.length){
				if(MASCARA_PARSE_DATA == mascara){
					data = new Date(strData.substr(4,4),strData.substr(2,2),strData.substr(0,2));
				}else if(MASCARA_PARSE_DATA_MES == mascara){
					data = new Date(strData.substr(2,4),strData.substr(0,2));
				}else if(MASCARA_PARSE_DATA_MES_INVERTIDO == mascara){
					data = new Date(strData.substr(0,4),strData.substr(4,2));
				}
			}
			return data;
		}
		
		public static function formataValorReal(valor:String):String{
			if(valor == null || valor == ""){
				return "";
			}
			return ("R$ "+FormataNumero.formata(Number(valor)));
		}
		 
		
		public static function formataData(strData:String, mascara:String="dd/MM/yyyy"):String{
			strData= strData.replace("BRT","GMT-0300");
			strData= strData.replace("BRST","GMT-0300");
			strData= strData.replace("GMT-03:00","GMT-0300");
			var data:Date=new Date();
			data.setTime(Date.parse(strData));
			return FormataData.formata(data, mascara);
		}
		
		/**
		 * Esse metodo foi feito para inverter o dia com o mês, pois em alguns casos essa data
		 * vem invertida no retorno do Serasa. Como foi no caso da dataInicioAchei e dataFimAchei no 
		 * relatorio e tela da consulta.
		 *  
		 * */
		public static function formataDataMMddYYYY(strData:String, mascara:String="MM/dd/yyyy"):String{
			strData= strData.replace("BRT","GMT-0300");
			strData= strData.replace("BRST","GMT-0300");
			strData= strData.replace("GMT-03:00","GMT-0300");
			var data:Date=new Date();
			data.setTime(Date.parse(strData));
			return FormataData.formata(data, mascara);
		}
		
        public static function formatDateToNumber(data:Date):Number{
        	var formatador:DateFormatter = new DateFormatter();
        	formatador.formatString = "DD/MM/YYYY";
        	return Date.parse(formatador.format(data));
        }
		
		
			
		private static function existeElemento(xml:XML, elemento:XML):XML{
			for each (var x:XML in xml.elements()){
				if(x.name()==elemento.name()){
					return x;
				}
			}
			return null;
		}
	
		/**
		 * Método responsável por ordenar as listas em ordem crescente.
		 * 
		 * */
		public static function ordenarLista(lista: ListCollectionView, nomeAtributo: String): void{
			var sort: Sort = new Sort();
			sort.fields = [new SortField(nomeAtributo)];
			lista.sort = sort;
			lista.refresh();
		}

		public static function formataPorcentagem(numero:Number):String
		{
			return FormataNumero.formata(numero) + "%";
		}
		
		public static function formataPorcentagemXML(strNum:String):String
		{
			if(strNum == null || strNum == ""){
				return "";
			}
			return FormataNumero.formata(Number(strNum)) + "%";
		}
		
	}
}