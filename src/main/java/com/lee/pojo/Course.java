package com.lee.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.*;

import java.io.Serializable;

/**
 * (Course)实体类
 *
 * @author makejava
 * @since 2022 -05-13 20:09:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course implements Serializable {

    private static final long serialVersionUID = 121480512686513251L;
    
    /**
     * 课程编号
     */
    @TableId
    private Integer cosId;
    /**
     * 课程名称
     */
    private String cosName;
    /**
     * 授课教师编号
     */
    private Integer cosTch;
    /**
     * 授课教师名称
     */
    @TableField(exist = false)
    private String cosTchName;
    /**
     * 课程描述
     */
    private String cosDcp;
    /**
     * 当前选课人数
     */
    private Integer cosNow;
    /**
     * 课程最大人数
     */
    private Integer cosAll;
    /**
     * 课程图片名称
     */
    private String cosFile;

}

