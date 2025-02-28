package com.neelkanth.tracker.controller;

import com.neelkanth.tracker.dto.NoteDTO;
import com.neelkanth.tracker.model.Note;
import com.neelkanth.tracker.model.Tag;
import com.neelkanth.tracker.repository.TagRepository;
import com.neelkanth.tracker.service.NoteService;
import com.neelkanth.tracker.utils.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController(value = "/api")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/notes/")
    public ResponseEntity<List<Note>> getNote(){
       return ResponseEntity.ok(noteService.findAll());
    }

    @GetMapping("/note/{id}/")
    public ResponseEntity<NoteDTO> fetchNote(@PathVariable Long id){
        NoteDTO noteDTO = DTOConverter.convertToNoteDTO(noteService.fetchById(id));
        return ResponseEntity.ok(noteDTO);
    }

    @PostMapping("/note/")
    public ResponseEntity<Note> saveNote(@RequestBody Note noteObject){
        System.out.print("******");
        if( Objects.nonNull(noteObject.getTags())){
           return ResponseEntity.ok(createNoteWithTag(noteObject, ""));
        }
        return ResponseEntity.ok(noteService.save(noteObject));
    }

    @DeleteMapping("/note/{id}/")
    public ResponseEntity<Note> deleteNote(@PathVariable Long id){
        return ResponseEntity.ok(noteService.deleteNote(id));
    }

    public Note createNoteWithTag(Note noteObject, String tagName) {
        // Create or fetch the tag
        Tag tag = tagRepository.findByName(tagName)
                .orElseGet(() -> {
                    Tag newTag = new Tag();
                    newTag.setName(tagName);
                    return tagRepository.save(newTag);
                });

        // Create the note
        Note note = new Note();
        note.setTitle(noteObject.getTitle());
        note.setContent(note.getContent());
        note.getTags().add(tag); // Associate the tag with the note
        note.setLastModifiedBy(noteObject.getLastModifiedBy());
        note.setArchived(noteObject.isArchived());
        note.setPinned(noteObject.isPinned());
        note.setCreatedBy(noteObject.getCreatedBy());


        // Save the note
        return noteService.save(note);
    }
}
