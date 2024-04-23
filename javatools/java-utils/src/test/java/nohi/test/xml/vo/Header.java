package nohi.test.xml.vo;

import lombok.Data;
import nohi.test.xml.adapter.JaxbDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>header</p>
 * @date 2024/04/01 14:18
 **/
@XmlRootElement(name = "Header")
@XmlAccessorType(XmlAccessType.FIELD) // 根据字段解析XML
@Data
public class Header {
    private String sysCode;
    private String flowId;

    @XmlJavaTypeAdapter(JaxbDateAdapter.class)
    private Date transDate;

    @XmlAttribute(name = "HEADER_NAME")
    private String headerName;

}
