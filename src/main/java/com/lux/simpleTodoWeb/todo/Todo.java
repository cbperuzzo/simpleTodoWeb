package com.lux.simpleTodoWeb.todo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table
public class Todo {

    @Id
    @GeneratedValue
    private int id;

    @NotEmpty(message = "this field can't be empty")
    private String description;

    @NotNull(message = "this field can't be empty")
    @Future(message="must be a date in the future")
    private LocalDate date;
    private boolean done;

    public Todo(String description, LocalDate date, boolean done, int id) {
        this.description = description;
        this.date = date;
        this.done = done;
        this.id = id;
    }
    public Todo(){}

    public Todo(String s, LocalDate now) {
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isDone() {
        return done;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", done=" + done +
                '}';
    }
}
