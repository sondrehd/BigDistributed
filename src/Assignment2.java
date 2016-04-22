/**
 * Created by sondredyvik on 06.10.15.
 */
import java.util.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.WRITE;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.DELETE_ON_CLOSE;



public class Assignment2 {
    private  static final int BLOCKSIZE =8192;
    private  static final int BLOCKS = 131072;
    private  Path file;
    public static void main (String[] args)
    {
        try
        {
            Assignment2 obj = new Assignment2 ();
            obj.run();
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
    }
    public Assignment2(){
        file = Paths.get(System.getProperty("user.dir"), "myjavadata");

    }



    public void run()  {
        System.out.print("EXT4/NIO  Throughput  Time\n");
        System.out.print("---------------------------\n");

        writeToFile(1);
        writeToFile(2);
        writeToFile(4);
        writeToFile(8);
        writeToFile(16);
        writeToFile(32);

    }
    public void writeToFile(int n){
        try{
            SeekableByteChannel out = Files.newByteChannel
                    (file,EnumSet.of(CREATE, APPEND, WRITE, DELETE_ON_CLOSE));

            long startTime = System.nanoTime();

            for (int i=0;i <n*BLOCKS; i++) {
                ByteBuffer buff = ByteBuffer.allocate(BLOCKSIZE);
                out.write((buff));

            }
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            double elapsedTime = TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS);
            System.out.print((out.size() / (1024 * 1024 * 1024)) + "GB");
            System.out.print("          " + String.format("%.1f", (out.size() / ((elapsedTime) * 1024 * 1024))) + "MB/s");
            System.out.print("      " + (int)elapsedTime*1000 + "ms\n");

        }


        catch(IOException e){
            System.out.print("sumething is wrong");
            e.printStackTrace();
        }


    }


}
