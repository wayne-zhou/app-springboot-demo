package com.example.demo.note.netty;

import org.junit.Test;

/**
 * Created by zhouwei on 2017/12/27
 **/
public class NettyTest {

    @Test
    public void severStart() throws Exception {
        SimpleServer server = new SimpleServer(9999);
        server.run();
    }

    @Test
    public void clentStart() throws Exception {
        SimpleClient client = new SimpleClient();
        client.connect("127.0.0.1", 9999);
    }

}
