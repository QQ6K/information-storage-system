package ru.task.iss.common;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.task.iss.models.Item;
@Component
public class PageToPageDTOMapper<T> {
    public PageDTO<Item> pageToPageDTO(Page<Item> page) {
        PageDTO<Item> pageDTO = new PageDTO<>();
        pageDTO.setData(page.getContent());
        pageDTO.setTotalElements(page.getTotalElements());
        return pageDTO;
    }


}