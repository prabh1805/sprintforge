package com.sprintforge.project.service;

import com.sprintforge.project.dto.CreateProjectRequest;
import com.sprintforge.project.entity.Project;
import com.sprintforge.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public Project createProject(CreateProjectRequest request, Long ownerId) {
        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .projectKey(request.getProjectKey())
                .createdAt(LocalDateTime.now())
                .ownerId(ownerId)
                .build();
        return projectRepository.save(project);
    }

    public List<Project> getProjectByOwner(Long ownerId) {
        return projectRepository.findByOwnerId(ownerId);
    }
}
