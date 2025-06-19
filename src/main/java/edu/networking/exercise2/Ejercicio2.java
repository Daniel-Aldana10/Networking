package edu.networking.exercise2;


import org.springframework.web.bind.annotation.*;

@RestController
public class Ejercicio2{
    @PostMapping("/upload")
    public String uploadURL(@RequestParam String url) {
        return HttpServer.getHTML(url);
    }
}
