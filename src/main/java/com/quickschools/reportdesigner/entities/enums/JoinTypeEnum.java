package com.quickschools.reportdesigner.entities.enums;

public enum JoinTypeEnum {
    InnerJoin, LeftJoin, RightJoin, FullJoin;

    public String sql() {
        switch (this) {
            case InnerJoin: {
                return "INNER JOIN";
            }
            case FullJoin: {
                return "JOIN";
            }
            case LeftJoin: {
                return "LEFT JOIN";
            }
            case RightJoin: {
                return "RIGHT JOIN";
            }
        }
        return name();
    }
}
