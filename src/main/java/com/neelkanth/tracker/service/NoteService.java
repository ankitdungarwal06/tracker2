package com.neelkanth.tracker.service;

import com.neelkanth.tracker.model.Note;
import com.neelkanth.tracker.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public List<Note> findAll() {
        List<Note> response = noteRepository.findAll();
        return response;
    }

    public Note fetchById(Long id) {
        Optional<Note> response = noteRepository.findById(id);

        if(response.isPresent()){
            return response.get();
        }
        else{
            return new Note();
        }
    }

    public Note save(Note noteObject) {
        return noteRepository.save(noteObject);
    }

    public Note deleteNote(Long id){
        Note find = fetchById(id);
        if(find != null){
            noteRepository.delete(find);
            return find;
        }
        return new Note();
    }
}
