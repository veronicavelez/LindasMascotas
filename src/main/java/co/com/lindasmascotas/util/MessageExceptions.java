
package co.com.lindasmascotas.util;


public class MessageExceptions {
    public static String messageException(String exception){
    
        if (exception == null || exception.contains("not null")){
            return "No fue posible guardar el registro.";
        }
        
        if (exception.contains("exist")){
            return "No se guardó el registro porque ya existe uno con las mismas características.";
        }
        
        if (exception.contains("cannot be destroyed")){
            return "No es posible eliminar el registro.";
        }
        
        return "";
    }
}
