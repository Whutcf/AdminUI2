package com.smic.cf.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable{

	private static final long serialVersionUID = -8262093903425648981L;
	@TableId(type=IdType.AUTO)
	private Integer roleId;
	private String roleName;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
	@TableField(fill=FieldFill.INSERT)
	private Date createTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
	@TableField(fill=FieldFill.INSERT_UPDATE)
	private Date updateTime;
	@TableField(fill=FieldFill.INSERT_UPDATE)
	private String updatePerson; 

}
