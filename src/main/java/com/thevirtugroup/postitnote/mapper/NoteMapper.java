package com.thevirtugroup.postitnote.mapper;

import com.thevirtugroup.postitnote.dto.NoteDto;
import com.thevirtugroup.postitnote.dto.NoteResponse;
import com.thevirtugroup.postitnote.dto.UpdateNoteDto;
import com.thevirtugroup.postitnote.model.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface NoteMapper {

  Note mapToEntity(NoteDto noteDto);

  @Mapping(target = "createdDate", dateFormat = "dd/mm/yyyy")
  NoteResponse mapToDto(Note note);

  void updateBeforeUpdate(@MappingTarget Note note, UpdateNoteDto noteDto);
}
