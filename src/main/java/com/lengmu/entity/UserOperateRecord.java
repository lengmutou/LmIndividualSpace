package com.lengmu.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * user_operate_record
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserOperateRecord implements Serializable {
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 文章ID
     */
    private Integer articleId;

    /**
     * 操作
     */
    private String operate;

    /**
     * 操作时间
     */
    private Date operateTime;

    private static final long serialVersionUID = 1L;
}