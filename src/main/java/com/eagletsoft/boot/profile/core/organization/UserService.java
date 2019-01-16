package com.eagletsoft.boot.profile.core.organization;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eagletsoft.boot.auth.core.AuthUserService;
import com.eagletsoft.boot.framework.common.session.UserInterface;
import com.eagletsoft.boot.framework.common.session.UserSession;
import com.eagletsoft.boot.framework.common.utils.PasswordUtils;
import com.eagletsoft.boot.framework.data.service.BaseService;
import com.eagletsoft.boot.profile.core.organization.dao.PositionDao;
import com.eagletsoft.boot.profile.core.organization.dao.PositionPermissionDao;
import com.eagletsoft.boot.profile.core.organization.dao.UserAuthDao;
import com.eagletsoft.boot.profile.core.organization.dao.UserDao;
import com.eagletsoft.boot.profile.core.organization.model.Permission;
import com.eagletsoft.boot.profile.core.organization.model.Position;
import com.eagletsoft.boot.profile.core.organization.model.User;
import com.eagletsoft.boot.profile.core.organization.model.UserAuth;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class UserService extends BaseService<User> implements AuthUserService {

	@Resource
	private UserDao inner;

	@Resource
	private UserAuthDao authDao;
	@Resource
	private PositionDao positionDao;

	@Resource
	private PositionPermissionDao positionPermissionDao;

	@Override
	protected BaseMapper<User> getInner() {
		return inner;
	}

	@Override
	public UserSession.Authorize<UserInterface> findAuthorize(UserInterface user, String token) {
		UserSession.Authorize auth = new UserSession.Authorize();
		auth.setToken(token);
		auth.setUser(user);
		auth.setPermissions(findPermissions((User)user));
		return auth;
	}

	public User findByLoginName(String loginName) {
		return this.inner.findByLoginName(loginName);
	}

	public boolean validateWithCode(String id, String password) {
		String encripted = PasswordUtils.encript(password);
		UserAuth auth = authDao.findByUserId(id);
		return encripted.equals(auth.getPassword());
	}

	private Collection<String> findPermissions(User user) {
		List<String> perms = new ArrayList<>();

		if (null != user.getPositionId()) {
			Position position = positionDao.selectById(user.getPositionId());
			if (Position.States.ENABLED.equals(position.getState())) {
				Collection<Permission> colPerms = positionPermissionDao.findByPositionId(user.getPositionId());

				for (Permission p : colPerms) {
					perms.add(p.getUri());
				}
			}
		}
		return perms;
	}
}
