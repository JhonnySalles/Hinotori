package cadastro.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import comum.model.enums.TamanhoImagem;
import comum.model.utils.Utils;
import servidor.entities.Imagem;

/**
 * <p>
 * Classe responssável por conter funções uteis do sistema.
 * </p>
 * 
 * @author Jhonny de Salles Noschang
 */
public class CadastroUtils {

	public static Set<Imagem> processaImagens(File caminhoImagem) throws IOException {
		Set<Imagem> imagens = new HashSet<Imagem>();

		String imagemNome = caminhoImagem.getName();
		String imagemExtenssao = Utils.getFileExtension(caminhoImagem);

		BufferedImage bImageOriginal = ImageIO.read(caminhoImagem);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bImageOriginal, imagemExtenssao, bos);

		imagens.add(new Imagem(imagemNome, imagemExtenssao, bos.toByteArray(), TamanhoImagem.ORIGINAL));

		BufferedImage bImgPequena = Utils.resizeImage(bImageOriginal, 100, 100);
		ImageIO.write(bImgPequena, imagemExtenssao, bos);
		imagens.add(new Imagem(imagemNome, imagemExtenssao, bos.toByteArray(), TamanhoImagem.PEQUENA));

		BufferedImage bImgMedia = Utils.resizeImage(bImageOriginal, 600, 600);
		ImageIO.write(bImgMedia, imagemExtenssao, bos);
		imagens.add(new Imagem(imagemNome, imagemExtenssao, bos.toByteArray(), TamanhoImagem.MEDIA));

		return imagens;
	}

}
