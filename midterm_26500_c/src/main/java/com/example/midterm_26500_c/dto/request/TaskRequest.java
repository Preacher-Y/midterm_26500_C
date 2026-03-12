package com.example.midterm_26500_c.dto.request;

import com.example.midterm_26500_c.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {

    @NotBlank(message = "Task title is required")
    private String title;

    private String description;

    @NotNull(message = "Task status is required")
    private TaskStatus status;

    private LocalDate dueDate;

    @NotNull(message = "User ID is required")
    private UUID userId;

    private Set<UUID> tagIds;
}
