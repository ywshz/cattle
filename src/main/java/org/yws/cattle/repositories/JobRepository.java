package org.yws.cattle.repositories;

import org.springframework.data.repository.CrudRepository;
import org.yws.cattle.models.FileEntity;
import org.yws.cattle.models.JobEntity;

/**
 * Created by ywszjut on 15/7/27.
 */
public interface JobRepository extends CrudRepository<JobEntity,Long> {
    JobEntity findOneByFile(FileEntity file);
}
