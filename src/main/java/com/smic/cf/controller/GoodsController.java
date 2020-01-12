package com.smic.cf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("tgls/goodsManage")
public class GoodsController {

	@GetMapping("/toGoodsAdd")
	public String toGoodsAdd() {
		log.info("进入商品添加页面");
		return "tgls/goodsManage/goods_add";
	}

	@GetMapping("/toGoodsList")
	public String toGoodsList() {
		log.info("进入商品列表页面");
		return "tgls/goodsManage/goods_list";
	}
	
	@GetMapping("/toGoodsUpdate")
	public String toGoodsUpdate() {
		log.info("进入商品更新页面");
		return "tgls/goodsManage/goods_update";
	}
	

	@GetMapping("/toGoodsTypeList")
	public String toGoodsTypeList() {
		log.info("进入商品类型列表页面");
		return "tgls/goodsManage/goodsType_list";
	}
	

	@GetMapping("/toSpecificationsList")
	public String toSpecificationsList() {
		log.info("进入特殊商品列表页面");
		return "tgls/goodsManage/specifications_list";
	}

	@GetMapping("/toSpecificationsList1")
	public String toSpecificationsList1() {
		log.info("进入特殊商品列表页面1");
		return "tgls/goodsManage/specifications_list1";
	}

}
