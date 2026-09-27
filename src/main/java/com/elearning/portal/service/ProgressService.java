package com.elearning.portal.service;

import com.elearning.portal.dto.request.ProgressRequest;
import com.elearning.portal.dto.response.ProgressResponse;

import java.util.List;

public interface ProgressService {
    ProgressResponse markProgress(ProgressRequest request);
    List<ProgressResponse> courseProgress(Long courseId);
}
