package ru.task.miss.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO<Product> {
    private List<Product> data;
    //private int pageNo;
    //private int pageSize;
    private long totalElements;
    //private int totalPages;
    //private boolean last;
}
