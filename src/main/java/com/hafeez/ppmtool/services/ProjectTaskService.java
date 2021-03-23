package com.hafeez.ppmtool.services;

import com.hafeez.ppmtool.domain.Project;
import com.hafeez.ppmtool.domain.ProjectTask;
import com.hafeez.ppmtool.exceptions.ProjectIdException;
import com.hafeez.ppmtool.exceptions.ProjectNotFoundException;
import com.hafeez.ppmtool.repositories.ProjectRepository;
import com.hafeez.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectTaskService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {

        Project project = projectService.findProjectByIdentifier(projectIdentifier,username);

        // TODO handle existing project task posting with id

        Integer projectSequence = project.getPTSequence();
        project.setPTSequence(++projectSequence);

        projectTask.setProject(project);
        projectTask.setProjectSequence(projectIdentifier + "-" + projectSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        projectTaskFieldDefVal(projectTask);

        return projectTaskRepository.save(projectTask);

    }

    private void projectTaskFieldDefVal(ProjectTask projectTask) {
        if (projectTask.getStatus() == null || projectTask.getStatus() == "") {
            projectTask.setStatus("TO_DO");
        }

        if (projectTask.getPriority() == null ) { //In the future we need projectTask.getPriority()== 0 to handle the form
            projectTask.setPriority(3);
        }
    }

    public Iterable<ProjectTask> findProjectTaskByProjectId(String id, String username) {

        projectService.findProjectByIdentifier(id, username);
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String projectIdentifier, String ptId, String username) {

        projectService.findProjectByIdentifier(projectIdentifier, username);

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(ptId);
        if(projectTask == null) {
            throw new ProjectNotFoundException("Project Task '"+ ptId +"' not found");
        }

        if(!projectTask.getProjectIdentifier().equals(projectIdentifier)) {
            throw new ProjectNotFoundException("Project Task '" + ptId + "' does not exist in project: '" + projectIdentifier);
        }

        return projectTask;
    }

    // update project task
    // find existing project task
    // replace it with updated task
    // save update
    public ProjectTask updateByProjectSequence(String projectId, ProjectTask updatedTask, String ptId, String username) {
        findPTByProjectSequence(projectId, ptId, username);
        projectTaskFieldDefVal(updatedTask);
        if(updatedTask.getId() != null ) {
            Optional<ProjectTask> existingProjectTask = projectTaskRepository.findById(updatedTask.getId());
            existingProjectTask.orElseThrow(() -> new ProjectIdException("Project Task with " + updatedTask.getId() + " cannot be updated because it does not exist"));
        }
        return projectTaskRepository.save(updatedTask);
    }

    public void deletePTByProjectSequence(String projectId, String ptId, String username) {
        ProjectTask projectTask = findPTByProjectSequence(projectId, ptId, username);
        projectTaskRepository.delete(projectTask);

    }
}