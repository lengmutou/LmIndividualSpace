package com.lengmu.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * role
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Role implements Serializable {
    private Integer id;

    /**
     * 身份名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 生效状态
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}