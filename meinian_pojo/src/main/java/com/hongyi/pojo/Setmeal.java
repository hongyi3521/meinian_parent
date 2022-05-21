package com.hongyi.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 旅行套餐
 */
public class Setmeal implements Serializable {
    private Integer id;//套餐ID
    private String name;//套餐名称
    private String code;//编号
    private String helpCode;//助记码
    private String sex;//套餐适用性别：0不限 1男 2女
    private String age;//套餐适用年龄
    private Float price;//套餐价格
    private String remark;//备注
    private String attention;//注意事项
    private String img;//套餐对应图片存储路径（用于存放七牛云上的图片名称）
    private List<TravelGroup> travelGroups;//体检套餐对应的跟团游，多对多关系

    public List<TravelGroup> getTravelGroups() {
        return travelGroups;
    }

    public void setTravelGroups(List<TravelGroup> travelGroups) {
        this.travelGroups = travelGroups;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHelpCode() {
        return helpCode;
    }

    public void setHelpCode(String helpCode) {
        this.helpCode = helpCode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
