package com.lengmu.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * role_permission
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RolePermission implements Serializable {
    private Integer id;

    /**
     * 身份ID
     */
    private Integer roleId;

    /**
     * 权限ID
     */
    private Integer permissionId;

    private static final long serialVersionUID = 1L;
}