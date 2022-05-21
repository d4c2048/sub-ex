package com.lee.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;

/**
 * (Student)实体类
 *
 * @author Lee
 * @since 2022 -05-12 13:49:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {

    private static final long serialVersionUID = 790496744781412071L;
    
    /**
     * 学生学号
     */
    @TableId
    private Integer stuId;
    /**
     * 学生姓名
     */
    private String stuName;
    /**
     * 学生大学年级
     */
    private Integer level;
    /**
     * 账号密码
     */
    @TableField(select = false)
    private String pwd;

}

