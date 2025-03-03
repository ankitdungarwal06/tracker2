package com.neelkanth.tracker.controller;

import com.neelkanth.tracker.dto.NoteDTO;
import com.neelkanth.tracker.model.Note;
import com.neelkanth.tracker.model.Tag;
import com.neelkanth.tracker.model.User;
import com.neelkanth.tracker.repository.TagRepository;
import com.neelkanth.tracker.repository.UserRepository;
import com.neelkanth.tracker.service.NoteService;
import com.neelkanth.tracker.utils.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController(value = "/api")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

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
    public ResponseEntity<Note> saveNote(@RequestBody NoteDTO noteDTO){
        System.out.print("******");
        // Map DTO to entity
        Note note = new Note();
        note.setTitle(noteDTO.getTitle());
        note.setContent(noteDTO.getContent());
        note.setEmailContent(noteDTO.getEmailContent());
        note.setHashtags(noteDTO.getHashtags());
        note.setArchived(noteDTO.isArchived());
        note.setPinned(noteDTO.isPinned());
        note.setCreatedBy(noteDTO.getCreatedBy());
        note.setLastModifiedBy(noteDTO.getLastModifiedBy());

        // Handle tags
        Set<Tag> tags = new HashSet<>();
        if (noteDTO.getTagIds() != null) {
            for (Long tagId : noteDTO.getTagIds()) {
                Optional<Tag> tag = tagRepository.findById(tagId);
                tag.ifPresent(tags::add);
            }
        }
        note.setTags(tags);

        // Handle users
        Set<User> users = new HashSet<>();
        if (noteDTO.getUserIds() != null) {
            for (Long userId : noteDTO.getUserIds()) {
                Optional<User> user = userRepository.findById(userId);
                user.ifPresent(users::add);
            }
        }
        note.setUsers(users);

        Note saved = noteService.save(note);
        //log.info("Saved note with id: {}", saved.getId());
        return ResponseEntity.ok(saved);
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
