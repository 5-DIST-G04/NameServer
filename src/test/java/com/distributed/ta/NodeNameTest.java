package com.distributed.ta;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.distributed.common.Node;
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
        Node node = new Node("node1","192.168.0.2");
        NodeNameDatabase.getInstance().addNode(node);

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        Node node = new Node("node1","192.168.0.2");
        NodeNameDatabase.getInstance().removeNode(node);
        server.stop();
    }


    @Test
    public void testGetIp() {

        Response responseMsg = target.path("NodeName/node1").request(MediaType.APPLICATION_JSON).get();
        assertEquals("192.168.0.2", responseMsg.readEntity(Node.class).getIpAddress());
    }

    @Test
    public void testAddNode(){
        Node testnode = new Node("node6","192.168.0.6");
        String responseString = target.path("NodeName").request(MediaType.TEXT_PLAIN).post(Entity.entity(testnode,
                MediaType.APPLICATION_JSON),String.class);
        assertEquals("the node has been added to the list of known nodes", responseString);
        responseString = target.path("NodeName/"+testnode.getName()).request(MediaType.TEXT_PLAIN).delete(String.class);
    }
}
