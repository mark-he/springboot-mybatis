package com.eagletsoft.boot.auth.core;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eagletsoft.boot.auth.core.dao.ClientDao;
import com.eagletsoft.boot.auth.core.model.Client;
import com.eagletsoft.boot.framework.data.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class ClientService extends BaseService<Client> {

	@Resource
	private ClientDao inner;

	@Override
	protected BaseMapper<Client> getInner() {
		return inner;
	}

	public Client findByName(String name) {
		return this.inner.findByName(name);
	}
}
