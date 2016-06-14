package org.konghao.shiro.service;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.konghao.shiro.dao.IRoleDao;
import org.konghao.shiro.dao.IUserDao;
import org.konghao.shiro.kit.ShiroKit;
import org.konghao.shiro.model.Resource;
import org.konghao.shiro.model.Role;
import org.konghao.shiro.model.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service("userService")
public class UserService implements IUserService {
	@Inject
	private IUserDao userDao;
	@Inject
	private IRoleDao roleDao;
	
	public void add(User user) {
		if(ShiroKit.isEmpty(user.getUsername())||ShiroKit.isEmpty(user.getPassword())) {
			throw new RuntimeException("用户名或者密码不能为空！");
		}
		user.setPassword(ShiroKit.md5(user.getPassword(), user.getUsername()));
		userDao.add(user);
	}

	public void delete(int id) {
		userDao.delete(id);
	}

	public void update(User user,List<Integer> rids) {
		roleDao.deleteUserRoles(user.getId());
		for(int rid:rids) {
			roleDao.addUserRole(user.getId(), rid);
		}
		userDao.update(user);
	}
	
	public void update(User user) {
		userDao.update(user);
	}

	public User load(int id) {
		return userDao.load(id);
	}

	public User loadByUsername(String username) {
		return userDao.loadByUsername(username);
	}

	public User login(String username, String password) {
		User u = userDao.loadByUsername(username);
		if(u==null) throw new UnknownAccountException("用户名或者密码出错");
		if(!u.getPassword().equals(ShiroKit.md5(password, username)))
			throw new IncorrectCredentialsException("用户名或者密码出错");
		if(u.getStatus()==0) throw new LockedAccountException("用户已经被锁定");
		return u;
	}

	public List<User> list() {
		return userDao.listUser();
	}

	public List<User> listByRole(int id) {
		return userDao.listByRole(id);
	}

	public List<Resource> listAllResource(int uid) {
		return userDao.listAllResource(uid);
	}

	public void add(User user, List<Integer> rids) {
		this.add(user);
		for(int rid:rids) {
			roleDao.addUserRole(user.getId(), rid);
		}
	}

	@Override
	public List<String> listRoleSnByUser(int uid) {
		return userDao.listRoleSnByUser(uid);
	}

	@Override
	public List<Role> listUserRole(int uid) {
		return userDao.listUserRole(uid);
	}

}
