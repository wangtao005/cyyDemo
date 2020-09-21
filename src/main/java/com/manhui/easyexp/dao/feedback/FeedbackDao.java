package com.manhui.easyexp.dao.feedback;

import com.manhui.easyexp.entity.feedback.Feedback;
import org.mics.jpa.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackDao extends BaseRepository<Feedback> {

}
