package nohi.utils.sm4;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>加密算法名枚举</p>
 * @date 2023/04/06 10:50
 **/
public enum AlgorithmNameEnums {
    SM_4("SM4", "SM4加密"),

    SM_3("SM3", "SM3加密");

    private String name;

    private String desc;

    AlgorithmNameEnums(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
