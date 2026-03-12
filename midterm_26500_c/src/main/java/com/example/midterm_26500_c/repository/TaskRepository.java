package com.example.midterm_26500_c.repository;

import com.example.midterm_26500_c.entity.Task;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
