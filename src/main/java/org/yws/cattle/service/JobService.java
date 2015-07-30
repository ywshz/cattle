package org.yws.cattle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yws.cattle.models.FileEntity;
import org.yws.cattle.models.JobEntity;
import org.yws.cattle.models.JobHistoryEntity;
import org.yws.cattle.repositories.FileRepository;
import org.yws.cattle.repositories.JobHistoryRepository;
import org.yws.cattle.repositories.JobRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by ywszjut on 15/7/27.
 */
@Component("jobService")
@Transactional
public class JobService {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private JobHistoryRepository jobHistoryRepository;

    public JobEntity getByJobId(Long jobId) {
        return jobRepository.findOne(jobId);
    }

    public JobEntity getByFileId(Long fileId) {
        return jobRepository.findOneByFile(new FileEntity(fileId));
    }

    public List<JobHistoryEntity> getJobHistoryList(Long jobId) {
        return jobHistoryRepository.findByJob(new JobEntity(jobId));
    }

    public void updateJob(JobEntity modifiedJob) {
        jobRepository.save(modifiedJob);
    }

    public void delete(Long jobId) {
        JobEntity job = jobRepository.findOne(jobId);
        jobRepository.delete(job);
    }
}
