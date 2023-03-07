package com.ellio.notes.controller;

import com.ellio.notes.model.Note;
import com.ellio.notes.repository.NoteRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class NoteController {

    private final NoteRepository noteRepository;

    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        Note note = new Note();
        note.setTitle("My first note");
        note.setContent("This is my first note in this app");
        noteRepository.save(note);
    }

    @GetMapping("/")
    public String listNotes(Model model) {
        List<Note> notes = noteRepository.findAll();
        model.addAttribute("notes", notes);
        return "listNotes";
    }

    @GetMapping("/newNote")
    public String newNoteForm(Model model) {
        model.addAttribute("note", new Note());
        return "newNote";
    }

    @PostMapping("/newNote")
    public String newNoteSubmit(@ModelAttribute Note note) {
        noteRepository.save(note);
        return "redirect:/";
    }

    @GetMapping("/editNote/{id}")
    public String editNoteForm(@PathVariable("id") long id, Model model) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid note Id:" + id));
        model.addAttribute("note", note);
        return "editNote";
    }

    @PostMapping("/editNote/{id}")
    public String editNoteSubmit(@PathVariable("id") long id, @ModelAttribute Note note) {
        note.setId(id);
        noteRepository.save(note);
        return "redirect:/";
    }

    @GetMapping("/deleteNote/{id}")
    public String deleteNoteForm(@PathVariable("id") long id, Model model) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid note Id:" + id));
        model.addAttribute("note", note);
        return "deleteNote";
    }

    @PostMapping("/deleteNote/{id}")
    public String deleteNoteSubmit(@PathVariable("id") long id) {
        noteRepository.deleteById(id);
        return "redirect:/";
    }
}
