package com.example.midterm_26500_c.mapper;

import com.example.midterm_26500_c.dto.response.TaskResponse;
import com.example.midterm_26500_c.dto.response.TagResponse;
import com.example.midterm_26500_c.entity.Task;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final TagMapper tagMapper;

    public TaskResponse toTaskResponse(Task task) {
        Set<TagResponse> tagResponses = task.getTags()
                .stream()
                .map(tagMapper::toTagResponse)
                .collect(Collectors.toSet());

        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .dueDate(task.getDueDate())
                .userId(task.getUser().getId())
                .userEmail(task.getUser().getEmail())
                .tags(tagResponses)
                .createdAt(task.getCreatedAt())
                .build();
    }
}
