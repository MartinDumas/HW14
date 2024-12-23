package com.example.TodoList.controller;

import com.example.TodoList.entity.Note;
import com.example.TodoList.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/list")
    public String listNotes(Model model) {
        model.addAttribute("notes", noteService.listAll());
        return "note-list";
    }

    @PostMapping("/delete")
    public String deleteNote(@RequestParam Long id) {
        noteService.deleteById(id);
        return "redirect:/note/list";
    }

    @GetMapping("/edit")
    public String editNoteForm(@RequestParam(required = false) Long id, Model model) {
        Note note = (id == null || id == -1) ? new Note() : noteService.getById(id);
        model.addAttribute("note", note);
        return "note-edit";
    }

    @PostMapping("/edit")
    public String saveNote(@ModelAttribute Note note) {
        if (note.getId() == null || note.getId() == -1) {
            noteService.add(note);
        } else {
            noteService.update(note);
        }
        return "redirect:/note/list";
    }
}
