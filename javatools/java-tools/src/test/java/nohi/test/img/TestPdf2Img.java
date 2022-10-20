package nohi.test.img;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author NOHI
 * 2021-10-24 16:40
 **/
public class TestPdf2Img {

    @Test
    public void test1() {
        String pdfPath = "/Users/nohi/Downloads/222.pdf";
        String imgPath = "/Users/nohi/Downloads/222";
        File file = new File(pdfPath);
        PDDocument doc = null;
        try {
            doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            System.out.println("pageCount:" + pageCount);
            for (int i = 0; i < pageCount; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 296);
                //BufferedImage image = renderer.renderImage(i, 2.5f);
                ImageIO.write(image, "PNG", new File(imgPath + "_" + i + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != doc) {
                try {
                    doc.close();
                } catch (IOException e) {
                }
            }


        }
    }

    @Test
    public void testCustImg() throws IOException {
        String imgPath = "/Users/nohi/Downloads/222_0.png";
        String targetImg = "/Users/nohi/Downloads/3";
        BufferedImage bufImage = ImageIO.read(new File(imgPath));
        /**
         * 裁剪图片, 参数说明:
         *     x: 裁剪起点横坐标
         *     y: 裁剪起点纵坐标
         *     w: 需要裁剪的宽度
         *     h: 需要裁剪的高度
         * BufferedImage getSubimage (int x, int y, int w, int h);
         */
        System.out.println("width:" + bufImage.getWidth() + ",height:" + bufImage.getHeight());
        int startY = 0;
        int height = 1150;
        int endY = height;
        System.out.println(String.format("[%s,%s][%s,%s]", 0, startY, bufImage.getWidth(), endY));
        BufferedImage newBI = bufImage.getSubimage(0, startY, bufImage.getWidth(), endY);
        ImageIO.write(newBI, "PNG", new File(targetImg + "_1" + ".png"));

        startY = startY + height;
        endY = endY + height;
        System.out.println(String.format("[%s,%s][%s,%s]", 0, startY, bufImage.getWidth(), endY));
        newBI = bufImage.getSubimage(0, startY, bufImage.getWidth(), height);
        ImageIO.write(newBI, "PNG", new File(targetImg + "_2" + ".png"));

        startY = startY + height;
        endY = endY + height;
        System.out.println(String.format("[%s,%s][%s,%s]", 0, startY, bufImage.getWidth(), endY));
        newBI = bufImage.getSubimage(0, startY, bufImage.getWidth(), height);
        ImageIO.write(newBI, "PNG", new File(targetImg + "_3" + ".png"));
    }
}
