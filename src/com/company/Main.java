package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8081)) {
            System.out.println("порт " + serverSocket.getLocalPort() + " прослушивается");
            while (true) {
                Socket socket = serverSocket.accept();


                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                     PrintWriter printWriter = new PrintWriter(socket.getOutputStream())) {
                    while (!bufferedReader.ready()) ;

                    String firstLine = bufferedReader.readLine();
                    String[] splited = firstLine.split(" ");
                    while (bufferedReader.ready()) {
                        System.out.println(bufferedReader.readLine());
                    }

                    Path path = Paths.get("F:\\NiceServerAwesomeSockets\\out\\production\\NiceServerAwesomeSockets\\com\\company\\files\\index.htm",splited[1]);
                    if (!Files.exists(path)){
                        printWriter.println("HTTP/1.1 404 NOT_FOUND");
                        printWriter.println("Content-Type: text/html; charset=utf-8");
                        printWriter.println();
                        printWriter.println("<h4>Файл не найден</h4>");
                        printWriter.flush();
                        continue;
                    }
                    printWriter.println("HTTP/1.1 200 OK");
                    printWriter.println("Content-Type: text/html; charset=utf-8");
                    printWriter.println();
                    printWriter.println("<h1>Добро пожаловать на сервер \"шизофрения\"</h1>");
                    Files.newBufferedReader(path).transferTo(printWriter);
                    printWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

