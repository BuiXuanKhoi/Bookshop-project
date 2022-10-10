package com.example.ecommerce_web.model.dto.respond;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageAuthor {
    private List<AuthorRespondDTO> authorRespondDTO;
    private int totalElements;
    private int currentPage;
    private int pageSize;
}
