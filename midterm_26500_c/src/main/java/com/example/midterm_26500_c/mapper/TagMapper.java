package com.example.midterm_26500_c.mapper;

import com.example.midterm_26500_c.dto.response.TagResponse;
import com.example.midterm_26500_c.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    public TagResponse toTagResponse(Tag tag) {
        return TagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
}
