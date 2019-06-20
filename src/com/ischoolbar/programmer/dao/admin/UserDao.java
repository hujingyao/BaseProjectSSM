package com.ischoolbar.programmer.dao.admin;

import org.springframework.stereotype.Repository;

import com.ischoolbar.programmer.entity.admin.User;

@Repository
public interface UserDao {
	public User findByUsername(String username);
}
