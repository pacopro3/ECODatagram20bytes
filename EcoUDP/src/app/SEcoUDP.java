package app;
import java.net.*;
import java.nio.ByteBuffer;
import java.io.*;

public class SEcoUDP{
    public static void main(String[] args) {
        try {
            int bufflen;
            bufflen = 10;
            DatagramSocket s = new DatagramSocket(2000);
            System.out.println("Servidor iniciado ...");
            String msj = "";
            for(;;){
                DatagramPacket p = new DatagramPacket(new byte[bufflen], bufflen);
                s.receive(p);
                System.out.println("Datagrama recibido desde: " + p.getAddress() + ":" + p.getPort());
                if(p.getLength()<bufflen){
                    msj = msj.concat(new String(p.getData(),0,p.getLength()));
                    System.out.println("Recibimos el mensaje: " + msj);
                    for(int i = 0;i<msj.length();i+=bufflen){
                        byte [] m = new byte[Math.min(bufflen,(msj.length()-i))];
                        System.arraycopy(msj.getBytes(), i, m, 0, Math.min(bufflen,(msj.length()-i)));
                        DatagramPacket pp = new DatagramPacket(m, m.length,p.getAddress(),p.getPort());
                        s.send(pp);
                        System.out.println("Eniamos el eco: " + new String(m,0,m.length));
                    }
                    msj = "";
                }else{
                    msj = msj.concat(new String(p.getData(),0,p.getLength()));
                    System.out.println("Recibo datos" + new String(p.getData(),0,p.getLength()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //TODO: handle exception
        }
    }
}
