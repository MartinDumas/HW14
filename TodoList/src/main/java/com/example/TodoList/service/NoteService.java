package com.example.TodoList.service;

import com.example.TodoList.entity.Note;
import com.example.TodoList.exception.NotFoundNoteException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NoteService {
    private final Map<Long, Note> noteMap = new HashMap<>();
    private long idCounter = 1;

    public List<Note> listAll() {
        return new ArrayList<>(noteMap.values());
    }

    public Note add(Note note) {
        note.setId(idCounter++);
        noteMap.put(note.getId(), note);
        return note;
    }

    @SneakyThrows
    public void deleteById(Long id) {
        if (!noteMap.containsKey(id)) {
            throw new NotFoundNoteException("Note with id " + id + " not found");
        }
        noteMap.remove(id);
    }

    @SneakyThrows
    public void update(Note note) {
        if (!noteMap.containsKey(note.getId())) {
            throw new NotFoundNoteException("Note with id " + note.getId() + " not found");
        }
        Note existingNote = noteMap.get(note.getId());
        existingNote.setTitle(note.getTitle());
        existingNote.setContent(note.getContent());
    }

    @SneakyThrows
    public Note getById(Long id) {
        Note note = noteMap.get(id);
        if (note == null) {
            throw new NotFoundNoteException("Note with id " + id + " not found");
        }
        return note;
    }
}
