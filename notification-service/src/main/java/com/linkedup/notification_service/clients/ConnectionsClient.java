package com.linkedup.notification_service.clients;

import com.linkedup.notification_service.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "connection-service", path = "/connections")
public interface ConnectionsClient {

    @GetMapping("/core/first-degree")
    List<PersonDto> getFirstDegreeConnections(Long userId);
}
