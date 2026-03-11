package com.example.midterm_26500_c.service;

import com.example.midterm_26500_c.dto.request.TagRequest;
import com.example.midterm_26500_c.dto.response.TagResponse;
import com.example.midterm_26500_c.entity.Tag;
import com.example.midterm_26500_c.mapper.TagMapper;
import com.example.midterm_26500_c.repository.TagRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagResponse create(TagRequest request) {
        if (tagRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Tag name already exists");
        }
        Tag tag = Tag.builder().name(request.getName()).build();
        return tagMapper.toTagResponse(tagRepository.save(tag));
    }

    public List<TagResponse> getAll() {
        return tagRepository.findAll().stream().map(tagMapper::toTagResponse).toList();
    }

    public TagResponse getById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tag not found with id: " + id));
        return tagMapper.toTagResponse(tag);
    }

    public TagResponse update(Long id, TagRequest request) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tag not found with id: " + id));

        if (!tag.getName().equals(request.getName()) && tagRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Tag name already exists");
        }

        tag.setName(request.getName());
        return tagMapper.toTagResponse(tagRepository.save(tag));
    }

    public void delete(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tag not found with id: " + id));
        tagRepository.delete(tag);
    }
}
