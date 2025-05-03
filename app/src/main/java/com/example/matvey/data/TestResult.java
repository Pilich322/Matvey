package com.example.matvey.data;

public class TestResult {
    private long id;
    private String userName;
    private int score;
    private String date;
    private long testId;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public long getTestId() { return testId; }
    public void setTestId(long testId) { this.testId = testId; }
}
