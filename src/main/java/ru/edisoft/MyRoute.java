package ru.edisoft;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyRoute extends RouteBuilder {
    public void configure() throws Exception {
//        from("activemq:queue:test.queue").to("bean:myBean?method=appendCamel");
        from("timer:hello?period={{timer.period}}").from("file:/home/aleksandrk/testCamel/output/input").log("todo");
    }
}
