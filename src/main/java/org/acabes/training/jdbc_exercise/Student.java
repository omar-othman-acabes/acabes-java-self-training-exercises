package org.acabes.training.jdbc_exercise;

public class Student {
    private final int id;
    private final String name;
    private final double average;
    private final String email;

    public Student(int id, String name, double average, String email) {
        this.id = id;
        this.name = name;
        this.average = average;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAverage() {
        return average;
    }

    public String getEmail() {
        return email;
    }
}
