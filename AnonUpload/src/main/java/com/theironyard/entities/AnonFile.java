package com.theironyard.entities;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * Created by jeff on 7/27/16.
 */
@Entity
@Table(name = "files")
public class AnonFile {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String originalFilename;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean permanent = false;

    @Column
    private String comment;

    @Column
    private String password;

    public AnonFile() {
    }

    public AnonFile(String filename, String originalFilename) {
        this.filename = filename;
        this.originalFilename = originalFilename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
