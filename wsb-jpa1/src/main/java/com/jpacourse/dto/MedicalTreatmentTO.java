package com.jpacourse.dto;

import java.io.Serializable;

import com.jpacourse.persistance.enums.TreatmentType;

public class MedicalTreatmentTO implements Serializable {
    private Long id;
    private String description;
    private TreatmentType type;
    private Long visitId; 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TreatmentType getType() {
        return type;
    }

    public void setType(TreatmentType type) {
        this.type = type;
    }

    public Long getVisitId() {
        return visitId;
    }

    public void setVisitId(Long visitId) {
        this.visitId = visitId;
    }
}