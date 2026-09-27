package com.elearning.portal.service;

import java.util.Map;

public interface DashboardService {
    Map<String, Object> student();
    Map<String, Object> instructor();
    Map<String, Object> admin();
}
