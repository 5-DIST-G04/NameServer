package com.distributed.ta;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static String BASE_URI = "http://localhost:8080/";
    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.distributed.ta package
        final ResourceConfig rc = new ResourceConfig().packages("com.distributed.ta");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static MulticastPublisher publish = new MulticastPublisher();

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        MulticastReceiver receiver = new MulticastReceiver("224.0.0.251" , 3000);
        receiver.run();

        System.out.println("Write down the ip address and port number to bind to as ip:port");
        BASE_URI = "http://" + input.next() + "/";

        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}

