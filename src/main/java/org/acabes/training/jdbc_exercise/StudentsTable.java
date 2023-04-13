package org.acabes.training.jdbc_exercise;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class StudentsTable {
    private static final String TABLE_NAME = "students";
    private final MyConnection connection;


    StudentsTable(MyConnection connection) {
        this.connection = connection;
    }

    static void printView(ResultSet result) throws SQLException {
        String format = "%-16s";
        ResultSetMetaData metaData = result.getMetaData();

        String lineBreak = "======================================================================================";

        System.out.println(lineBreak);
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            System.out.printf(format, metaData.getColumnName(i));
        }

        System.out.println();

        while (result.next()) {
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                System.out.printf(format, result.getString(i));
            }
            System.out.println();
        }
        System.out.println(lineBreak);
    }

    InsertFeedback addStudent(Student student) {
        try {
            connection.getStatement().executeUpdate(
                    String.format("INSERT INTO %s VALUES (%s, '%s', %s, '%s');",
                            TABLE_NAME, student.getId(), student.getName(), student.getAverage(), student.getEmail())
            );
            return InsertFeedback.SUCCESS;
        } catch (SQLIntegrityConstraintViolationException e) {
            return InsertFeedback.DUPLICATE;
        } catch (SQLException e) {
            return InsertFeedback.OTHER_EXCEPTION;
        }
    }

    boolean updateStudent(Student student) {
        try {
            StringBuilder setCommands = new StringBuilder();
            setCommands.append("SET");

            if (student.getName() != null) {
                setCommands.append(String.format(" name = '%s'", student.getName()));
            }

            if (student.getAverage() != -1) {
                if (setCommands.length() != 3) {
                    setCommands.append(",");
                }
                setCommands.append(String.format(" average = %s", student.getAverage()));
            }

            if (student.getEmail() != null) {
                if (setCommands.length() != 3) {
                    setCommands.append(",");
                }
                setCommands.append(String.format(" email = '%s'", student.getEmail()));
            }

            if (setCommands.length() == 3) {
                return false;
            }

            int rowCount = connection.getStatement().executeUpdate(
                    String.format("UPDATE %s %s WHERE id = %s", TABLE_NAME, setCommands.toString().trim(), student.getId())
            );
            return rowCount == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    boolean deleteStudent(int id) {
        try {
            String sql = String.format("DELETE FROM %s WHERE id = %s", TABLE_NAME, id);
            int rowCount = connection.getStatement().executeUpdate(sql);
            return rowCount == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void showAllStudents() {
        try {
            ResultSet result = connection.getStatement().executeQuery(String.format(
                    "SELECT * FROM %s", TABLE_NAME
            ));
            printView(result);
            result.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    Result queryStudentById(int id) {
        try {
            ResultSet resultSet = connection.getStatement().executeQuery(String.format(
                    "SELECT * FROM %s WHERE id = %s", TABLE_NAME, id
            ));
            return new Result(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
