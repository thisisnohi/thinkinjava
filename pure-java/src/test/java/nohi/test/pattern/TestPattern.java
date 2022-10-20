package nohi.test.pattern;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author NOHI
 * 2021-09-18 20:22
 **/
public class TestPattern {

    @Test
    public void testPattern() {
        StringBuilder soap = new StringBuilder(); //构造请求报文
        soap.append("<soapenv:Envelope  xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" ");
        soap.append(" xmlns:wor=\"http://www.horizon.com/workflow/webservice/client/workflowCommon\">");
        soap.append("<soapenv:Header>");
        soap.append("<HZWFService  xmlns=\"http://www.huizhengtech.com/webservice/workflow\"");
        soap.append(" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"");
        soap.append(" SOAP-ENV:actor=\"http://www.w3.org/2003/05/soap-envelope/role/next\">admin&admin</HZWFService>");
        soap.append("</soapenv:Header>");
        soap.append("<soapenv:Body>");
        soap.append(" <wor:sysLogin>");
        soap.append(" <loginName>loginname</loginName >");
        soap.append(" <password>password</password>");
        soap.append(" <dbidentifier>system</dbidentifier>");
        soap.append(" </wor:sysLogin>");
        soap.append(" </soapenv:Body>");
        soap.append(" </soapenv:Envelope>");

        String xml = soap.toString();
        System.out.println(xml);
        System.out.println("============================================");
        Pattern pattern = Pattern.compile("<[a-zA-Z]*?:");
        Matcher matcher = pattern.matcher(xml);

        while (matcher.find()){
            System.out.println(matcher.group());
        }
        System.out.println("============================================");
        xml = xml.replaceAll("<[a-zA-Z]*?:", "<");
        System.out.println(xml);
        xml = xml.replaceAll("</[a-zA-Z]*?:", "</");
        System.out.println(xml);
    }
}
