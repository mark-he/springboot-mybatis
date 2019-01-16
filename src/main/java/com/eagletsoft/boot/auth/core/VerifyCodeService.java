package com.eagletsoft.boot.auth.core;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eagletsoft.boot.auth.core.dao.AuthCodeDao;
import com.eagletsoft.boot.auth.core.model.VerifyCode;
import com.eagletsoft.boot.framework.data.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class VerifyCodeService extends BaseService<VerifyCode> {

	@Resource
	private AuthCodeDao inner;

	@Override
	protected BaseMapper<VerifyCode> getInner() {
		return inner;
	}

}
