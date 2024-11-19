package ru.task.miss.common;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageToPageDTOMapper<T> {
    public PageDTO<T> pageToPageDTO(Page<T> page) {
        PageDTO<T> pageDTO = new PageDTO<>();
        pageDTO.setData(page.getContent());
        pageDTO.setTotalElements(page.getTotalElements());
        return pageDTO;
    }

}