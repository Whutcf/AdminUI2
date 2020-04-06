package com.smic.cf.entities.pojo;

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
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

	private static final long serialVersionUID = -2317543120131319228L;
	@TableId(type=IdType.AUTO)
	private Integer userId;
	private String userName;
	private String password;
	private String state;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
	@TableField(fill=FieldFill.INSERT)
	private Date createTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
	@TableField(fill=FieldFill.INSERT_UPDATE)
	private Date updateTime;
	@TableField(fill=FieldFill.INSERT_UPDATE)
	private String updatePerson; 
	
}
