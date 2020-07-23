package br.com.sicoob.icg.vo
{
	import flash.net.registerClassAlias;
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	
	import br.com.bancoob.tipos.IDateTime;

	registerClassAlias("br.com.sicoob.icg.negocio.entidades.vo.ErroProcessamentoVO", ErroProcessamentoVO);
	public class ErroProcessamentoVO implements IExternalizable {
		
		private var _indiceZero: String;
		
		private var _pessoa: String;
		private var _situacaoProcessamento: String;
		private var _dataProcessamento: IDateTime;
		private var _descricaoErro: String;
		private var _linhaArquivo: String;

		public function set pessoa(value:String):void {
			_pessoa = value;
		}
		public function get pessoa():String {
			return _pessoa;
		}	

		public function set situacaoProcessamento(value:String):void {
			_situacaoProcessamento = value;
		}
		public function get situacaoProcessamento():String {
			return _situacaoProcessamento;
		}	

		public function set dataProcessamento(value:IDateTime):void {
			_dataProcessamento = value;
		}
		public function get dataProcessamento():IDateTime {
			return _dataProcessamento;
		}

		public function set descricaoErro(value:String):void {
			_descricaoErro = value;
		}
		public function get descricaoErro():String {
			return _descricaoErro;
		}	

		public function set linhaArquivo(value:String):void {
			_linhaArquivo = value;
		}
		public function get linhaArquivo():String {
			return _linhaArquivo;
		}	

		 public function readExternal(input:IDataInput):void {
			 _indiceZero = input.readObject() as String;
			 _pessoa = input.readObject() as String;
			 _situacaoProcessamento = input.readObject() as String;
			 _dataProcessamento = input.readObject() as IDateTime;
			_descricaoErro = input.readObject() as String;
			_linhaArquivo = input.readObject() as String;
		}
		
		public function writeExternal(output:IDataOutput):void {
			output.writeObject(_indiceZero);
			output.writeObject(_pessoa);
			output.writeObject(_situacaoProcessamento);
			output.writeObject(_dataProcessamento);
			output.writeObject(_descricaoErro);
			output.writeObject(_linhaArquivo);
		}
	}	
}