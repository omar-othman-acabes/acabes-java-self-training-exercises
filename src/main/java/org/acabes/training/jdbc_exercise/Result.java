package org.acabes.training.jdbc_exercise;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Result {
    ResultSet resultSet;

    Result(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    boolean exists() {
        try {
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void print() {
        try {
            String format = "%-16s";
            ResultSetMetaData metaData = resultSet.getMetaData();

            String lineBreak = "======================================================================================";

            System.out.println(lineBreak);
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                System.out.printf(format, metaData.getColumnName(i));
            }

            System.out.println();

            if (!resultSet.isFirst()) {
                resultSet.next();
            }

            do {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    System.out.printf(format, resultSet.getString(i));
                }
                System.out.println();
            } while (resultSet.next());

            System.out.println(lineBreak);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
