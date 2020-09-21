package com.manhui.easyexp.service.feedback;


import com.manhui.easyexp.entity.feedback.Feedback;
import org.mics.core.response.PageDataResponse;
import org.mics.token.model.CurrentUser;


public interface FeedbackService {

    Feedback saveAndEdit(Feedback entity, CurrentUser currentUser);

    boolean del(String id);

    Feedback getById(String id);

    PageDataResponse listByPage(Feedback entity, Integer pageNo, Integer pageSize, CurrentUser currentUser);
}
