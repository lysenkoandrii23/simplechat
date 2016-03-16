import java.net.*;
import java.io.*;

public class SimpleClient {
    public static void main(String args[]) {
        try {
            Socket s1 = new Socket("localhost", 5432);
            try (BufferedReader bis = new BufferedReader(new InputStreamReader(s1.getInputStream()));
                 BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(s1.getOutputStream()));
                 BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
                new Thread() {
                    public void run() {
                        read(bis);
                    }
                }.start();

                while (true) {
                    bos.write(in.readLine() + System.lineSeparator());
                    bos.flush();
                }
            }
        } catch (ConnectException connExc) {
            System.err.println("Could not connect.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(BufferedReader br) {
        try {
            while (true) {
                System.out.println(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}