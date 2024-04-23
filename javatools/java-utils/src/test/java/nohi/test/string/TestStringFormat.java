package nohi.test.string;

import nohi.utils.StringFormat;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>stringformat</p>
 * @date 2024/04/16 14:47
 **/
public class TestStringFormat {


    @Test
    public void testLength() {
        String str1 = "123456789012345678901234567890123456789012345678901234567890";
        String str2 = "一二三四五六七八九十一二三四五六七八九十";

        int maxLength = StringFormat.maxLength(str1, str2);
        System.out.println("maxLength: " + maxLength);

        int formatLength = StringFormat.formatLength(str1, maxLength);
        System.out.println(String.format("%-" + formatLength + "s\t%s", str1, formatLength));

        formatLength = StringFormat.formatLength(str2, maxLength);
        System.out.println(String.format("%-" + formatLength + "s\t%s", str2, formatLength));

    }

    @Test
    public void strLength() {
        String str1 = "123456789012345678901234567890123456789012345678901234567890";
        String str2 = "一二三四五六七八九十一二三四五六七八九十";

        int maxLength = StringFormat.maxLength(str1, str2);
        System.out.println("maxLength: " + maxLength);

        int formatLength = StringFormat.formatLength(str1, maxLength);
        System.out.println(String.format("%-" + formatLength + "s\t%s", str1, formatLength));

        formatLength = StringFormat.formatLength(str2, maxLength);
        System.out.println(String.format("%-" + formatLength + "s\t%s", str2, formatLength));
        System.out.println("=======================================");
        for (int i = 1; i <= 38 ; i++) {
            str1 = StringUtils.repeat("一", i);
            formatLength = StringFormat.formatLength(str1, maxLength);
            System.out.println(String.format("%-" + formatLength + "s\t%s\t%s", str1, i, formatLength));
        }
        for (int i = 1; i <= 60 ; i++) {
            str1 = StringUtils.repeat("1", i);
            formatLength = StringFormat.formatLength(str1, maxLength);
            System.out.println(String.format("%-" + formatLength + "s\t%s\t%s", str1, i, formatLength));
        }
        for (int i = 1; i <= 23 ; i++) {
            str1 = StringUtils.repeat("一1", i);
            formatLength = StringFormat.formatLength(str1, maxLength);
            System.out.println(String.format("%-" + formatLength + "s\t%s\t%s", str1, i, formatLength));
        }
        for (int i = 1; i <= 14 ; i++) {
            str1 = StringUtils.repeat("一二1", i);
            formatLength = StringFormat.formatLength(str1, maxLength);
            System.out.println(String.format("%-" + formatLength + "s\t%s\t%s", str1, i, formatLength));
        }
    }
}
