package com.thevirtugroup.postitnote.rest;

import com.thevirtugroup.postitnote.dto.NoteDto;
import com.thevirtugroup.postitnote.dto.NoteResponse;
import com.thevirtugroup.postitnote.dto.UpdateNoteDto;
import com.thevirtugroup.postitnote.exceptions.NotFoundException;
import com.thevirtugroup.postitnote.services.NoteService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/notes")
@Validated
public class NoteController {

  @Autowired
  private NoteService noteService;

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<NoteResponse> createSavedSearch(@RequestBody @NotNull NoteDto request) {
    return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri())
        .body(noteService.save(request));
  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<NoteResponse>> findAllNotes() {
    return ResponseEntity.ok(noteService.findAll());
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<NoteResponse> findNoteById(@PathVariable Long id) {
    return ResponseEntity.ok(noteService.findById(id));
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public ResponseEntity<NoteResponse> updateNoteById(
      @RequestBody @NotNull @Valid UpdateNoteDto request,
      @PathVariable Long id) {
    return ResponseEntity.ok(noteService.updateNote(request, id));
  }
}
