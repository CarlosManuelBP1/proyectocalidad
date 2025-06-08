import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import qasoft.cotacts_jwt.Model.Contact;
import qasoft.cotacts_jwt.Repository.ContactRepository;
import qasoft.cotacts_jwt.Service.ContactService;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ContactServiceTest {

    @InjectMocks
    private ContactService contactService;

    @Mock
    private ContactRepository contactRepository;

    private Contact contact;

    //Esta prueba verifica que si se intenta actualizar un contacto que no existe
    //mande un error.
    @Test(expected = RuntimeException.class)
    public void testUpdateContact_NotFound() {
        // Simulamos que el contacto con el ID 999 no está en la base de datos
        // buscamos un contacto inexistente.
        when(contactRepository.findById(999)).thenReturn(Optional.empty());
        //Esperamos que el  sistema lance un error porque no se puede actualizar algo que no existe.
        contactService.updateContact(999, contact);
    }
//Esta prueba verifica que si intentas eliminar un
// contacto que sí existe, el sistema realmente lo elimine.
    @Test
    public void testDeleteContact() {
        // Simulamos que el contacto con el ID 1 sí existe en la base de datos.
        when(contactRepository.existsById(1)).thenReturn(true);
        // lo eliminamos
        contactService.deleteContact(1);
        // Verificamos que el sistema haya llamado al metodo
        // para eliminar ese contacto en la base de datos.
        verify(contactRepository, times(1)).deleteById(1);
    }
        //Esta prueba verifica que si intentas eliminar
        // un contacto que no existe, el sistema lance un error.
    @Test(expected = RuntimeException.class)
    public void testDeleteContact_NotFound() {
        // Simulamos que el contacto con el ID 999 no está en la bdD
        // buscamos algo inexistente
        when(contactRepository.existsById(999)).thenReturn(false);
        // Intentamos eliminar un contacto que no existe y esperamos una excepción
        // porque no se puede eliminar algo inexistente.
        contactService.deleteContact(999);
    }
}
