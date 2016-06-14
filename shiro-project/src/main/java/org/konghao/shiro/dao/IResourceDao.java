package org.konghao.shiro.dao;

import com.hust.shiro.hibernate.dao.IBaseDao;
import org.konghao.shiro.model.Resource;

import java.util.List;

public interface IResourceDao extends IBaseDao<Resource> {
    public List<Resource> listResource();
}
