package nohi.test.xml.vo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>Root</p>
 * @date 2024/04/01 14:17
 **/
// @XmlRootElement(name = "ROOT", namespace = "nohi.com")
@XmlRootElement(name = "ROOT")
@XmlAccessorType(XmlAccessType.FIELD)  // 根据字段解析XML
@Data
public class RootXml {

    @XmlAttribute(name = "VERSION")
    private String version;

    private Header header;
    private Body body;

}
