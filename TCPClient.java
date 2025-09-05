import java.io.*;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) {
        // Statiske værdier
        // IP-adressen til serveren (127.0.0.1 = localhost)
        String serverIP = "127.0.0.1";
        // Portnummer som skal matche serveren
        int port = 5003;
        // Filen gemmes lokalt under dette navn
        String outputFile = "modtaget.txt"; // hvor filen skal gemmes

        // Opretter forbindelse til serveren
        try (Socket socket = new Socket(serverIP, port);
        // InputStream til at modtage data
             InputStream in = socket.getInputStream();
             // Opretter ny fil til at gemme data
             FileOutputStream fOut = new FileOutputStream(outputFile);
             // Tilføjer buffer for hurtigere skrivning
             BufferedOutputStream bOut = new BufferedOutputStream(fOut)) {

            // Buffer til at gemme de indkommende data
            byte[] buffer = new byte[4096];
            // Variabel til at gemme antal modtagne bytes
            int bytesRead;

            // modtager filen i bidder
            // Læser fra serveren, -1 = slut
            while ((bytesRead = in.read(buffer)) != -1) {
                // Skriver de modtagne bytes i den lokale fil
                bOut.write(buffer, 0, bytesRead);
            }
            //Bekræfter når filen er modtaget
            System.out.println("Fil modtaget og gemt som: " + outputFile);

            // Hvis der opstår fejl (fx ingen forbindelse, skrivefejl)
        } catch (IOException e) {
            // Udskriver fejlen i konsollen
            e.printStackTrace();
        }
    }
}