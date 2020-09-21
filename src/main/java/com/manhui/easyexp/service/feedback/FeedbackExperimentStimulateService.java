package com.manhui.easyexp.service.feedback;


import com.manhui.easyexp.entity.feedback.FeedbackExperimentStimulate;
import org.mics.token.model.CurrentUser;


public interface FeedbackExperimentStimulateService {

    FeedbackExperimentStimulate saveAndEdit(FeedbackExperimentStimulate entity, CurrentUser currentUser);

    boolean del(String id);

    FeedbackExperimentStimulate getById(String id);


}
