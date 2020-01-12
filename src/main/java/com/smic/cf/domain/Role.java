package com.smic.cf.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role")
public class Role implements Serializable{

	private static final long serialVersionUID = -8262093903425648981L;
	@TableId("role_id")
	private Integer roleId;
	private String roleName;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
	@TableField(fill=FieldFill.INSERT)
	private Date createtime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
	@TableField(fill=FieldFill.INSERT_UPDATE)
	private Date updatetime;
	@TableField(fill=FieldFill.INSERT_UPDATE)
	private String updatePerson; 
	@TableField(exist=false)
	private ArrayList<User> users;

}
