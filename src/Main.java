import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    // clear screen console
    public static void clear_console() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    // main menu
    public static void main_menu(){
        // title menu
        System.out.println("\t\t************************************************");
        System.out.println("\t\t************************************************");
        System.out.println("\t\t************** SIMPLE TEXT EDITOR **************");
        System.out.println("\t\t************************************************");
        System.out.println("\t\t************************************************");
        System.out.println('\n');

        // menu
        System.out.println("1)Open Editor");
        System.out.println("2)Import File");
        System.out.println("3)Add Word");
        System.out.println("4)Delete Word");
        System.out.println("5)Dictionary");
        System.out.println("6)Export Dictionary");
        System.out.println("0)Exit");
        System.out.println('\n');
    }

    // open editor
    public static void open_editor(TrieTree tree) throws IOException, InterruptedException {

        // string for save all word enter user in console
        String text = "";

        // create scanner
        Scanner scanner = new Scanner(System.in);

        while(true){

            // clear screen console
            clear_console();

            // title
            System.out.println("\t\t************************************************");
            System.out.println("\t\t***************** TEXT EDITOR ******************");
            System.out.println("\t\t************************************************");
            System.out.println();

            // info
            System.out.println("#####################################################");
            System.out.println();
            System.out.println("InteliSence: part of word + #t + Enter key");
            System.out.println("Save and Back: #e + Enter key");
            System.out.println();
            System.out.println("######################################################");
            System.out.println();

            // print text
            System.out.print(text);

            String word;

            while(scanner.hasNext()){

                word = scanner.next();

                if(word.contains("#e")){

                    if (word.length() > 2){
                        text = text + " " + word.split("#");
                    }

                    // ask the user to save the file or not
                    System.out.println();
                    System.out.print("Save file (y/n):");
                    String result = scanner.next().toLowerCase();

                    // check result
                    if(result.equals("y")){
                        System.out.println();
                        System.out.print("Please,Enter path for save dictionary(path/filename.txt):");
                        String path = scanner.next();

                        // create fileio class instance
                        FileIO f = new FileIO();

                        try {
                            // open file
                            f.openFile(path , 'w');

                            // write text to file
                            f.writeFile(text);

                            // close file
                            f.closeFile('w');

                            System.out.println();
                            System.out.println("Success:The dictionary was saved to the file!!!");
                            TimeUnit.SECONDS.sleep(3);
                            return;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }else{
                        return;
                    }

                }else if (word.contains("#t")){

                    String sub_word = word.split("#")[0];

                    // get a list of words that contain this sub string
                    String[] list_words = tree.getListOfWordInTrieTree(sub_word).split(" ");

                    // check list words is empty or not
                    if(list_words.length != 0) {

                        // show words
                        System.out.println();
                        for (int i = 0; i < list_words.length; i++) {
                            System.out.print((i + 1) + "-" + list_words[i] + " ");
                        }
                        System.out.println();
                        //get choice
                        System.out.println();
                        System.out.print("Please,Enter your choice:");
                        int index = scanner.nextInt();

                        // add to text
                        text = text + " " + list_words[index-1];
                    }
                    // exit while(scanner.hasNext())
                    break;
                }else{

                    // add word to text for save to file
                    text = text + " " + word;

                   // preprocess content file:1) convert to lower case 2) remove punctuation 3) remove digits
                    word = word.toLowerCase();
                    word = word.replaceAll("\\p{Punct}|\\d+","");

                    // add word to trie tree
                    tree.addWord(word);
                }
            }
        }

    }

    // import file
    public static void import_file_page(TrieTree tree) throws IOException, InterruptedException {

        // clear screen console
        clear_console();

        // title
        System.out.println("\t\t************************************************");
        System.out.println("\t\t****************** IMPORT FILE *****************");
        System.out.println("\t\t************************************************");
        System.out.println('\n');

        //get path for save dictionary
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please,Enter path file(path/filename.txt):");
        String path  = scanner.next();

        FileIO f = new FileIO();

        if(f.existsFile(path)){
            try {
                // open file for read
                f.openFile(path , 'r');

                // read content file
                String content_file = f.readFile();

                // close file
                f.closeFile('r');

                // preprocess content file:1) convert to lower case 2) remove punctuation 3) remove digits
                content_file = content_file.toLowerCase();
                content_file = content_file.replaceAll("\\p{Punct}|\\d+","");

                // convert string to string array
                String[] list_word = content_file.trim().split("\\W+");

                // add words to trie tree
                for(int i=0 ; i< list_word.length ; i++){
                    tree.addWord(list_word[i].trim());
                }

                System.out.println();
                System.out.println("SUCCESS: All words add to trie tree!!!");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println();
            System.out.println("Error:There is no this file in this path!!!");
        }
    }

    // add word
    public static void add_word_page(TrieTree tree) throws IOException, InterruptedException {

        // clear screen console
        clear_console();

        // title
        System.out.println("\t\t************************************************");
        System.out.println("\t\t******************* ADD WORD *******************");
        System.out.println("\t\t************************************************");
        System.out.println('\n');

        //get word
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please,Enter word:");
        String word  = scanner.next();

        System.out.println('\n');

        // check length word greater than 2
        if (word.length() < 2){
            System.out.println("Error:Enter the word with more than 2 letters!!!");
        }else{

            try {
                tree.addWord(word);
                System.out.println("SUCCESS:Add word successfull!!!");
            }catch (Exception e){
                System.out.println("Error:Add word failed!!!");
            }
        }
    }

    // delete word
    public static void delete_word_page(TrieTree tree) throws IOException, InterruptedException {

        // clear screen console
        clear_console();

        // title
        System.out.println("\t\t************************************************");
        System.out.println("\t\t****************** DELETE WORD *****************");
        System.out.println("\t\t************************************************");
        System.out.println('\n');

        //get word
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please,Enter word:");
        String word  = scanner.next();

        System.out.println('\n');

        // check length word greater than 2
        if (word.length() < 2){
            System.out.println("Error:Enter the word with more than 2 letters!!!");
        }else{

            boolean flag = tree.deleteWord(word);

            if (flag) {
                System.out.println("SUCCESS:deleted word !!!");
            }else{
                System.out.println("Error:delete word failed(this word not exists)!!!");
            }
        }
    }

    // show dictionary
    public static void show_dictionary_page(TrieTree tree) throws IOException, InterruptedException {

        // clear screen console
        clear_console();

        // title menu
        System.out.println("\t\t************************************************");
        System.out.println("\t\t**************** SHOW DICTIONARY ***************");
        System.out.println("\t\t************************************************");
        System.out.println('\n');

        String dictionary = tree.getListOfWordInTrieTree("");

        if (dictionary != ""){
            System.out.println(dictionary);
        }else{
            System.out.println();
            System.out.println("Error:TrieTree is empty!!!");
        }
    }

    // export dictionary to path
    public static void export_dictionary_page(TrieTree tree) throws IOException, InterruptedException {

        // clear screen console
        clear_console();

        // title
        System.out.println("\t\t************************************************");
        System.out.println("\t\t************** EXPORT DICTIONARY ***************");
        System.out.println("\t\t************************************************");
        System.out.println('\n');

        //get path for save dictionary
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please,Enter path for save dictionary(path/filename.txt):");
        String path  = scanner.next();

        // get dictionary word from trie tree
        String dictionary = tree.getListOfWordInTrieTree("");

        if(dictionary != ""){
            FileIO f = new FileIO();
            try {
                // open file for write
                f.openFile(path , 'w');

                // write dictionary to file
                f.writeFile(dictionary);

                // close file
                f.closeFile('w');

                //
                System.out.println();
                System.out.println("Success:The dictionary was saved to the file!!!");
            } catch (IOException e) {
                System.out.println();
                System.out.println("Error:" + e);
            }
        }else{
            System.out.println();
            System.out.println("Error:Trie Tree is empty!!!");
        }
    }


    public static void main(String [] args) throws IOException, InterruptedException {

        // choice option menu
        int choice_option_main_menu = 0;

        TrieTree tree = new TrieTree();

        while (true) {

            // clear screen console
            clear_console();

            // show main menu
            main_menu();

            //get the selected option from the menu
            Scanner scanner = new Scanner(System.in);
            System.out.print("Please,Enter your choice or exit:");
            choice_option_main_menu = scanner.nextInt();

            switch (choice_option_main_menu){
                case 1:
                    open_editor(tree);
                    break;
                case 2:
                    while(true) {
                        import_file_page(tree);

                        // check to back or refresh page
                        System.out.println();
                        System.out.print("Enter 'b' to back previous page or 'r' to refresh page:");
                        String c = scanner.next();

                        if(c.equals("b")) {
                            break;
                        }
                    }
                    break;
                case 3:
                    while(true) {
                        add_word_page(tree);

                        // check to back or refresh page
                        System.out.println();
                        System.out.print("Enter 'b' to back previous page or 'r' to refresh page:");
                        String c = scanner.next();

                        if(c.equals("b")) {
                            break;
                        }
                    }
                    break;
                case 4:
                    while(true) {
                        delete_word_page(tree);

                        // check to back or refresh page
                        System.out.println();
                        System.out.print("Enter 'b' to back previous page or 'r' to refresh page:");
                        String c = scanner.next();

                        if(c.equals("b")) {
                            break;
                        }
                    }
                    break;
                case 5:
                    while(true) {
                        show_dictionary_page(tree);

                        // check to back or refresh page
                        System.out.println();
                        System.out.print("Enter 'b' to back previous page or 'r' to refresh page:");
                        String c = scanner.next();

                        if(c.equals("b")) {
                            break;
                        }
                    }
                    break;
                case 6:
                    while(true) {
                        export_dictionary_page(tree);

                        // check to back or refresh page
                        System.out.println();
                        System.out.print("Enter 'b' to back previous page or 'r' to refresh page:");
                        String c = scanner.next();

                        if(c.equals("b")) {
                            break;
                        }
                    }
                    break;
                case 0:
                    System.exit(0);
            }
        }

    }
}
