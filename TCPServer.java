import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) {
        // Statiske værdier
        //Port som serveren lytter på
        int port = 5003;
        //Navnet på filen som skal sendes til klienten
        String fileToSend = "dokument.txt"; 

        //Opretter en server socket på den angivne port
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            //Bekræfter i konsolen at serveren kører
            System.out.println("Server kører på port " + port);

            //Venter og acceptere en forbindelse fra en klient
            Socket socket = serverSocket.accept();
            //Skriver ud når en klient er forbundet
            System.out.println("Klient forbundet");

            //Åbner filen der skal sendes
            try (FileInputStream fInput = new FileInputStream(fileToSend);
            //Tilføjer buffer for hurtigere læsning
                 BufferedInputStream bInput = new BufferedInputStream(fInput);
                 // Henter output stream til klienten
                 OutputStream out = socket.getOutputStream()) {

                //Opretter en buffer til data (4 KB ad gangen)
                byte[] buffer = new byte[4096];
                //Variabel til at gemme antal læste bytes
                int bytesRead;

                //Sender filen i bidder
                //Læser filen ind i buffer, -1 betyder slut på fil
                while ((bytesRead = bInput.read(buffer)) != -1) {
                    //Sender de læste bytes til klienten
                    out.write(buffer, 0, bytesRead);
                }
                // Bekræfter når filen er sendt
                System.out.println("Fil sendt færdig: " + fileToSend);
            }

            // Hvis der opstår fejl (fx ingen fil, ingen forbindelse)
        } catch (IOException e) {
            // Udskriver fejlen i konsollen 
            e.printStackTrace();
        }
    }
}