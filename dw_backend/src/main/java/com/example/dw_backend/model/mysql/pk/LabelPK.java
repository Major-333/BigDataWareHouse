package com.example.dw_backend.model.mysql.pk;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class LabelPK implements Serializable {
    private String productId;
    private String labelName;

    public String getLabelName() {
        return labelName;
    }
}
