//package com.salary.util;
//
//import org.junit.jupiter.api.Test;
//
//import java.nio.charset.StandardCharsets;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.UUID;
//
//public class UUIDTest {
//    @Test
//    void makeUUID(){
//        for(int i=0; i<10; i++){
//            System.out.println(UUID.randomUUID());
//        }
//    }
//
//    @Test
//    void generateState() throws NoSuchAlgorithmException {
//        long nonce = 0L;
//        String key = "secret-key";
//        for(int i=0; i<5; i++){
//            String input = key + i;
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            byte[] hashedBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
//
//            StringBuilder hexString = new StringBuilder();
//            for (byte hashedByte : hashedBytes) {
//                hexString.append(String.format("%02X", hashedByte));
//            }
//            System.out.println(hexString);
//        }
//    }
//}
