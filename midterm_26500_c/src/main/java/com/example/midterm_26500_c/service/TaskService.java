package com.example.midterm_26500_c.service;

import com.example.midterm_26500_c.dto.request.TaskRequest;
import com.example.midterm_26500_c.dto.response.TaskResponse;
import com.example.midterm_26500_c.entity.Tag;
import com.example.midterm_26500_c.entity.Task;
import com.example.midterm_26500_c.entity.User;
import com.example.midterm_26500_c.mapper.TaskMapper;
import com.example.midterm_26500_c.repository.TagRepository;
import com.example.midterm_26500_c.repository.TaskRepository;
import com.example.midterm_26500_c.repository.UserRepository;
import com.example.midterm_26500_c.util.PageResponse;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final TaskMapper taskMapper;

    public TaskResponse create(TaskRequest request) {
        User user = findUser(request.getUserId());
        Set<Tag> tags = resolveTags(request.getTagIds());

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .dueDate(request.getDueDate())
                .user(user)
                .tags(tags)
                .build();

        return taskMapper.toTaskResponse(taskRepository.save(task));
    }

    public PageResponse<TaskResponse> getAll(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Page<Task> tasks = taskRepository.findAll(PageRequest.of(page, size, sort));

        return PageResponse.<TaskResponse>builder()
                .content(tasks.getContent().stream().map(taskMapper::toTaskResponse).toList())
                .page(tasks.getNumber())
                .size(tasks.getSize())
                .totalElements(tasks.getTotalElements())
                .totalPages(tasks.getTotalPages())
                .last(tasks.isLast())
                .build();
    }

    public TaskResponse getById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found with id: " + id));
        return taskMapper.toTaskResponse(task);
    }

    public TaskResponse update(Long id, TaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found with id: " + id));

        User user = findUser(request.getUserId());
        Set<Tag> tags = request.getTagIds() == null ? task.getTags() : resolveTags(request.getTagIds());

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());
        task.setUser(user);
        task.setTags(tags);

        return taskMapper.toTaskResponse(taskRepository.save(task));
    }

    public void delete(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found with id: " + id));
        taskRepository.delete(task);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));
    }

    private Set<Tag> resolveTags(Set<Long> tagIds) {
        Set<Tag> tags = new HashSet<>();
        if (tagIds == null || tagIds.isEmpty()) {
            return tags;
        }
        for (Long tagId : tagIds) {
            Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new NoSuchElementException("Tag not found with id: " + tagId));
            tags.add(tag);
        }
        return tags;
    }
}
