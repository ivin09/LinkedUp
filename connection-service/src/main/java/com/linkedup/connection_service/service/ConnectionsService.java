package com.linkedup.connection_service.service;

import com.linkedup.connection_service.auth.UserContextHolder;
import com.linkedup.connection_service.entity.Person;
import com.linkedup.connection_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionsService {

    private final PersonRepository personRepository;

    public List<Person> getFirstDegreeConnectionsOfUser() {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Getting first degree connections of user with ID: {}", userId);
        return personRepository.getFirstDegreeConnections(userId);
    }
}
