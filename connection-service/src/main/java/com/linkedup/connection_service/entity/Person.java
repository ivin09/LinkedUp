package com.linkedup.connection_service.entity;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class Person {

    @Id
    @GeneratedValue
    private Long id;    //neo4j need in order to work properly we dont care about it more importantly

    private Long userId;    //actual id of user stored in a postgresql database

    private String name;
}
