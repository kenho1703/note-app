package com.thevirtugroup.postitnote.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "note")
public class Note extends BaseEntity {

  private static final long serialVersionUID = 1L;
  @Column(name = "name")
  private String name;
  @Column(name = "text", columnDefinition = "TEXT")
  private String text;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
