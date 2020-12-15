package com.quickschools.reportdesigner.entities;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Entity {

    private String name; // The name of entity

    private String tableName; // The name of table in database

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Entity)) {
            return false;
        }

        Entity entity = (Entity) obj;

        return this.name.equals(entity.getName());
    }
}
