package com.manhui.easyexp.dao.vip;

import org.mics.jpa.repository.BaseRepository;

import com.manhui.easyexp.entity.vip.request.VipLevelDO;

public interface VipLevelRepository extends BaseRepository<VipLevelDO> {

	VipLevelDO getByLevel(Byte level);
	
	VipLevelDO getByType(Byte type);
}
