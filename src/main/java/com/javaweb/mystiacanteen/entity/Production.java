package com.javaweb.mystiacanteen.entity;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public abstract class Production {
    protected Integer id;//ID
    protected String name;//产品名
    protected String description;//产品描述
    protected Integer price;//产品价格
    protected String image;//产品图片
    protected String position;//产品位置
    protected long click;//点击次数
    protected long buy;//购买次数
    protected String type;//产品类型

    public void setTags(JsonNode tags){};
    public JsonNode getTags(){return null;};
}
