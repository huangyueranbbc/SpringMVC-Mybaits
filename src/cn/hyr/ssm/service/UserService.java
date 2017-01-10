package cn.hyr.ssm.service;

import cn.hyr.ssm.model.User;

/**
 * 商品管理
 *
 */
public interface UserService {

	public User findUserById(Integer id) throws Exception;

}
