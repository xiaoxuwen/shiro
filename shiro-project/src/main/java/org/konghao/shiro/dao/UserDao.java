package org.konghao.shiro.dao;

import com.hust.shiro.hibernate.dao.BaseDao;
import org.konghao.shiro.model.Resource;
import org.konghao.shiro.model.Role;
import org.konghao.shiro.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public class UserDao extends BaseDao<User> implements IUserDao {


    public List<User> listUser() {
        return super.list("from User");
    }

    public User loadByUsername(String username) {
        return (User) super.queryObject("from User where username=?", username);
    }

    public List<User> listByRole(int id) {
        String hql = "select u from User u,Role r,UserRole ur where u.id=ur.userId and r.id=ur.roleId and r.id=?";
        return (List<User>) super.listObj(hql, id);
    }

    public List<Resource> listAllResource(int uid) {
        String hql = "select res from User u,Resource res,UserRole ur,RoleResource rr where " +
                "u.id=ur.userId and ur.roleId=rr.roleId  and rr.resId=res.id and u.id=?";
        return (List<Resource>) super.listObj(hql, uid);
    }

    @Override
    public List<String> listRoleSnByUser(int uid) {
        String hql = "select r.sn from UserRole ur,Role r,User u where u.id=ur.userId and r.id=ur.roleId and u.id=?";
        return (List<String>) super.listObj(hql, uid);
    }

    @Override
    public List<Role> listUserRole(int uid) {
        String hql = "select r from UserRole ur,Role r,User u where u.id=ur.userId and r.id=ur.roleId and u.id=?";
        return (List<Role>) super.listObj(hql, uid);
    }

}
