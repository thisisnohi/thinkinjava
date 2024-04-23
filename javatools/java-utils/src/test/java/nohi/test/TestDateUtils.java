package nohi.test;

import nohi.utils.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p></p>
 * @date 2022/10/21 16:59
 **/
public class TestDateUtils {

    @Test
    public void testDate() {
        Date date = DateUtils.getNow();
        System.out.println("date:" + date);
        String dateStr = DateUtils.format(date, DateUtils.SIMPLE_DATE);
        String nowStr = DateUtils.getNowStr();
        System.out.println("dateStr:" + dateStr + ",nowStr:" + nowStr);
        Assertions.assertEquals("日期格式不正确", dateStr, nowStr);
    }

    @Test
    public void testLocalDate() {
        Date date = DateUtils.getNow();
        String nowStr = DateUtils.getNowStr();

        LocalDate localDate = DateUtils.stringToLocalDate(nowStr);
        System.out.println("localDate:" + localDate);
        System.out.println("localDate:" + localDate.getYear());
        System.out.println("localDate:" + localDate.getMonth());
        System.out.println("localDate:" + localDate.getMonthValue());
        System.out.println("localDate:" + localDate.getDayOfYear());
    }
}
