package com.manhui.easyexp.service.feedback;


import com.manhui.easyexp.entity.feedback.FeedbackExperimentStimulate;
import org.mics.token.model.CurrentUser;

import java.util.Map;

public interface FeedbackExperimentStimulateVoService {

    FeedbackExperimentStimulate saveAndEdit(FeedbackExperimentStimulate entity, CurrentUser currentUser);

    boolean del(String id);

    FeedbackExperimentStimulate getById(String id);

    Map<String, Object> importExcelDataFormat(String[][] data, String experimentType);
}
