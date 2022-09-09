package com.thevirtugroup.postitnote.mapper;

import com.thevirtugroup.postitnote.dto.NoteDto;
import com.thevirtugroup.postitnote.dto.NoteResponse;
import com.thevirtugroup.postitnote.dto.UpdateNoteDto;
import com.thevirtugroup.postitnote.model.Note;
import java.time.format.DateTimeFormatter;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-09T15:40:29+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_202 (Oracle Corporation)"
)
public class NoteMapperImpl implements NoteMapper {

    @Override
    public Note mapToEntity(NoteDto noteDto) {
        if ( noteDto == null ) {
            return null;
        }

        Note note = new Note();

        note.setName( noteDto.getName() );
        note.setText( noteDto.getText() );

        return note;
    }

    @Override
    public NoteResponse mapToDto(Note note) {
        if ( note == null ) {
            return null;
        }

        NoteResponse noteResponse = new NoteResponse();

        if ( note.getCreatedDate() != null ) {
            noteResponse.setCreatedDate( DateTimeFormatter.ofPattern( "dd/mm/yyyy" ).format( note.getCreatedDate() ) );
        }
        noteResponse.setName( note.getName() );
        noteResponse.setText( note.getText() );
        noteResponse.setId( note.getId() );

        return noteResponse;
    }

    @Override
    public void updateBeforeUpdate(Note note, UpdateNoteDto noteDto) {
        if ( noteDto == null ) {
            return;
        }

        note.setName( noteDto.getName() );
        note.setText( noteDto.getText() );
    }
}
