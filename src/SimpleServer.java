import java.net.*;
import java.io.*;

public class SimpleServer {
    private static WriterRepository repository = WriterRepository.getInstance();
    public static void main(String args[]) {
        ServerSocket s = null;
        try {
            s = new ServerSocket(5432);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Socket s1 = s.accept();
                new Thread() {
                    public void run() {
                        try {
                            try (BufferedReader br = new BufferedReader(new InputStreamReader(s1.getInputStream()));
                                 BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s1.getOutputStream()))) {
                                int userId = repository.addWriter(bw);
                                while (true) {
                                    repository.pushMessage(br.readLine(), userId);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}