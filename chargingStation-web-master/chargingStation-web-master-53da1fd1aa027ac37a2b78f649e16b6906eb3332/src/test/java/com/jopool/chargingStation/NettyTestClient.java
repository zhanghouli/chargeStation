package com.jopool.chargingStation;

import com.jopool.chargingStation.www.netty.util.HexStringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by gexin on 2017/10/24.
 */
public class NettyTestClient {
    private Socket         socket;
    private BufferedReader in;
    private OutputStream   out;

    public NettyTestClient() {
        try {
            socket = new Socket("127.0.0.1", 20001);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = socket.getOutputStream();
            Scanner input = new Scanner(System.in);
            String msg = null;
            do {
                msg = input.nextLine();
                out.write(HexStringUtils.hexString2Bytes(msg));
                out.flush();
                System.out.println("input : " + msg);
            } while (!"exit".equals(msg));
            input.close();
            out.close();
            in.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new NettyTestClient();
    }
}
