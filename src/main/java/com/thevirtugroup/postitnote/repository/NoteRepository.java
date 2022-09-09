package com.thevirtugroup.postitnote.repository;

import com.thevirtugroup.postitnote.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

}
