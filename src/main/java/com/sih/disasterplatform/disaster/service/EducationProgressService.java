package com.sih.disasterplatform.disaster.service;

public interface EducationProgressService {

    void markCompleted(Long userId, Long disasterId);

    boolean isCompleted(Long userId, Long disasterId);

    long completedCount(Long userId);
}
