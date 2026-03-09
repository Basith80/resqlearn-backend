package com.sih.disasterplatform.service.assessment;

import com.sih.disasterplatform.entity.Question;
import com.sih.disasterplatform.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question save(Question question) {
        return questionRepository.save(question);
    }

    public List<Question> getByAssessmentId(Long assessmentId) {
        return questionRepository.findByAssessment_Id(assessmentId);
    }

    public void delete(Long id) {
        questionRepository.deleteById(id);
    }
}