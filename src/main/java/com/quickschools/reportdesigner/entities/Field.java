package com.quickschools.reportdesigner.entities;

import com.quickschools.reportdesigner.entities.enums.FieldTypeEnum;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Field {

    private String name; // the name of field

    private String columnName; // the name of column in table of database

    private FieldTypeEnum fieldType; // the type of field (Integer, String, ...)

    private Boolean nullable; // is the field is nullable or not

    private Entity entity;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Field)) {
            return false;
        }

        Field field = (Field) obj;

        return this.getName().equals(field.getName()) && this.entity.equals(field.getEntity());
    }
}
