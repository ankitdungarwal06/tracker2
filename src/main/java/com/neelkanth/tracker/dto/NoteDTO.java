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
    private Set<Long> tagIds;  // Use IDs instead of full Tag objects
    private Set<Long> userIds; // Use IDs instead of full User objects
    private boolean isArchived;
    private boolean isPinned;
    private String createdBy;
    private String lastModifiedBy;
}
