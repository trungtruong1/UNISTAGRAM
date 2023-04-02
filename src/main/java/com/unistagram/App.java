package com.unistagram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableSwagger2
@RestController
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
        System.out.println("Server is running!");
    }
    
    @GetMapping("/")
    public String greet() {
        return "Hi";
    }

    @GetMapping("/supp")
    public String supp() {
        return "Whazzzzzzzzzzzzzzzzzzapppppppppppppp";
    }

    // @GetMapping("/ratings")
    // public String ratings() {
    //     return "YOOOOOOOOOOOOOOOOOOO";
    // }

    // @GetMapping("/employees")
    // public String employees() {
    //     return "Whatsoup employees";
    // }
}
