package org.konghao.shiro.service;

import org.konghao.shiro.dao.IResourceDao;
import org.konghao.shiro.model.Resource;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service("resourceService")
public class ResourceService implements IResourceService {
	@Inject
	private IResourceDao resourceDao;
	public void add(Resource res) {
		resourceDao.add(res);
	}

	public void update(Resource res) {
		resourceDao.update(res);
	}

	public void delete(int id) {
		resourceDao.delete(id);
	}

	public Resource load(int id) {
		return resourceDao.load(id);
	}

	public List<Resource> listResource() {
		return resourceDao.listResource();
	}
}
