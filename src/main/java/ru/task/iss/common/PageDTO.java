package ru.task.iss.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO<Item> {
    private List<Item> data;
    //private int pageNo;
    //private int pageSize;
    private long totalElements;
    //private int totalPages;
    //private boolean last;
}
