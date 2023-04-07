package nohi.test;


import nohi.utils.sm2.SM2Util;
import org.junit.jupiter.api.Test;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>Sm2</p>
 * @date 2023/04/06 09:38
 **/
public class TestSm2 {

    @Test
    public void sm2SignSM3withSM2() {
        String content = "我是Hanley.";
        String signData = "MEQCIGO7GWbMuesHV3KDOdyG5OhLDpEPk42St7yfsY6XOaOVAiBNLJEiDLSjsDTvArI5O1zzOQDzfU451oV/8D/Auge0xQ==";
        String privateKey = "308193020100301306072a8648ce3d020106082a811ccf5501822d0479307702010104205f196b0dd2306b1c4c703680258a4d03ec262e1280df16083cae40158a7f062ea00a06082a811ccf5501822da14403420004cd77072e241da72b9c8ed27d8fb034e6feea35102dd559721485440c1fd6cf9c8a53c50bf0a19c765061cabb0a7da3a30fe46d56c3858b764929645ddbffb5ef";
        String publicKey = "3059301306072a8648ce3d020106082a811ccf5501822d03420004cd77072e241da72b9c8ed27d8fb034e6feea35102dd559721485440c1fd6cf9c8a53c50bf0a19c765061cabb0a7da3a30fe46d56c3858b764929645ddbffb5ef";

    }

    @Test
    public void testSM2Util() {
        SM2Util sm2 = new SM2Util();
    }
}
