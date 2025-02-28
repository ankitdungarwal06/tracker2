package com.neelkanth.tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NoteDTO {

    private Long id;
    private String title;
    private String content;
    private String emailContent;
    private Set<String> hashtags;
    private Set<TagDTO> tags;
    private boolean isArchived;
    private boolean isPinned;
    private String createdBy;
    private String lastModifiedBy;
}
