package nohi.test.xml.vo;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>acct</p>
 * @date 2024/04/01 15:38
 **/
@Data
@XmlRootElement(name = "person")
public class PersonItem {
    private String name;
    private int age;

}
