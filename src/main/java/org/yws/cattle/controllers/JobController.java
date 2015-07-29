package org.yws.cattle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yws.cattle.models.JobEntity;
import org.yws.cattle.models.JobHistoryEntity;
import org.yws.cattle.service.JobService;

import java.util.List;

/**
 * Created by ywszjut on 15/7/25.
 */
@Controller
@RequestMapping("/job")
public class JobController {
    @Autowired
    private JobService jobService;

    @RequestMapping(value = "get_by_file_id")
    @ResponseBody
    public JobEntity get_by_file_id(Long fileId) {
        return jobService.getByFileId(fileId);
    }

    @RequestMapping(value = "get_history_list")
    @ResponseBody
    public List<JobHistoryEntity> get_history_list(Long jobId) {
        return jobService.getJobHistoryList(jobId);
    }
}
