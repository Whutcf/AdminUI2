package com.smic.cf.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
public class User implements Serializable {

	private static final long serialVersionUID = -2317543120131319228L;
	@TableId(value="user_id",type=IdType.AUTO)
	private Integer userId;
	private String username;
	private String password;
	private String state;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
	@TableField(fill=FieldFill.INSERT)
	private Date createtime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
	@TableField(fill=FieldFill.INSERT_UPDATE)
	private Date updatetime;
	@TableField(fill=FieldFill.INSERT_UPDATE)
	private String updatePerson; 
	@TableField(exist=false)
	private ArrayList<Role> roles;
	
}
