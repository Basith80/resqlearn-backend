package com.sih.disasterplatform.disaster.service;

public interface SchoolDisasterAccessService {

    void unlock(Long schoolId, Long disasterId);

    boolean isUnlocked(Long schoolId, Long disasterId);
}
