package com.smic.cf.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

/**
 * 添加注释
 * @author 蔡明涛
 * @date 2020.03.04 23:38
 **/
@Data
public class UserRole implements Serializable {
	
	private static final long serialVersionUID = 1719056360399156720L;
	@TableId(type=IdType.AUTO)
	private Integer id;
	private Integer userId;
	private Integer roleId;

}
