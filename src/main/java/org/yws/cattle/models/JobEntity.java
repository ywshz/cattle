package org.yws.cattle.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by ywszjut on 15/7/25.
 */
@Entity
@Table(name = "job", schema = "", catalog = "cattle")
public class JobEntity {
    private long id;
    private String name;
    private JobType jobType;
    private ScheduleType scheduleType;
    private ScheduleStatus scheduleStatus;
    private String cron;
    private String dependencies;
    private String script;
    private Timestamp createTime;

    private FileEntity folder;

    private List<JobEntity> dependencyList;

    private List<JobHistoryEntity> jobHistories;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "job_type")
    @Enumerated(EnumType.ORDINAL)
    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    @Basic
    @Column(name = "schedule_type")
    @Enumerated(EnumType.ORDINAL)
    public ScheduleType getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(ScheduleType scheduleType) {
        this.scheduleType = scheduleType;
    }

    @Basic
    @Column(name = "schedule_status")
    @Enumerated(EnumType.ORDINAL)
    public ScheduleStatus getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(ScheduleStatus scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    @Basic
    @Column(name = "cron")
    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    @Basic
    @Column(name = "dependencies")
    public String getDependencies() {
        return dependencies;
    }

    public void setDependencies(String dependencies) {
        this.dependencies = dependencies;
    }

    @Basic
    @Column(name = "script")
    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @ManyToOne(targetEntity=FileEntity.class,fetch = FetchType.LAZY,cascade=CascadeType.REMOVE)
    @JoinColumn(name="file_id", referencedColumnName="id",nullable=false)
    public FileEntity getFolder() {
        return folder;
    }

    public void setFolder(FileEntity folder) {
        this.folder = folder;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "job")
    public List<JobHistoryEntity> getJobHistories() {
        return jobHistories;
    }

    public void setJobHistories(List<JobHistoryEntity> jobHistories) {
        this.jobHistories = jobHistories;
    }

    @Transient
    public List<JobEntity> getDependencyList() {
        return dependencyList;
    }

    public void setDependencyList(List<JobEntity> dependencyList) {
        this.dependencyList = dependencyList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobEntity jobEntity = (JobEntity) o;

        if (id != jobEntity.id) return false;
        if (jobType != jobEntity.jobType) return false;
        if (scheduleStatus != jobEntity.scheduleStatus) return false;
        if (scheduleType != jobEntity.scheduleType) return false;
        if (createTime != null ? !createTime.equals(jobEntity.createTime) : jobEntity.createTime != null) return false;
        if (cron != null ? !cron.equals(jobEntity.cron) : jobEntity.cron != null) return false;
        if (dependencies != null ? !dependencies.equals(jobEntity.dependencies) : jobEntity.dependencies != null)
            return false;
        if (name != null ? !name.equals(jobEntity.name) : jobEntity.name != null) return false;
        if (script != null ? !script.equals(jobEntity.script) : jobEntity.script != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + jobType.ordinal();
        result = 31 * result + scheduleType.ordinal();
        result = 31 * result + scheduleStatus.ordinal();
        result = 31 * result + (cron != null ? cron.hashCode() : 0);
        result = 31 * result + (dependencies != null ? dependencies.hashCode() : 0);
        result = 31 * result + (script != null ? script.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
