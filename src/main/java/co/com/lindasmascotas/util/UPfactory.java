
package co.com.lindasmascotas.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Veronica
 */
public class UPfactory {
    private static final EntityManagerFactory FACTORY;
    private static final String BD = "LindasMascotas";
    
    static{
        FACTORY = Persistence.createEntityManagerFactory(BD);
    }
    
    public static EntityManagerFactory getFACTORY(){
        return FACTORY;
    }
}
