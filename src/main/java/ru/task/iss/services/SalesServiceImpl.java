package ru.task.iss.services;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.repositories.SalesRepository;

@RequiredArgsConstructor
@Service
@EnableScheduling
@Transactional(readOnly = true)
public class SalesServiceImpl {
    SalesRepository salesRepository;


}
