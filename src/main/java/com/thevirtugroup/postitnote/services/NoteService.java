package com.thevirtugroup.postitnote.services;

import com.thevirtugroup.postitnote.dto.NoteDto;
import com.thevirtugroup.postitnote.dto.NoteResponse;
import com.thevirtugroup.postitnote.dto.UpdateNoteDto;
import com.thevirtugroup.postitnote.exceptions.NotFoundException;
import com.thevirtugroup.postitnote.mapper.NoteMapper;
import com.thevirtugroup.postitnote.model.Note;
import com.thevirtugroup.postitnote.repository.NoteRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

  @Autowired
  private NoteRepository noteRepository;

  NoteMapper noteMapper = Mappers.getMapper(NoteMapper.class);

  @Transactional
  public NoteResponse save(NoteDto noteDto) {
    return noteMapper.mapToDto(noteRepository.save(noteMapper.mapToEntity(noteDto)));
  }

  public List<NoteResponse> findAll() {
    return noteRepository.findAll().stream().map(noteMapper::mapToDto).collect(Collectors.toList());
  }

  public NoteResponse findById(Long id) {
    return noteMapper.mapToDto(noteRepository.findOne(id));
  }

  @Transactional
  public NoteResponse updateNote(UpdateNoteDto dto, Long id) {
    Note note = noteRepository.findOne(id);
    if (note == null) {
      throw new NotFoundException("Note Not Found");
    }
    noteMapper.updateBeforeUpdate(note, dto);
    return noteMapper.mapToDto(noteRepository.save(note));
  }

}
