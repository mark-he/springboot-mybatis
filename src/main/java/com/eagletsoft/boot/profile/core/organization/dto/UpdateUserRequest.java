package com.eagletsoft.boot.profile.core.organization.dto;

import javax.validation.constraints.Max;
import java.io.Serializable;

public class UpdateUserRequest implements Serializable {
    @Max(100)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
