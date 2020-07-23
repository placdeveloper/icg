package br.com.sicoob.icg.comum.negocio.servicos.ejb;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.commons.io.IOUtils;

import br.com.sicoob.capes.api.negocio.vo.DominioVO;
import br.com.sicoob.icg.comum.negocio.servicos.interfaces.DownloadDominioServicoLocal;
import br.com.sicoob.icg.comum.negocio.servicos.interfaces.DownloadDominioServicoRemote;
import br.com.sicoob.icg.negocio.entidades.vo.DownloadDominioVO;

@Stateless
@Local(DownloadDominioServicoLocal.class)
@Remote(DownloadDominioServicoRemote.class)
public class DownloadDominioServicoEJB extends ICGComumServicoEJB
		implements DownloadDominioServicoLocal, DownloadDominioServicoRemote {

	private static final String NOME_ARQUIVO = "SICOOB_ICG_{0}_{1,date,yyyyMMddHHmmss}.txt";

	public DownloadDominioVO obterArquivoDominio(List<DominioVO> lista, String tipoArquivo) {
		DownloadDominioVO retorno = null;

		if (lista != null && !lista.isEmpty()) {

			Date dataAtual = new Date();
			ZipOutputStream zos = null;
			ByteArrayOutputStream baos = null;

			String nomeArquivo = MessageFormat.format(NOME_ARQUIVO, tipoArquivo, dataAtual);

			baos = new ByteArrayOutputStream();
			zos = new ZipOutputStream(baos);

			try {
				zos.setLevel(ZipOutputStream.DEFLATED);
				zos.setComment("Download de dados do CAPES");
				zos.putNextEntry(new ZipEntry(nomeArquivo));

				for (DominioVO dominioVO : lista) {
					IOUtils.write(dominioVO.getCodigo() + ";" + dominioVO.getDescricao(), zos, "UTF-8");
					IOUtils.write(IOUtils.LINE_SEPARATOR, zos, "UTF-8");
				}

				zos.closeEntry();
				retorno = new DownloadDominioVO();
				retorno.setBytes(baos.toByteArray());
				retorno.setNome(nomeArquivo.replace(".txt", ".zip"));

			} catch (IOException ioe) {
				getLogger().erro(ioe, "[ICG] Erro ao gerar o arquivo.");
			} finally {
				IOUtils.closeQuietly(baos);
				IOUtils.closeQuietly(zos);
			}

		}
		return retorno;

	}

}
