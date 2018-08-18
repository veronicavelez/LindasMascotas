package co.com.lindasmascotas.util;

import java.util.Properties;


import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;


/**
 *
 * @author ISABEL MEDINA
 */
public class Mail {
    
    String usuarioCorreo;
    String password;
    
    String destinatario;
    String asunto;
    String mensaje;
    
    
    public Mail(String usuarioCorreo, String password, String destinatario, String asunto, String mensaje) {
       this.usuarioCorreo = usuarioCorreo;
       this.password = password;
       this.destinatario = destinatario;
       this.asunto = asunto;
       this.mensaje = mensaje;
    }
    
  
    public Mail(String usuarioCorre, String password, String destinatario, String mensaje){
        this(usuarioCorre, password, destinatario,"",mensaje);
        
     } 
       
    public Boolean EnviarMail() {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", usuarioCorreo);
            props.setProperty("mail.smtp.auth", "true");
            
            
            Session session = Session.getDefaultInstance(props, null);
            BodyPart texto = new MimeBodyPart();
            texto.setText(mensaje);
            
           
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(usuarioCorreo));
            message.addRecipient( 
                    Message.RecipientType.TO,
                    new InternetAddress(destinatario));
                    message.setSubject(asunto);
            
            Transport t = session.getTransport("smtp");
            t.connect(usuarioCorreo, password);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    
    public static void main(String[] args){
      String clave= "lindasMascotas01";
      Mail m = new Mail("lindasmascotasmed@gmail.com", clave,"medisabel97@gmail.com", "Recordar Cita");
        if(m.EnviarMail()){
            JOptionPane.showMessageDialog(null, "El Correo se mando correctamente");
            }else{
            JOptionPane.showMessageDialog(null, "El correo no se mando correctamente");
        }
    }
}
    

            

 
                    
        


