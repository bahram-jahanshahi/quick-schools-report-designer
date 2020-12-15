package com.quickschools.reportdesigner.entities;

import com.quickschools.reportdesigner.entities.enums.CardinalityEnum;
import com.quickschools.reportdesigner.entities.enums.JoinTypeEnum;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Join {

    // the source of relation
    private Field sourcePrimaryKey;

    // the destination of relation
    private Field destinationPrimaryKey;

    // OneToMany, ManyToOne, ...
    private CardinalityEnum cardinality;

    private JoinTypeEnum joinType;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Join)) {
            return false;
        }

        Join join = (Join) obj;

        return this.sourcePrimaryKey.equals(join.sourcePrimaryKey)
                && this.destinationPrimaryKey.equals(join.destinationPrimaryKey);
    }
}
