import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriterRepository {
    private int size = 0;
    private List<BufferedWriter> list = new ArrayList();
    private static WriterRepository INSTANCE;

    private WriterRepository(){
    }

    public static WriterRepository getInstance(){
//        synchronized (INSTANCE) {
            if(INSTANCE == null){
                INSTANCE = new WriterRepository();
            }
//        }
        return INSTANCE;
    }

    public int addWriter(BufferedWriter bufferedWriter) {
        synchronized (list) {
            list.add(bufferedWriter);
            return ++size;
        }
    }

    public void pushMessage(String message, int userId) throws IOException {
        synchronized (list) {
            for(int i = 0; i < list.size(); i++) {
                if (i != userId - 1) {
                    list.get(i).write("user " + userId + ": "+ message + System.lineSeparator());
                    list.get(i).flush();
                }
            }
        }
    }
}