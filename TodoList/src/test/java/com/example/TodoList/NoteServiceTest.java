package com.example.TodoList;


import com.example.TodoList.entity.Note;
import com.example.TodoList.exception.NotFoundNoteException;
import com.example.TodoList.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class NoteServiceTest {
    private NoteService noteService;

    @BeforeEach
    void setUp() {
        noteService = new NoteService();
    }

    @Test
    void testAddNote() {
        Note note = new Note();
        note.setTitle("Test Title");
        note.setContent("Test Content");

        Note addedNote = noteService.add(note);

        assertNotNull(addedNote.getId(), "ID should be generated");
        assertEquals("Test Title", addedNote.getTitle());
        assertEquals("Test Content", addedNote.getContent());
        assertEquals(1, noteService.listAll().size(), "List size should be 1 after adding a note");
    }

    @Test
    void testGetById() {
        Note note = new Note();
        note.setTitle("Test Title");
        note.setContent("Test Content");
        Note addedNote = noteService.add(note);

        Note fetchedNote = noteService.getById(addedNote.getId());

        assertNotNull(fetchedNote);
        assertEquals("Test Title", fetchedNote.getTitle());
    }

    @Test
    void testDeleteById() {
        Note note = new Note();
        note.setTitle("Test Title");
        note.setContent("Test Content");
        Note addedNote = noteService.add(note);

        noteService.deleteById(addedNote.getId());

        assertThrows(NotFoundNoteException.class, () -> noteService.getById(addedNote.getId()));
        assertEquals(0, noteService.listAll().size(), "List size should be 0 after deletion");
    }

    @Test
    void testUpdateNote() {
        Note note = new Note();
        note.setTitle("Original Title");
        note.setContent("Original Content");
        Note addedNote = noteService.add(note);

        Note updatedNote = new Note();
        updatedNote.setId(addedNote.getId());
        updatedNote.setTitle("Updated Title");
        updatedNote.setContent("Updated Content");

        noteService.update(updatedNote);

        Note fetchedNote = noteService.getById(addedNote.getId());
        assertEquals("Updated Title", fetchedNote.getTitle());
        assertEquals("Updated Content", fetchedNote.getContent());
    }

    @Test
    void testUpdateNonExistentNote() {
        Note note = new Note();
        note.setId(999L); // Нотатка, якої немає в базі
        note.setTitle("Non-existent Title");
        note.setContent("Non-existent Content");

        assertThrows(NotFoundNoteException.class, () -> noteService.update(note));
    }

    @Test
    void testGetByIdNonExistent() {
        assertThrows(NotFoundNoteException.class, () -> noteService.getById(999L));
    }


}
