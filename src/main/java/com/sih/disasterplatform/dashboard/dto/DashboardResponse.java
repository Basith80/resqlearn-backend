package com.sih.disasterplatform.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DashboardResponse {

    private long totalDisasters;
    private long completedEducation;
    private long unlockedDisasters;
}
