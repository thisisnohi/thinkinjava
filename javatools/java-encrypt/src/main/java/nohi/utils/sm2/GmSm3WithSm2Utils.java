package nohi.utils.sm2;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jcajce.spec.SM2ParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>GmUtil</p>
 * @date 2023/04/06 11:54
 **/

/**
 * need jars:
 * bcpkix-jdk15on-170.jar
 * bcprov-jdk15on-170.jar
 * <p>
 * <p>
 * <p>
 * 按要求国密算法仅允许使用加密机，本demo国密算法仅供学习使用，请不要用于生产用途。
 */
public class GmSm3WithSm2Utils {

    private static X9ECParameters x9ECParameters = GMNamedCurves.getByName("sm2p256v1");
    private static ECDomainParameters ecDomainParameters = new ECDomainParameters(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN());
    private static ECParameterSpec ecParameterSpec = new ECParameterSpec(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN());

    static {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        } else {
            Security.removeProvider("BC"); //解决eclipse调试时tomcat自动重新加载时，BC存在不明原因异常的问题。
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    /**
     * @param msg
     * @param userId
     * @param privateKey
     * @return r||s，直接拼接byte数组的rs
     */
    public static byte[] signSm3WithSm2(byte[] msg, byte[] userId, PrivateKey privateKey) {
        return rsAsn1ToPlainByteArray(signSm3WithSm2Asn1Rs(msg, userId, privateKey));
    }

    /**
     * @param msg
     * @param userId
     * @param privateKey
     * @return rs in <b>asn1 format</b>
     */
    public static byte[] signSm3WithSm2Asn1Rs(byte[] msg, byte[] userId, PrivateKey privateKey) {
        try {
            SM2ParameterSpec parameterSpec = new SM2ParameterSpec(userId);
            Signature signer = Signature.getInstance("SM3withSM2", "BC");
            signer.setParameter(parameterSpec);
            signer.initSign(privateKey, new SecureRandom());
            signer.update(msg, 0, msg.length);
            byte[] sig = signer.sign();
            return sig;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param msg
     * @param userId
     * @param rs        r||s，直接拼接byte数组的rs
     * @param publicKey
     * @return
     */
    public static boolean verifySm3WithSm2(byte[] msg, byte[] userId, byte[] rs, PublicKey publicKey) {
        if (rs == null || msg == null || userId == null) {
            return false;
        }
        if (rs.length != RS_LEN * 2) {
            return false;
        }
        return verifySm3WithSm2Asn1Rs(msg, userId, rsPlainByteArrayToAsn1(rs), publicKey);
    }

    /**
     * @param msg
     * @param userId
     * @param rs        in <b>asn1 format</b>
     * @param publicKey
     * @return
     */
    public static boolean verifySm3WithSm2Asn1Rs(byte[] msg, byte[] userId, byte[] rs, PublicKey publicKey) {
        try {
            SM2ParameterSpec parameterSpec = new SM2ParameterSpec(userId);
            Signature verifier = Signature.getInstance("SM3withSM2", "BC");
            verifier.setParameter(parameterSpec);
            verifier.initVerify(publicKey);
            verifier.update(msg, 0, msg.length);
            return verifier.verify(rs);
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return false;
        }
    }

    /**
     * SM2解密，加密机为C1C3C2
     * c1||c3||c2
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] sm2Decrypt(byte[] data, PrivateKey key) {
        try {
            BCECPrivateKey localECPrivateKey = (BCECPrivateKey) key;
            ECPrivateKeyParameters ecPrivateKeyParameters = new ECPrivateKeyParameters(localECPrivateKey.getD(), ecDomainParameters);
//             SM2Engine sm2Engine = new SM2Engine();
            SM2Engine sm2Engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
            sm2Engine.init(false, ecPrivateKeyParameters);
            try {
                return sm2Engine.processBlock(data, 0, data.length);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * SM2加密，加密机为C1C3C2
     * c1||c3||c2
     *
     * @param data
     * @param key
     * @return
     */

    public static byte[] sm2Encrypt(byte[] data, PublicKey key) {
        BCECPublicKey localECPublicKey = (BCECPublicKey) key;
        ECPublicKeyParameters ecPublicKeyParameters = new ECPublicKeyParameters(localECPublicKey.getQ(), ecDomainParameters);
//           SM2Engine sm2Engine = new SM2Engine();
        SM2Engine sm2Engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
        sm2Engine.init(true, new ParametersWithRandom(ecPublicKeyParameters, new SecureRandom()));
        try {
            return sm2Engine.processBlock(data, 0, data.length);
        } catch (InvalidCipherTextException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param bytes
     * @return
     */
    public static byte[] sm3(byte[] bytes) {
        SM3Digest sm3 = new SM3Digest();
        sm3.update(bytes, 0, bytes.length);
        byte[] result = new byte[sm3.getDigestSize()];
        sm3.doFinal(result, 0);
        return result;
    }


    /**
     * @param bytes
     * @return
     */

    private final static int RS_LEN = 32;

    private static byte[] bigIntToFixexLengthBytes(BigInteger rOrS) {
        // for sm2p256v1, n is 00fffffffeffffffffffffffffffffffff7203df6b21c6052b53bbf40939d54123,
        // r and s are the result of mod n, so they should be less than n and have length<=32
        byte[] rs = rOrS.toByteArray();
        if (rs.length == RS_LEN) {
            return rs;
        } else if (rs.length == RS_LEN + 1 && rs[0] == 0) {
            return Arrays.copyOfRange(rs, 1, RS_LEN + 1);
        } else if (rs.length < RS_LEN) {
            byte[] result = new byte[RS_LEN];
            Arrays.fill(result, (byte) 0);
            System.arraycopy(rs, 0, result, RS_LEN - rs.length, rs.length);
            return result;
        } else {
            throw new RuntimeException("err rs: " + Hex.toHexString(rs));
        }
    }

    /**
     * BC的SM3withSM2签名得到的结果的rs是asn1格式的，加密机需要直接拼接r||s的字节数组，使用这个方法转换
     *
     * @param rsDer rs in asn1 format
     * @return sign result in plain byte array
     */
    private static byte[] rsAsn1ToPlainByteArray(byte[] rsDer) {
        ASN1Sequence seq = ASN1Sequence.getInstance(rsDer);
        byte[] r = bigIntToFixexLengthBytes(ASN1Integer.getInstance(seq.getObjectAt(0)).getValue());
        byte[] s = bigIntToFixexLengthBytes(ASN1Integer.getInstance(seq.getObjectAt(1)).getValue());
        byte[] result = new byte[RS_LEN * 2];
        System.arraycopy(r, 0, result, 0, r.length);
        System.arraycopy(s, 0, result, RS_LEN, s.length);
        return result;
    }

    /**
     * BC的SM3withSM2验签需要的rs是asn1格式的，加密机返回的是r||s的字节数组，使用这个方法转化成asn1格式
     *
     * @param sign in plain byte array
     * @return rs result in asn1 format
     */
    private static byte[] rsPlainByteArrayToAsn1(byte[] sign) {
        if (sign.length != RS_LEN * 2) {
            throw new RuntimeException("err rs. ");
        }
        BigInteger r = new BigInteger(1, Arrays.copyOfRange(sign, 0, RS_LEN));
        BigInteger s = new BigInteger(1, Arrays.copyOfRange(sign, RS_LEN, RS_LEN * 2));
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.add(new ASN1Integer(r));
        v.add(new ASN1Integer(s));
        try {
            return new DERSequence(v).getEncoded("DER");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator kpGen = KeyPairGenerator.getInstance("EC", "BC");
            kpGen.initialize(ecParameterSpec, new SecureRandom());
            KeyPair kp = kpGen.generateKeyPair();
            return kp;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static BCECPrivateKey getPrivatekeyFromD(BigInteger d) {
        ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(d, ecParameterSpec);
        return new BCECPrivateKey("EC", ecPrivateKeySpec, BouncyCastleProvider.CONFIGURATION);
    }

    //加密机产生的公钥格式为裸公钥 X||Y, 此方法为将加密机公钥转换为BC库可识别的asn.1格式公钥
    public static BCECPublicKey getPublickeyFromXY(BigInteger x, BigInteger y) {
        ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(x9ECParameters.getCurve().createPoint(x, y), ecParameterSpec);
        return new BCECPublicKey("EC", ecPublicKeySpec, BouncyCastleProvider.CONFIGURATION);
    }


    public static void main(String[] args) {

        // 生成公私钥对 ---------------------
        KeyPair kp = generateKeyPair();

        System.out.println("private：" + Hex.toHexString(kp.getPrivate().getEncoded()));
        System.out.println("public：" + Hex.toHexString(kp.getPublic().getEncoded()));

//        System.out.println("private key d: " + (bigIntToFixexLengthBytes((BCECPrivateKey)kp.getPrivate()).getD()));
        System.out.println("public key q:" + ((BCECPublicKey) kp.getPublic()).getQ()); //{x, y, zs...}

        //BC生成的公钥为ans.1编码的，取出X和Y送给加密机
        System.out.println("public key X:" + ((BCECPublicKey) kp.getPublic()).getQ().getXCoord()); //{x, y, zs...}
        System.out.println("public key Y:" + ((BCECPublicKey) kp.getPublic()).getQ().getYCoord()); //{x, y, zs...}


//      // 由d生成私钥 ---------------------
        BigInteger d = new BigInteger("D919089D12E085E7905DDFE74C20144CADFE2A1B0E1647C89BE60DEBAE076227", 16);
        BCECPrivateKey PrivateKey1 = getPrivatekeyFromD(d);


        //加密机产生的公钥格式为裸公钥 X||Y，如C64A9FE6B4A9361A2E8BE1B5A057E9C3302CF7088CD4B2357E9ED8B012E4F0A687896945FF9AA0DD2E44AC430D8E1F77A66C13759A2A21AF968FCB3AAEF8F250
        //公钥X坐标PublicKeyXHex: C64A9FE6B4A9361A2E8BE1B5A057E9C3302CF7088CD4B2357E9ED8B012E4F0A6
        //公钥Y坐标PublicKeyYHex: 87896945FF9AA0DD2E44AC430D8E1F77A66C13759A2A21AF968FCB3AAEF8F250
        PublicKey publicKey1 = getPublickeyFromXY(new BigInteger("C64A9FE6B4A9361A2E8BE1B5A057E9C3302CF7088CD4B2357E9ED8B012E4F0A6", 16), new BigInteger("87896945FF9AA0DD2E44AC430D8E1F77A66C13759A2A21AF968FCB3AAEF8F250", 16));
        System.out.println(Hex.toHexString(publicKey1.getEncoded()));


        byte[] msg = "message digest".getBytes();
        byte[] userId = "userId".getBytes();
        byte[] sig = signSm3WithSm2(msg, userId, PrivateKey1);
        System.out.println("sign:" + Hex.toHexString(sig));
        System.out.println("verifsign:" + verifySm3WithSm2(msg, userId, sig, publicKey1));


//        // sm2 encrypt and decrypt test ---------------------
////        KeyPair kp = generateKeyPair();
//
//        byte[] bs = sm2Encrypt("s12333".getBytes(), publicKey1);
//        System.out.println("sm2Chiper:" + Hex.toHexString(bs));
//
//        //BC加密的密文前有标识符04，需要将标识符去掉送给加密机
////        System.arraycopy(bs, 1, bs, 0, bs.length-1);
////        System.out.println("sm2Chipertohsm:"+Hex.toHexString(bs));
//
//        bs = sm2Decrypt(bs, PrivateKey1);
////        byte[] data =HexString2Bytes("040C1EBA59D41F8523200C3B656B49624709D978D9E03C98A07B2024CF027FF2AD9574C9B498FB1C4AFCE284BACE71848253BCD639CB629B82A0C21DF11026602E00A4208657C5CB81B49B2385B0843A25A7AAA7F714651A115375D1D8AB894CFABD1A04");
////        bs = sm2Decrypt(data, PrivateKey1);
//        if (bs == null || bs.length == 0) {
//            throw new RuntimeException("ad empty bs");
//        }
//        {
//            System.out.println("sm2clear:" + new String(bs));
//
//        }
//
////        byte[] msg = "message digest".getBytes();
//        byte[] digest = sm3(msg);
//
//        if (digest == null || digest.length == 0) {
//            throw new RuntimeException("ad empty digest");
//        }
//        System.out.println("sm3digest:" + Hex.toHexString(digest));

    }

}
