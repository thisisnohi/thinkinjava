package nohi.utils;

import javax.xml.bind.*;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>xml</p>
 * @date 2024/04/01 10:51
 **/
public class XmlUtils {
    public static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * 对象转换为XML
     *
     * @param info 转换对象
     * @return 返回字符串
     * @throws JAXBException 异常
     */
    public static String convert2Xml(Object info) throws JAXBException {
        return convert2Xml(info, DEFAULT_ENCODING);
    }

    /**
     * 对象转换为XML
     *
     * @param info     转换对象
     * @param encoding 字符集
     * @return 返回字符串
     * @throws JAXBException 异常
     */
    public static String convert2Xml(Object info, String encoding) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(info.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
        StringWriter writer = new StringWriter();
        marshaller.marshal(info, writer);
        return writer.toString();
    }


    /**
     * xml转换为对象
     *
     * @param xml xml 字符串
     * @param clz 对象类型
     * @return 返回对象
     * @throws JAXBException 转换异常
     */
    public static <T> T convertXml2Bean(String xml, Class<T> clz) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clz);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (T) unmarshaller.unmarshal(new StringReader(xml));
    }

}
