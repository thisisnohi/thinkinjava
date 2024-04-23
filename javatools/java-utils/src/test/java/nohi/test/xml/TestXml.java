package nohi.test.xml;


import com.alibaba.fastjson2.JSONObject;
import nohi.test.xml.vo.*;
import nohi.utils.XmlUtils;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>xml</p>
 * @date 2024/04/01 11:08
 **/
public class TestXml {

    /**
     * 获取XML
     * @return 返回
     */
    public String getXml() throws JAXBException {
        RootXml root = new RootXml();
        root.setVersion("1.0");

        Header header = new Header();
        root.setHeader(header);
        header.setHeaderName("奋力无意");
        header.setFlowId("11111");
        header.setSysCode("A001");
        header.setTransDate(new Date());
        Body body = new Body();
        root.setBody(body);
        body.setAcctName("张三四");
        body.setAcctNo("1234567890");

        List<AcctItem> items = new ArrayList<>();
        body.setAcctItems(items);

        AcctItem item = new AcctItem();
        item.setAcctNo("ACCT_01");
        item.setName("一二三");
        items.add(item);

        item = new AcctItem();
        item.setAcctNo("ACCT_02");
        item.setName("一二三1111");
        items.add(item);

        List<PersonItem> personList = new ArrayList<>();
        body.setPersonItems(personList);

        PersonItem pItem = new PersonItem();
        pItem.setAge(1);
        pItem.setName("一二三");
        personList.add(pItem);

        pItem = new PersonItem();
        pItem.setAge(11);
        pItem.setName("一二三");
        personList.add(pItem);

        return XmlUtils.convert2Xml(root);
    }

    @Test
    public void obj2Xml() throws JAXBException {
        String xml = getXml();
        System.out.println(xml);
/*

<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<ROOT VERSION="1.0">
    <header HEADER_NAME="奋力无意">
        <sysCode>A001</sysCode>
        <flowId>11111</flowId>
        <transDate>2024-04-01 15:49:49</transDate>
    </header>
    <body>
        <acctNo>1234567890</acctNo>
        <acctName>张三四</acctName>
        <acctItems>
            <acctNo>ACCT_01</acctNo>
            <name>一二三</name>
        </acctItems>
        <acctItems>
            <acctNo>ACCT_02</acctNo>
            <name>一二三1111</name>
        </acctItems>
        <personList>
            <person>
                <age>1</age>
                <name>一二三</name>
            </person>
            <person>
                <age>11</age>
                <name>一二三</name>
            </person>
        </personList>
    </body>
</ROOT>

 */
    }

    @Test
    public void xml2Bean() throws JAXBException {
        String xml = getXml();
        System.out.println(xml);

        //
        RootXml root = XmlUtils.convertXml2Bean(xml, RootXml.class);

        System.out.println(JSONObject.toJSONString(root));

    }

}
