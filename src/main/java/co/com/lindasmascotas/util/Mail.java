package co.com.lindasmascotas.util;

import co.com.lindasmascotas.dtos.CitasDTO;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class Mail {
    
    private static final Properties PROPS = new Properties();
    private static final String CUENTA = "lindasmascotasmed@gmail.com";
    private static final String PASSWORD = "lindasMascotas01";
    private static final String USUARIO = "Lindas Mascotas";
    private static Session session;
    
    private static void init(){
        PROPS.put("mail.smtp.host", "smtp.gmail.com");
        PROPS.put("mail.smtp.starttls.enable", "true");
        PROPS.put("mail.smtp.port", "587");
        PROPS.put("mail.smtp.auth", "true");
        
        session = Session.getDefaultInstance(PROPS, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(CUENTA, PASSWORD);                
            }
        });
    }
    
    public static void enviarNotificacionCita(CitasDTO c){
       try{
           InternetAddress[] to = new InternetAddress[1];
           //to[0] = new InternetAddress(c.getIdPropietario().getCorreoElectronico());
           to[0] = new InternetAddress("medisabel97@gmail.com");
           
           String asunto = "Recordar Cita Lindas Mascotas";
           
           MimeMultipart multipart = new MimeMultipart("related");
           BodyPart mensaje = new MimeBodyPart();
           String body = "Mensaje de prueba";
           mensaje.setContent(body,"text/html");
           multipart.addBodyPart(mensaje);
           
           enviarCorreo(to, asunto, multipart);           
           
       }catch(Exception ex){
           throw new RuntimeException(ex);
       }
        
    }
    
    private static void enviarCorreo(InternetAddress[]to, String asunto, MimeMultipart multipart) throws MessagingException{
        init();
        
        MimeMessage mensaje = new MimeMessage(session);
        mensaje.setFrom(new InternetAddress(CUENTA));
        mensaje.setRecipients(Message.RecipientType.TO, to);
        mensaje.setSubject(asunto);
        mensaje.setContent(multipart);
        
        Transport.send(mensaje);
    }
}
