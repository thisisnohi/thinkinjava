package nohi.test.xml.vo;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>header</p>
 * @date 2024/04/01 14:18
 **/
@XmlRootElement(name = "Body")
@XmlAccessorType(XmlAccessType.FIELD)  // 根据字段解析XML
@Data
public class Body {
    private String acctNo;
    private String acctName;

    private List<AcctItem> acctItems;

    // XmlElementWrapper 为 personItems 对应层级
    // XmlElement 为列表内对象标签
    @XmlElementWrapper(name = "personList")
    @XmlElement(name = "person")
    private List<PersonItem> personItems;

}
