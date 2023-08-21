package com.lengmu.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * article
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)

public class Article implements Serializable {
    private Integer id;

    /**
     * 发布用户
     */
    private Integer userId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标识
     */
    private Integer deleted;

    private static final long serialVersionUID = 1L;
}