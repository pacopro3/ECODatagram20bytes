package app;
import java.net.*;
import java.io.*;

public class CEcoUDP{
    public static void main(String[] args) {
        try {
            int bufflen = 10;
            DatagramSocket cl = new DatagramSocket();
            System.out.println("Cliente iniciado. Escriba un mensaje:");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String mensaje = br.readLine();
            byte[] b = mensaje.getBytes();
            String dst = "127.0.0.1";
            int port = 2000;
            int contador = 0;
            for(int i = 0;i<b.length;i+=bufflen){
                contador++;
                byte [] msj = new byte[Math.min(bufflen,(b.length-i))];
                System.arraycopy(b, i, msj, 0, Math.min(bufflen,(b.length-i)));
                DatagramPacket p = new DatagramPacket(msj, msj.length,InetAddress.getByName(dst),port);
            cl.send(p);
            }
            String eco = "";
            for(int i = 0;i<contador;i++){
                DatagramPacket p = new DatagramPacket(new byte[bufflen], bufflen);
                cl.receive(p);
                eco = eco.concat(new String(p.getData(),0,p.getLength()));
            }

            System.out.println("El eco recibido es : " + eco);
            
            cl.close();
        } catch (Exception e) {
            e.printStackTrace();
            //TODO: handle exception
        }
    }
}