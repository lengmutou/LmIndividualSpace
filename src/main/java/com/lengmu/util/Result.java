package com.lengmu.util;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Result {
    private Integer code;
    private String msg;
    private Integer count;
    private Object data;
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
