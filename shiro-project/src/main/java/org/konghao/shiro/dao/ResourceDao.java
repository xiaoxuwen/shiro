package org.konghao.shiro.dao;

import com.hust.shiro.hibernate.dao.BaseDao;
import org.konghao.shiro.model.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("resourceDao")
public class ResourceDao extends BaseDao<Resource> implements IResourceDao {

    public List<Resource> listResource() {
        return super.list("from Resource");
    }

}
