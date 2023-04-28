package com.scaler;

import com.scaler.greet.Greeting;
import com.scaler.rest.Photo;
import com.scaler.rest.RetroClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Main {
    public static void main(String[] args) throws IOException {
        String arg = args[0];
        switch (arg){
            case "greet":
                Greeting greeting = new Greeting();
                System.out.println(greeting.getGreet());
            case "rest":
                RetroClient retroClient = new RetroClient();
                var response = retroClient.getApi().getPhotos().execute();
                int responseFiles = response.body().size();
                String outputFilePath = System. getProperty("user.dir").toString() + "\\Files\\albums";
                for(int i=0;i<responseFiles;i++){
                    Photo photo = response.body().get(i);
                    outputFilePath = outputFilePath+"/" +photo.getAlbumId();
                    Files.createDirectories(Paths.get(outputFilePath));
                    URL url = new URL(photo.getUrl());
                    try (InputStream in = url.openStream();
                         ReadableByteChannel rbc = Channels.newChannel(in);
                         FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                    }
                }

        }

    }
}