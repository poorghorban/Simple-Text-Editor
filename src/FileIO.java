import java.io.*;

public class FileIO {

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    // exists file
    public boolean existsFile(String path){
        File temp = new File(path);

        if (temp.exists()){
            return true;
        }else{
            return false;
        }
    }

    // open file
    public void openFile(String path , char mode) throws IOException {

        if(mode == 'r') {
            this.bufferedReader = new BufferedReader(new FileReader(path));
        }else {
            this.bufferedWriter = new BufferedWriter(new FileWriter(path));
        }
    }

    // close file
    public void closeFile(char mode) throws IOException {
        if(mode == 'r') {
            this.bufferedReader.close();
        }else {
            this.bufferedWriter.close();
        }
    }

    // read file
    public String readFile() throws IOException {

        String content_file = "";

        String line;

        while((line = bufferedReader.readLine()) != null) {
            content_file = content_file + "\n" + line;
        }

        return content_file;
    }

    // write file
    public void writeFile(String str) throws IOException {
        this.bufferedWriter.write(str);
    }
}
