package com.example.midterm_26500_c.dto.response;

import com.example.midterm_26500_c.enums.TaskStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaskResponse {
    private UUID id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate;
    private UUID userId;
    private String userEmail;
    private Set<TagResponse> tags;
    private LocalDateTime createdAt;
}
