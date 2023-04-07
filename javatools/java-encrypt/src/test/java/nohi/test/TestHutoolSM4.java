package nohi.test;


import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;

import java.security.PublicKey;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>hutoolSm4</p>
 * @date 2023/04/06 09:38
 **/
public class TestHutoolSM4 {

    @Test
    public void sm4() {
        String content = "test中文";
        SymmetricCrypto sm4 = SmUtil.sm4();
        System.out.println("getAlgorithm：" + sm4.getCipher().getAlgorithm());
        System.out.println("getBlockSize：" + sm4.getCipher().getBlockSize());
        System.out.println("getIV：" + (null == sm4.getCipher().getIV() ? "IS NULL" : new String(sm4.getCipher().getIV())));
        System.out.println("getParameters：" + sm4.getCipher().getParameters());
        System.out.println("getInfo：" + sm4.getCipher().getProvider().getInfo());
        String encryptHex = sm4.encryptHex(content);
        String decryptStr = sm4.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println("decryptStr:" + decryptStr);
    }

    @Test
    public void sm42() {
        String data = "{\"cardType\":\"D\",\"signNo\":\"10011000058120200327144700O0859556\",\"cardNo\":\"6222021208017891373\"}";
        String key = "0123456789abcdef";
        byte[] keyBytes = key.getBytes();
        System.out.println("keyBytes.length:" + keyBytes.length);

        String alg = "SM4/ECB/PKCS5Padding";
        alg = "SM4";
        alg = "SM4/ECB/PKCS7Padding";
//        alg = "SM4/CBC";

        SymmetricCrypto symmetricCrypto = new SymmetricCrypto(alg, keyBytes);
        byte[] encodeBytes = symmetricCrypto.encrypt(data.getBytes());
        System.out.println("加密结果长度：" + encodeBytes.length);

        String encodeMsg = Hex.toHexString(encodeBytes).toUpperCase();
        System.out.println("加密结果Hex:" + encodeMsg);

        // 解密
        String decodeMsg = symmetricCrypto.decryptStr(encodeMsg);
        System.out.println("解密结果:" + decodeMsg);
    }

}
