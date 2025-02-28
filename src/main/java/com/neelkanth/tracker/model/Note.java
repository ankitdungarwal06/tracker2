package com.neelkanth.tracker.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String emailContent; // To store the email content

    @ElementCollection
    private Set<String> hashtags = new HashSet<>(); // Hashtags for filtering

    @ManyToMany
    @JoinTable(
            name = "note_tag",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )

    @ElementCollection
    private Set<Tag> tags = new CopyOnWriteArraySet<>();

    @ManyToMany
    @JoinTable(
            name = "note_user",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>(); // Many-to-many relationship with users

    // Other necessary fields
    @Column(nullable = false)
    private boolean isArchived = false; // To archive notes

    @Column(nullable = false)
    private boolean isPinned = false; // To pin important notes

    @Column(nullable = false)
    private String createdBy; // User who created the note

    @Column(nullable = false)
    private String lastModifiedBy; // User who last modified the note

}