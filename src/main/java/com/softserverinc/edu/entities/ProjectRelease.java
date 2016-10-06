package com.softserverinc.edu.entities;

import com.softserverinc.edu.entities.enums.ReleaseStatus;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Indexed
public class ProjectRelease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;

    @NotEmpty(message = "")
    @Size(max = 32, message = "")
    @Column(nullable = false, length = 32)
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String version;

    @Column(nullable = false, length = 11)
    @Enumerated(EnumType.STRING)
    private ReleaseStatus releaseStatus = ReleaseStatus.OPEN;

    @Column(length = 10000)
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String description;

    @OneToMany(mappedBy = "projectRelease")
    private Set<Issue> issues = new HashSet<>();

    public ProjectRelease() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ReleaseStatus getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(ReleaseStatus releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Issue> getIssues() {
        return issues;
    }

    public void setIssues(Set<Issue> issues) {
        this.issues = issues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ProjectRelease that = (ProjectRelease) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("version", version)
                .toString();
    }

}