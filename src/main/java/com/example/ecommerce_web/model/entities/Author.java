package com.example.ecommerce_web.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
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
