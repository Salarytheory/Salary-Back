package com.salary.util;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UUIDTest {
    @Test
    void makeUUID(){
        for(int i=0; i<10; i++){
            System.out.println(UUID.randomUUID());
        }
    }
}
