package edu.networking;


import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;

@RestController
public class Ejercicio2{
    @PostMapping("/upload")
    public String uploadURL(@RequestParam String url) {
        return HttpServer.getHTML(url);
    }
}
