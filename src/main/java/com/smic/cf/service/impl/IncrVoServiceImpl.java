package com.smic.cf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smic.cf.entities.vo.IncrVo;
import com.smic.cf.mapper.IncrVoMapper;
import com.smic.cf.service.IncrVoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @ClassName IncrVoServiceImpl
 * @Author 蔡明涛
 * @Date 2020/4/6 18:14
 **/
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class IncrVoServiceImpl extends ServiceImpl<IncrVoMapper, IncrVo> implements IncrVoService {
}
