package com.example.studentmanagement.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * DTO for Q3.4: "Update student scores and skills"
 */
public class StudentScoreSkillUpdateDTO {

    @NotNull(message = "Programming score cannot be null")
    @Min(value = 0, message = "Programming score must be at least 0")
    @Max(value = 100, message = "Programming score cannot exceed 100")
    private Double programmingScore;

    @NotNull(message = "Mathematics score cannot be null")
    @Min(value = 0, message = "Mathematics score must be at least 0")
    @Max(value = 100, message = "Mathematics score cannot exceed 100")
    private Double mathematicsScore;

    private List<String> technicalSkills;

    public StudentScoreSkillUpdateDTO() {
    }

    public StudentScoreSkillUpdateDTO(Double programmingScore, Double mathematicsScore, List<String> technicalSkills) {
        this.programmingScore = programmingScore;
        this.mathematicsScore = mathematicsScore;
        this.technicalSkills = technicalSkills;
    }

    public Double getProgrammingScore() {
        return programmingScore;
    }

    public void setProgrammingScore(Double programmingScore) {
        this.programmingScore = programmingScore;
    }

    public Double getMathematicsScore() {
        return mathematicsScore;
    }

    public void setMathematicsScore(Double mathematicsScore) {
        this.mathematicsScore = mathematicsScore;
    }

    public List<String> getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(List<String> technicalSkills) {
        this.technicalSkills = technicalSkills;
    }
}
