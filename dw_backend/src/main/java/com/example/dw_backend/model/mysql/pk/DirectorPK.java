package com.example.dw_backend.model.mysql.pk;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class DirectorPK implements Serializable {

    private String productId;
    private String directorName;
}
