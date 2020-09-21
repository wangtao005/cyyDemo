package com.manhui.easyexp.service.myDataInfo;


import com.manhui.easyexp.entity.myData.MyDataInfo;
import org.mics.core.response.PageDataResponse;
import org.mics.token.model.CurrentUser;

import java.util.List;


public interface MyDataInfoService {

    MyDataInfo saveAndEdit(MyDataInfo entity, CurrentUser currentUser);

    boolean del(String id);

    MyDataInfo getById(String id);

    PageDataResponse listByPage(MyDataInfo entity, Integer pageNo, Integer pageSize);

    List<MyDataInfo> listData(MyDataInfo entity,Integer size);

    List<MyDataInfo> getByFeedbackId(String id);

    boolean delByFeedBackId(String id);
}
