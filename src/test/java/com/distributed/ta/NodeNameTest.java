package com.distributed.ta;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class NodeNameTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetIp() {
        Node responseMsg = target.path("NodeName").queryParam("name","node1").request().get(Node.class);
        assertEquals("192.168.0.2", responseMsg.getIpAddress());
    }

    @Test
    public void testAddNode(){
        Node testnode = new Node("node6","192.168.0.6");
        String responseString = target.path("NodeName").request(MediaType.TEXT_PLAIN).post(Entity.entity(testnode,
                MediaType.APPLICATION_JSON),String.class);
        assertEquals("the node has been added to the list of known nodes", responseString);
    }
}
