package com.fri.code.inputs.models.entities;

import javax.persistence.*;

@Entity
@Table(name="input_entity")
@NamedQueries(
        value = {
                @NamedQuery(name = "InputMetadataEntity.getAll", query = "SELECT inp FROM InputMetadataEntity inp"),
                @NamedQuery(name = "InputMetadataEntity.getInputsForExercise", query = "SELECT inp FROM InputMetadataEntity inp WHERE inp.ID = ?1")
        }
)
public class InputMetadataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column(name = "content")
    private String content;

    @Column(name = "isHidden")
    private Boolean isHidden;

    @Column(name = "exerciseID")
    private Integer exerciseID;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getHidden() {
        return isHidden;
    }

    public void setHidden(Boolean hidden) {
        isHidden = hidden;
    }

    public Integer getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(Integer exerciseID) {
        this.exerciseID = exerciseID;
    }
}
