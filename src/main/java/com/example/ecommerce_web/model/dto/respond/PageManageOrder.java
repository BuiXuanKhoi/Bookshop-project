package com.example.ecommerce_web.model.dto.respond;


import com.example.ecommerce_web.model.alter.ManageOrderRespondDTO;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PageManageOrder {

    private List<ManageOrderRespondDTO> listManageOrder;
    private long totalElements;
    private int pageSize;
    private int currentPage;
}
