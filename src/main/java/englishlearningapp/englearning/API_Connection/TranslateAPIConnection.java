package englishlearningapp.englearning.API_Connection;

import englishlearningapp.englearning.Controller.AlertController;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
public class TranslateAPIConnection {
    private static HttpURLConnection connection;
    private static boolean isConnected = true;

    public static boolean isIsConnected() {
        return isConnected;
    }

    public static String translateText(String langFrom, String langTo, String text) {
        TranslateInitTask task = new TranslateInitTask(langFrom, langTo, text);
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        try {
            connection = task.call();
        } catch (Exception e) {
            //throw new RuntimeException(e);
            System.out.println("khong co ket noi");
            isConnected = false;
            Platform.runLater(() -> {
                AlertController.showNotConnectInternet();
            });
        }
        StringBuilder reponse =new StringBuilder();
        if(connection != null) {
            BufferedReader input = null;
            try {
                input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } catch (IOException e) {
                //throw new RuntimeException(e);
                System.out.println("khong co ket noi");
                isConnected = false;
                Platform.runLater(() -> {
                    AlertController.showNotConnectInternet();
                });
            }
            String line;
            while (true && input != null){
                try {
                    if (((line = input.readLine()) != null)){
                        reponse.append(line);
                    } else {
                        break;
                    }
                } catch (IOException e) {
                    //throw new RuntimeException(e);
                    System.out.println("khong the doc file rong");
                    isConnected = false;
                }
            }
            try {
                input.close();
            } catch (IOException e) {
                //throw new RuntimeException(e);
                System.out.println("khong the dong file rong");
                isConnected = false;
            }
        }

        return reponse.toString();
    }
    public static void main(String[] args) throws Exception {
        System.out.println(translateText("en", "vi", "This is a piece of text, can you translate it?"));
        System.out.println(connection);
    }
}
