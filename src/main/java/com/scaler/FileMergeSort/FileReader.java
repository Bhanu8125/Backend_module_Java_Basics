package com.scaler.FileMergeSort;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {

        //System.out.println(System. getProperty("user.dir"));

        String inputFilePath =  System. getProperty("user.dir").toString() + "\\Files\\inputfiles";
        String outputFilePath = System. getProperty("user.dir").toString() + "\\Files\\outputfiles\\out.txt";

        File filePath = new File(inputFilePath);
        File  filesList[] = filePath.listFiles();

//        for(int i=0;i<filesList.length;i++){
//            System.out.println(filesList[i]);
//        }
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<String> totalLines = new ArrayList<>();
        for (int i = 0; i < filesList.length; i++) {
           List<String> lineList;
            try (Stream<String> lines = Files.lines(Paths.get(filesList[i].toString()))) {
                lineList = lines.collect(Collectors.toList());
                MergeSort fileLines = new MergeSort(lineList);
                Future<List<String>> futureFileLines = executorService.submit(fileLines);
                List<String> sortedFileLines = futureFileLines.get();
                totalLines.addAll(sortedFileLines);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        MergeSort totalFileLines = new MergeSort(totalLines);
        Future<List<String>> futureFileLines = executorService.submit(totalFileLines);

        List<String> sortedNumbers = futureFileLines.get();

        Path out = Paths.get(outputFilePath);
        Files.write(out,sortedNumbers, Charset.defaultCharset());
    }
}
