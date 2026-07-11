package com.linkedup.connection_service.service;

import com.linkedup.connection_service.auth.UserContextHolder;
import com.linkedup.connection_service.entity.Person;
import com.linkedup.connection_service.event.AcceptConnectionRequestEvent;
import com.linkedup.connection_service.event.SendConnectionRequestEvent;
import com.linkedup.connection_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionsService {

    private final PersonRepository personRepository;
    private final KafkaTemplate<Long, SendConnectionRequestEvent> sendRequestKafkaTemplate;
    private final KafkaTemplate<Long, AcceptConnectionRequestEvent> acceptRequestKafkaTemplate;

    public List<Person> getFirstDegreeConnectionsOfUser() {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Getting first degree connections of user with ID: {}", userId);
        return personRepository.getFirstDegreeConnections(userId);
    }

    public Boolean sendConnectionRequest(Long receiverId) {
        Long senderId = UserContextHolder.getCurrentUserId();

        log.info("Trying to send connection request , senderId: {}, receiverId: {}", senderId, receiverId);

        if (senderId.equals(receiverId)) throw new RuntimeException("Sender and receiver are same");

        boolean alreadyRequestSent = personRepository.connectionRequestExists(senderId,receiverId);
        if (alreadyRequestSent) {
            throw new RuntimeException("Connection request already exists");
        }

        boolean alreadyConnected = personRepository.alreadyConnected(senderId, receiverId);
        if (alreadyConnected) {
            throw new RuntimeException("Already connected");
        }

        log.info("Successfully sent connection request");
        personRepository.addConnectionRequest(senderId, receiverId);

        SendConnectionRequestEvent sendConnectionRequestEvent = SendConnectionRequestEvent.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .build();

        sendRequestKafkaTemplate.send("send-connection-request-topic", sendConnectionRequestEvent);

        return true;
    }

    public Boolean acceptConnectionRequest(Long senderId) {
        Long receiverId = UserContextHolder.getCurrentUserId();

        boolean connectionRequestExists = personRepository.connectionRequestExists(senderId,receiverId);
        if (!connectionRequestExists) {
            throw new RuntimeException("No connection request to accept");
        }

        personRepository.acceptConnectionRequest(senderId, receiverId);
        log.info("Successfully accepted connection request, senderId: {}, receiverId: {}", senderId, receiverId);

        AcceptConnectionRequestEvent acceptConnectionRequestEvent = AcceptConnectionRequestEvent.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .build();

        acceptRequestKafkaTemplate.send("accept-connection-request-topic", acceptConnectionRequestEvent);
        return true;
    }

    public Boolean rejectConnectionRequest(Long senderId) {
        Long receiverId = UserContextHolder.getCurrentUserId();
        boolean connectionRequestExists = personRepository.connectionRequestExists(senderId,receiverId);
        if (!connectionRequestExists) {
            throw new RuntimeException("No connection request to reject");
        }

        personRepository.rejectConnectionRequest(senderId, receiverId);
        return true;
    }
}
