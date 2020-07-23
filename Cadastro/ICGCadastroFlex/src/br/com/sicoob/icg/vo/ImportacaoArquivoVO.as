package br.com.sicoob.icg.vo
{
	import flash.net.registerClassAlias;
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	
	import mx.collections.ArrayCollection;
	
	import br.com.bancoob.tipos.IDateTime;
	import br.com.sicoob.icg.enums.SituacaoProcessamentoEnum;
	import br.com.sicoob.icg.enums.TipoAtualizacaoEnum;

	registerClassAlias("br.com.sicoob.icg.negocio.entidades.ImportaArquivo", ImportacaoArquivoVO);
	public class ImportacaoArquivoVO implements IExternalizable {
		
		private var _indiceZero: String;
		
		private var _codigoSituacaoProcessamento: String;
		private var _codigoTipoArquivo: String;
		private var _dataHoraImportacaoFiltroFim: IDateTime;
		private var _dataHoraImportacaoFiltroInicio: IDateTime;
		private var _dataHoraProcessamento: IDateTime;
		private var _dataImportacao: IDateTime;
		private var _descricaoErro: String;
		private var _descricaoSituacaoProcessamento: String;
		private var _descricaoTipoArquivo: String;
		private var _diretorioArquivo: String;
		private var _idImportaArquivo: Number;
		private var _idInstituicao: Number;
		private var _listaCodigoSituacaoProcessamento:ArrayCollection = new ArrayCollection();
		private var _nomeArquivo: String;
		private var _nomeArquivoDiretorio: String;
		private var _nomeInstituicao: String;
		private var _nomeUsuarioImportacao: String;
		private var _situacaoProcessamentoEnum: SituacaoProcessamentoEnum;
		private var _tipoAtualizacaoEnum: TipoAtualizacaoEnum;
		private var _usuarioResponsavel: String;
		
		public function set codigoSituacaoProcessamento(value:String):void {
			_codigoSituacaoProcessamento = value;
		}
		public function get codigoSituacaoProcessamento():String {
			return _codigoSituacaoProcessamento;
		}		

		public function set codigoTipoArquivo(value:String):void {
			_codigoTipoArquivo = value;
		}
		public function get codigoTipoArquivo():String {
			return _codigoTipoArquivo;
		}		

		public function set dataHoraImportacaoFiltroInicio(value:IDateTime):void {
			_dataHoraImportacaoFiltroInicio = value;
		}
		public function get dataHoraImportacaoFiltroInicio():IDateTime {
			return _dataHoraImportacaoFiltroInicio;
		}		

		public function set dataHoraImportacaoFiltroFim(value:IDateTime):void {
			_dataHoraImportacaoFiltroFim = value;
		}
		public function get dataHoraImportacaoFiltroFim():IDateTime {
			return _dataHoraImportacaoFiltroFim;
		}			

		public function set dataHoraProcessamento(value:IDateTime):void {
			_dataHoraProcessamento = value;
		}
		public function get dataHoraProcessamento():IDateTime {
			return _dataHoraProcessamento;
		}
		
		public function set dataImportacao(value:IDateTime):void {
			_dataImportacao = value;
		}
		public function get dataImportacao():IDateTime {
			return _dataImportacao;
		}

		public function set descricaoErro(value:String):void {
			_descricaoErro = value;
		}
		public function get descricaoErro():String {
			return _descricaoErro;
		}	

		public function set descricaoSituacaoProcessamento(value:String):void {
			_descricaoSituacaoProcessamento = value;
		}
		public function get descricaoSituacaoProcessamento():String {
			return _descricaoSituacaoProcessamento;
		}	

		public function set descricaoTipoArquivo(value:String):void {
			_descricaoTipoArquivo = value;
		}
		public function get descricaoTipoArquivo():String {
			return _descricaoTipoArquivo;
		}	

		public function set diretorioArquivo(value:String):void {
			_diretorioArquivo = value;
		}
		public function get diretorioArquivo():String {
			return _diretorioArquivo;
		}	
		
		public function set idImportaArquivo(value:Number):void {
			_idImportaArquivo = value;
		}
		public function get idImportaArquivo():Number {
			return _idImportaArquivo;
		}
		
		public function set idInstituicao(value:Number):void {
			_idInstituicao = value;
		}
		public function get idInstituicao():Number {
			return _idInstituicao;
		}	
		
		
		public function get listaCodigoSituacaoProcessamento():ArrayCollection
		{
			return _listaCodigoSituacaoProcessamento;
		}
		
		public function set listaCodigoSituacaoProcessamento(value:ArrayCollection):void
		{
			_listaCodigoSituacaoProcessamento = value;
		}
				
		public function set indixeZero(valor:String):void {
			_indiceZero = valor;
		}
		public function get indixeZero():String {
			return _indiceZero;
		}
		
		public function set nomeArquivo(valor:String):void {
			_nomeArquivo = valor;
		}
		
		public function get nomeArquivo():String {
			return _nomeArquivo;
		}
		
		public function set nomeArquivoDiretorio(valor:String):void {
			_nomeArquivoDiretorio = valor;
		}
		
		public function get nomeArquivoDiretorio():String {
			return _nomeArquivoDiretorio;
		}
		
		public function set nomeInstituicao(valor:String):void {
			_nomeInstituicao = valor;
		}
		
		public function get nomeInstituicao():String {
			return _nomeInstituicao;
		}
		
		public function set nomeUsuarioImportacao(valor:String):void {
			_nomeUsuarioImportacao = valor;
		}
		
		public function get nomeUsuarioImportacao():String {
			return _nomeUsuarioImportacao;
		}
		
		public function get situacaoProcessamentoEnum():SituacaoProcessamentoEnum {
			return _situacaoProcessamentoEnum;
		}
		
		public function set situacaoProcessamentoEnum(value:SituacaoProcessamentoEnum):void {
			_situacaoProcessamentoEnum = value;
		}

		public function get tipoAtualizacaoEnum():TipoAtualizacaoEnum {
			return _tipoAtualizacaoEnum;
		}
		
		public function set tipoAtualizacaoEnum(value:TipoAtualizacaoEnum):void {
			_tipoAtualizacaoEnum = value;
		}
		
		public function set usuarioResponsavel(value:String):void {
			_usuarioResponsavel = value;
		}
		public function get usuarioResponsavel():String {
			return _usuarioResponsavel;
		}

		 public function readExternal(input:IDataInput):void {
			_indiceZero = input.readObject() as String;
			_codigoSituacaoProcessamento = input.readObject() as String;
			_codigoTipoArquivo = input.readObject() as String;
			_dataHoraImportacaoFiltroFim = input.readObject() as IDateTime;
			_dataHoraImportacaoFiltroInicio = input.readObject() as IDateTime;
			_dataHoraProcessamento = input.readObject() as IDateTime;
			_dataImportacao = input.readObject() as IDateTime;
			_descricaoErro = input.readObject() as String;
			_descricaoSituacaoProcessamento = input.readObject() as String;
			_descricaoTipoArquivo = input.readObject() as String;
			_diretorioArquivo = input.readObject() as String;
			_idImportaArquivo = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
			_idInstituicao = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
			_listaCodigoSituacaoProcessamento = input.readObject() as ArrayCollection;
			_nomeArquivo = input.readObject() as String;
			_nomeArquivoDiretorio = input.readObject() as String;
			_nomeInstituicao = input.readObject() as String;
			_nomeUsuarioImportacao = input.readObject() as String;
			_situacaoProcessamentoEnum = input.readObject() as SituacaoProcessamentoEnum;
			_tipoAtualizacaoEnum = input.readObject() as TipoAtualizacaoEnum;
			_usuarioResponsavel = input.readObject() as String;
		}
		
		public function writeExternal(output:IDataOutput):void {
			output.writeObject(_indiceZero);
			output.writeObject(_codigoSituacaoProcessamento);
			output.writeObject(_codigoTipoArquivo);
			output.writeObject(_dataHoraImportacaoFiltroFim);
			output.writeObject(_dataHoraImportacaoFiltroInicio);
			output.writeObject(_dataHoraProcessamento);
			output.writeObject(_dataImportacao);
			output.writeObject(_descricaoErro);
			output.writeObject(_descricaoSituacaoProcessamento);
			output.writeObject(_descricaoTipoArquivo);
			output.writeObject(_diretorioArquivo);
			output.writeObject(_idImportaArquivo);
			output.writeObject(_idInstituicao);
			output.writeObject(_listaCodigoSituacaoProcessamento);
			output.writeObject(_nomeArquivo);
			output.writeObject(_nomeArquivoDiretorio);
			output.writeObject(_nomeInstituicao);
			output.writeObject(_nomeUsuarioImportacao);
			output.writeObject(_situacaoProcessamentoEnum);
			output.writeObject(_tipoAtualizacaoEnum);
			output.writeObject(_usuarioResponsavel);
			
		}
	}	
}