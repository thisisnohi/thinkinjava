package nohi.test;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>hutoolSm2</p>
 * @date 2023/04/06 09:38
 **/
public class TestHutoolSM2 {

    /**
     * 使用随机生成的密钥对加密或解密
     */
    @Test
    public void sm2EncodeDecode() {
        String text = "我是一段测试aaaa";

        SM2 sm2 = SmUtil.sm2();
        // 公钥加密，私钥解密
        String encryptStr = sm2.encryptBcd(text, KeyType.PublicKey);
        String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(encryptStr, KeyType.PrivateKey));
    }

    /**
     * 使用自定义密钥对加密或解密
     */
    @Test
    public void sm2EncodeDecode2() {
        String text = "我是一段测试aaaa";

        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();

        SM2 sm2 = SmUtil.sm2(privateKey, publicKey);
        // 公钥加密，私钥解密
        String encryptStr = sm2.encryptHex(text, KeyType.PublicKey);
        String decryptStr = StrUtil.utf8Str(sm2.decryptStr(encryptStr, KeyType.PrivateKey));
        System.out.println("decryptStr:" + decryptStr);
        Assertions.assertEquals(text, decryptStr, "解密后不相等");
    }

    /**
     * SM2签名和验签
     */
    @Test
    public void sm2Sign() {
        String content = "我是Hanley.";
        final SM2 sm2 = SmUtil.sm2();
        String sign = sm2.signHex(HexUtil.encodeHexStr(content));
        // true
        boolean verify = sm2.verifyHex(HexUtil.encodeHexStr(content), sign);
        Assertions.assertTrue(verify, "验签失败");
    }

    @Test
    public void sm2Sign2() {
        String content = "我是Hanley.";
        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        final SM2 sm2 = new SM2(pair.getPrivate(), pair.getPublic());
        byte[] sign = sm2.sign(content.getBytes());
        // true
        boolean verify = sm2.verify(content.getBytes(), sign);
        Assertions.assertTrue(verify, "验签失败");
    }

    @Test
    public void sm2SignWith生成密钥() {
        String content = "我是Hanley.";

        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        byte[] privateKeyBytes = pair.getPrivate().getEncoded();
        byte[] publicKeyBytes = pair.getPublic().getEncoded();

        System.out.println("私钥：\n" + Base64.encode(privateKeyBytes));
        System.out.println("公钥：\n" + Base64.encode(publicKeyBytes));

        System.out.println("私钥HexString：\n" + Hex.toHexString(privateKeyBytes));
        System.out.println("私钥HexString：\n" + Hex.toHexString(publicKeyBytes));


        String privateKey = Hex.toHexString(privateKeyBytes);
        String publicKey = Hex.toHexString(publicKeyBytes);

        final SM2 sm2 = new SM2(privateKey, publicKey);
        byte[] sign = sm2.sign(content.getBytes());
        String signData = Base64.encode(sign);
        System.out.println("签名:" + signData);
        // true
        boolean verify = sm2.verify(content.getBytes(), sign);
        Assertions.assertTrue(verify, "验签失败");
    }

    @Test
    public void sm2SignWith密钥() {
        String content = "我是Hanley.";

        String privateKey = "308193020100301306072a8648ce3d020106082a811ccf5501822d0479307702010104205f196b0dd2306b1c4c703680258a4d03ec262e1280df16083cae40158a7f062ea00a06082a811ccf5501822da14403420004cd77072e241da72b9c8ed27d8fb034e6feea35102dd559721485440c1fd6cf9c8a53c50bf0a19c765061cabb0a7da3a30fe46d56c3858b764929645ddbffb5ef";
        String publicKey = "3059301306072a8648ce3d020106082a811ccf5501822d03420004cd77072e241da72b9c8ed27d8fb034e6feea35102dd559721485440c1fd6cf9c8a53c50bf0a19c765061cabb0a7da3a30fe46d56c3858b764929645ddbffb5ef";

        final SM2 sm2 = new SM2(privateKey, publicKey);
        byte[] sign = sm2.sign(content.getBytes());
        // true
        boolean verify = sm2.verify(content.getBytes(), sign);
        Assertions.assertTrue(verify, "验签失败");
    }

    /**
     * 验签
     */
    @Test
    public void sm2SignVerify() {
        String content = "我是Hanley.";
        String signData = "MEQCIGO7GWbMuesHV3KDOdyG5OhLDpEPk42St7yfsY6XOaOVAiBNLJEiDLSjsDTvArI5O1zzOQDzfU451oV/8D/Auge0xQ==";
        String privateKey = "308193020100301306072a8648ce3d020106082a811ccf5501822d0479307702010104205f196b0dd2306b1c4c703680258a4d03ec262e1280df16083cae40158a7f062ea00a06082a811ccf5501822da14403420004cd77072e241da72b9c8ed27d8fb034e6feea35102dd559721485440c1fd6cf9c8a53c50bf0a19c765061cabb0a7da3a30fe46d56c3858b764929645ddbffb5ef";
        String publicKey = "3059301306072a8648ce3d020106082a811ccf5501822d03420004cd77072e241da72b9c8ed27d8fb034e6feea35102dd559721485440c1fd6cf9c8a53c50bf0a19c765061cabb0a7da3a30fe46d56c3858b764929645ddbffb5ef";

        SM2 sm2 = new SM2(privateKey, publicKey);
        // true
        boolean verify = sm2.verify(content.getBytes(), Base64.decode(signData));
        Assertions.assertTrue(verify, "验签失败");
    }

}
