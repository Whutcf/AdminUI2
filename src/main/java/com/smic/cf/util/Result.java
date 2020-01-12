package com.smic.cf.util;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 
 * @Description: :返回对象实体 code为状态码、msg为提示信息、data为返回的数据
 * @author cai feng
 * @date 2019年7月17日
 */
@Data
public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	public Integer code = 0;
	private Integer count;
	private String msg ;
	private List<T> data;
	
}
