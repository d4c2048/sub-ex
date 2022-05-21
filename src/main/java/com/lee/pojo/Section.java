package com.lee.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;

/**
 * (Section)实体类
 *
 * @author makejava
 * @since 2022 -05-14 21:42:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Section implements Serializable {

    private static final long serialVersionUID = -30169132038111718L;
    
    /**
     * section的编号
     */
    @TableId
    private Integer secId;
    /**
     * section中的学生
     */
    private Integer secStu;
    /**
     * section中的教师
     */
    private Integer secCos;

}

