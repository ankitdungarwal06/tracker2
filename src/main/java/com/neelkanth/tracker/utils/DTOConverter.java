package com.neelkanth.tracker.utils;

import com.neelkanth.tracker.dto.NoteDTO;
import com.neelkanth.tracker.dto.TagDTO;
import com.neelkanth.tracker.model.Note;
import com.neelkanth.tracker.model.Tag;

import java.util.HashSet;
import java.util.Set;

public class DTOConverter {

    public static NoteDTO convertToNoteDTO(Note note) {
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setId(note.getId());
        noteDTO.setTitle(note.getTitle());
        noteDTO.setContent(note.getContent());
        noteDTO.setEmailContent(note.getEmailContent());
        noteDTO.setHashtags(note.getHashtags());
        noteDTO.setArchived(note.isArchived());
        noteDTO.setPinned(note.isPinned());
        noteDTO.setCreatedBy(note.getCreatedBy());
        noteDTO.setLastModifiedBy(note.getLastModifiedBy());

        Set<TagDTO> tagDTOs = new HashSet<>();
        for (Tag tag : note.getTags()) {
            TagDTO tagDTO = new TagDTO();
            tagDTO.setId(tag.getId());
            tagDTO.setName(tag.getName());
            tagDTOs.add(tagDTO);
        }
       // noteDTO.setHashtags(tagDTOs);

        return noteDTO;
    }
}
