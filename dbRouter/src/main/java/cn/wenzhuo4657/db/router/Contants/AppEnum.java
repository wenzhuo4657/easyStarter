package cn.wenzhuo4657.db.router.Contants;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/20
 * @description:
 */

public enum AppEnum {
    DataSourse_name_split(",","数据源名称分隔符")
    ;
    String code;
    String info;

    AppEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
