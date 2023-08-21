package com.lengmu.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * permission
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Permission implements Serializable {
    private Integer id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限节点串
     */
    private String permission;

    private static final long serialVersionUID = 1L;
}