package nohi.test.xml.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>JaxbDateAdapter</p>
 * @date 2024/04/01 15:36
 **/
public class JaxbDateAdapter extends XmlAdapter<String, Date> {
    static final String STANDARM_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public Date unmarshal(String v) throws Exception {
        if (v == null) {
            return null;
        }

        DateFormat format = new SimpleDateFormat(STANDARM_DATE_FORMAT);
        return format.parse(v);
    }

    @Override
    public String marshal(Date v) throws Exception {
        DateFormat format = new SimpleDateFormat(STANDARM_DATE_FORMAT);
        return format.format(v);
    }
}
