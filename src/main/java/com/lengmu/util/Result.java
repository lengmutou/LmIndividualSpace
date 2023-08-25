package com.lengmu.util;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "响应集")
public class Result {
    @ApiModelProperty(value ="响应码")
    private Integer code;
    @ApiModelProperty(value ="返回的消息提示")
    private String msg;
    @ApiModelProperty(value ="返回的数据长度")
    private Integer count;
    @ApiModelProperty(value ="返回的数据")
    private Object data;
    @ApiModelProperty(value ="登录状态Token")
    private String token;
    public static Result success(String msg,Integer count,Object data){
        return new Result().setCode(0).setMsg(msg).setCount(count).setData(data);
    }
    public static Result success(Integer count,Object data){
        return new Result().setCode(0).setCount(count).setData(data);
    }
    public static Result success(Object data){
        return new Result().setCode(0).setData(data);
    }
    public static Result success(){
        return new Result().setCode(0);
    }
    public static Result success(String token){
        return new Result().setCode(0).setToken(token);
    }
    public static Result success(String token,String msg){
        return new Result().setCode(0).setToken(token).setMsg(msg);
    }
    public static Result failed(){
        return new Result().setCode(2);
    }
    public static Result failed(String msg){
        return new Result().setCode(2).setMsg(msg);
    }
}
