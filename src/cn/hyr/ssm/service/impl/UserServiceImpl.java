package cn.hyr.ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import cn.hyr.ssm.exception.CustomException;
import cn.hyr.ssm.mapper.UserMapper;
import cn.hyr.ssm.model.User;
import cn.hyr.ssm.service.UserService;

public class UserServiceImpl implements UserService {

	// 注入mapper 相当于DAO层
	@Autowired
	private UserMapper userMapper;

	@Override
	public User findUserById(Integer id) throws Exception {

		User user = userMapper.selectByPrimaryKey(id);

		if (user == null) {
			throw new CustomException("用户不存在!");
		}

		return user;
	}

}
