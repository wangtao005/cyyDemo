package com.manhui.easyexp.dao.user;

import org.mics.jpa.repository.BaseRepository;

import com.manhui.easyexp.entity.user.request.UserDO;

public interface UserRepository extends BaseRepository<UserDO> {

	UserDO getByAccount(String account);
	
}
