package com.linkedup.connection_service.controller;

import com.linkedup.connection_service.entity.Person;
import com.linkedup.connection_service.service.ConnectionsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionsService connectionsService;

    @GetMapping("/first-degree")
    public ResponseEntity<List<Person>> getFirstDegreeConnections() {
        return ResponseEntity.ok(connectionsService.getFirstDegreeConnectionsOfUser());
    }
}
