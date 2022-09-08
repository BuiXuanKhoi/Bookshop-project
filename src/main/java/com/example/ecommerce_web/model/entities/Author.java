package com.example.ecommerce_web.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private int authorID;

    @Column(name = "author_name")
    private String authorName;

    @OneToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private List<Books> books;

    public Author(String authorName){
        this.authorName = authorName;
    }

}
