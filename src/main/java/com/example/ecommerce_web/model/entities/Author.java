package com.example.ecommerce_web.model.entities;

import com.example.ecommerce_web.validator.UniqueField;
import com.example.ecommerce_web.validator.Validator;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private int authorID;

    @UniqueField( entity = Validator.AUTHOR, message = "Author name already existed")
    @Column(name = "author_name", unique = true)
    @NotEmpty(message = "author name is required")
    @NotNull(message = "author name must not be null")
    private String authorName;

    @OneToMany(mappedBy = "authors", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Books> books;

    public Author(String authorName){
        this.authorName = authorName;
    }

}
