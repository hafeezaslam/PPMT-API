package com.hafeez.ppmtool.controller;

import com.hafeez.ppmtool.domain.ProjectTask;
import com.hafeez.ppmtool.services.MapValidationErrorService;
import com.hafeez.ppmtool.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/projectTask")
@CrossOrigin
public class ProjectTaskController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{projectId}")
    public ResponseEntity<?> addPTtoProject(@Valid @RequestBody ProjectTask projectTask,
                                            BindingResult result, @PathVariable String projectId, Principal principal) {

        Optional<ResponseEntity<?>> errorMap = mapValidationErrorService.mapValidationService(result);
        return errorMap.orElseGet(() -> {
            ProjectTask projectTask1 = projectTaskService.addProjectTask(projectId, projectTask, principal.getName());
            return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
        });
    }

    @GetMapping("/{projectId}")
    public Iterable<ProjectTask> getProjectTasks(@PathVariable String projectId, Principal principal) {
        return projectTaskService.findProjectTaskByProjectId(projectId, principal.getName());
    }

    @GetMapping("/{projectId}/{ptId}")
    public ResponseEntity<?> getProjectTask(@PathVariable String projectId, @PathVariable String ptId, Principal principal) {
        ProjectTask projectTask = projectTaskService.findPTByProjectSequence(projectId, ptId, principal.getName());
        return new ResponseEntity<>(projectTask, HttpStatus.OK);
    }

    @PatchMapping("/{projectId}/{ptId}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String projectId, @PathVariable String ptId, Principal principal) {
        Optional<ResponseEntity<?>> errorMap = mapValidationErrorService.mapValidationService(result);
        return errorMap.orElseGet(() -> {
            ProjectTask updatedTask = projectTaskService.updateByProjectSequence(projectId, projectTask, ptId, principal.getName());
            return new ResponseEntity<ProjectTask>(updatedTask, HttpStatus.CREATED);
        });
    }

    @DeleteMapping("/{projectId}/{ptId}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String projectId, @PathVariable String ptId, Principal principal) {
        projectTaskService.deletePTByProjectSequence(projectId, ptId, principal.getName());
        return new ResponseEntity<String>("Project task : '" + ptId + "' was deleted", HttpStatus.OK);

    }
}
