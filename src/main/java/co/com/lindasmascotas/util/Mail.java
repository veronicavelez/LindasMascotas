package co.com.lindasmascotas.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ISABEL MEDINA
 */
public class Mail {

    private static final Properties props;

    static {
        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.setProperty("mail.user", "lindasmascotasmed@gmail.com");
        props.setProperty("mail.password", "lindasMascotas01");
    }

    public static Boolean EnviarMail(String asunto,String mailDestino,String mensaje) throws MessagingException, UnsupportedEncodingException {
        try {
            Session mailSession = Session.getInstance(props, null);
            Message msg = new MimeMessage(mailSession);
            msg.setSubject(asunto);
            msg.setFrom(new InternetAddress("lindasmascotasmed@gmail.com", "Lindas Mascotas"));
            msg.addRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(mailDestino)});

            DataHandler dh = new DataHandler(mensaje, "text/plain");
            msg.setDataHandler(dh);
            javax.mail.Transport.send(msg);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}
