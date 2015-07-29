package org.yws.cattle.repositories;

import org.springframework.data.repository.CrudRepository;
import org.yws.cattle.models.JobEntity;
import org.yws.cattle.models.JobHistoryEntity;

import java.util.List;

/**
 * Created by ywszjut on 15/7/27.
 */
public interface JobHistoryRepository extends CrudRepository<JobHistoryEntity,Long> {
    List<JobHistoryEntity> findByJob(JobEntity file);
}
