package com.sprintforge.project.controller;

import com.sprintforge.project.dto.CreateProjectRequest;
import com.sprintforge.project.entity.Project;
import com.sprintforge.project.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public Project createProject(
            Authentication authenticaton,
            @Valid @RequestBody CreateProjectRequest request
    ) {
        //temporary owner id
        Long ownerId = (Long)authenticaton.getPrincipal();
        return projectService.createProject(request,ownerId);
    }

    @GetMapping("/my")
    public List<Project> getMyProjects(Authentication authentication) {
        Long ownerId = (Long)authentication.getPrincipal();
        return projectService.getProjectByOwner(ownerId);
    }
}
