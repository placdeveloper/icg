package br.com.sicoob.icg.processamento.negocio.servicos.util;

import java.nio.file.Path;
import java.nio.file.Paths;

import br.com.sicoob.icg.negocio.entidades.ImportaArquivo;

public class ArquivoUtil {
	
	private ArquivoUtil() {
	}

	public static Path recuperaDiretorioArquivo(ImportaArquivo importaArquivo) {
		String nomeArquivo = importaArquivo.getDiretorioArquivo() + importaArquivo.getNomeArquivoDiretorio();
		return Paths.get(nomeArquivo);
	}

}
