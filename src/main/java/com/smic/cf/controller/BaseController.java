package com.smic.cf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("tgls/base")
public class BaseController {


	@GetMapping("/toBaseAdd")
	public String add() {
		log.info("进入表单含编辑器！");
		return "tgls/base/base_add";
	}

	@GetMapping("/toBaseList")
	public String list() {
		log.info("进入常用列表！");
		return "tgls/base/base_list";
	}

	@GetMapping("/toBaseCustomerList")
	public String customerList() {
		log.info("进入可新增的列表！");
		return "tgls/base/base_customList";
	}

	@GetMapping("/toBaseCustomerNewList")
	public String customerNewList() {
		log.info("进入可新增的列表(手写)！");
		return "tgls/base/base_customNewList";
	}

	@GetMapping("/toPages")
	public String pages() {
		log.info("进入页签切换Table！");
		return "tgls/base/pages";
	}
	
	@GetMapping("/toMaintain")
	public String maintainPage() {
		log.info("页面维护中！");
		return "maintain";
	}
	


}
