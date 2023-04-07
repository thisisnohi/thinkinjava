package nohi.test;

import nohi.utils.sm4.AlgorithmModeEnums;
import nohi.utils.sm4.AlgorithmNameEnums;
import nohi.utils.sm4.AlgorithmPaddingModeEnums;
import nohi.utils.sm4.Sm4AlgorithmUtil;
import org.junit.jupiter.api.Test;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>sm4</p>
 * @date 2023/04/06 10:55
 **/
public class TestSm4 {

    @Test
    public void sm4() throws Exception {
        String data = "快乐编码 , 生活愉快!";
        data = "{\"cardType\":\"D\",\"signNo\":\"10011000058120200327144700O0859556\",\"cardNo\":\"6222021208017891373\"}";
        String key = "0123456789abcdef";

        Sm4AlgorithmUtil util = new Sm4AlgorithmUtil(key);

        String ecbEncrypt = util.encrypt(AlgorithmNameEnums.SM_4, AlgorithmModeEnums.ECB, AlgorithmPaddingModeEnums.PKCS5_PADDING, data);
        System.out.println("ECB加密:" + ecbEncrypt);
        System.out.println("ECB解密:" + util.decrypt(AlgorithmNameEnums.SM_4, AlgorithmModeEnums.ECB, AlgorithmPaddingModeEnums.PKCS5_PADDING, ecbEncrypt));

        String cbcEncrypt = util.encrypt(AlgorithmNameEnums.SM_4, AlgorithmModeEnums.CBC, AlgorithmPaddingModeEnums.PKCS7_PADDING, data);
        System.out.println("CBC加密: " + cbcEncrypt);
        System.out.println("CBC解密" + util.decrypt(AlgorithmNameEnums.SM_4, AlgorithmModeEnums.CBC, AlgorithmPaddingModeEnums.PKCS7_PADDING, cbcEncrypt));

        String cfbEncrypt = util.encrypt(AlgorithmNameEnums.SM_4, AlgorithmModeEnums.CFB, AlgorithmPaddingModeEnums.PKCS7_PADDING, data);
        System.out.println("CFB加密: " + cfbEncrypt);
        System.out.println("CFB解密" + util.decrypt(AlgorithmNameEnums.SM_4, AlgorithmModeEnums.CFB, AlgorithmPaddingModeEnums.PKCS7_PADDING, cfbEncrypt));

        String ofbEncrypt = util.encrypt(AlgorithmNameEnums.SM_4, AlgorithmModeEnums.OFB, AlgorithmPaddingModeEnums.PKCS7_PADDING, data);
        System.out.println("OFB加密: " + ofbEncrypt);
        System.out.println("OFB解密: " + util.decrypt(AlgorithmNameEnums.SM_4, AlgorithmModeEnums.OFB, AlgorithmPaddingModeEnums.PKCS7_PADDING, ofbEncrypt));

        String ctrEncrypt = util.encrypt(AlgorithmNameEnums.SM_4, AlgorithmModeEnums.CTR, AlgorithmPaddingModeEnums.PKCS7_PADDING, data);
        System.out.println("CTR加密: " + ctrEncrypt);
        System.out.println("CTR解密:" + util.decrypt(AlgorithmNameEnums.SM_4, AlgorithmModeEnums.CTR, AlgorithmPaddingModeEnums.PKCS7_PADDING, ctrEncrypt));


    }
}
