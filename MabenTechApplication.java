package com.mabentech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class MabenTechApplication {

    public static void main(String[] args) {
        SpringApplication.run(MabenTechApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        System.out.println("""
            ╔══════════════════════════════════════════════════╗
            ║          MABENTECH Store - STARTED               ║
            ║    The Future of Laptops, Delivered Globally     ║
            ║    http://localhost:8080                         ║
            ╚══════════════════════════════════════════════════╝
            """);
    }
}
