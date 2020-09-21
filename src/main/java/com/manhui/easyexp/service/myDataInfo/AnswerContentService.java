package com.manhui.easyexp.service.myDataInfo;


import com.manhui.easyexp.entity.myData.AnswerContent;
import org.mics.core.response.PageDataResponse;
import org.mics.token.model.CurrentUser;


public interface AnswerContentService {

    AnswerContent saveAndEdit(AnswerContent entity, CurrentUser currentUser);

    boolean del(String id);

    AnswerContent getById(String id);

    PageDataResponse listByPage(AnswerContent entity, Integer pageNo, Integer pageSize);
}
