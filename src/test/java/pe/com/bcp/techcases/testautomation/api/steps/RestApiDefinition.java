package pe.com.bcp.techcases.testautomation.api.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import pe.com.bcp.techcases.testautomation.api.actors.ApiClient;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RestApiDefinition {

    @Steps
    private ApiClient apiClient;
    private String URL = "";


    @Dado("que la URL es: {string}")
    public void queLaURLEs(String url) {
        URL = getProperty(url);
        assertThat(URL).isNotEmpty();

    }

    @Cuando("consulto la pagina {int}")
    public void consultoLaPagina(int pageNumber) {
        apiClient.searchPageBy(URL, pageNumber);
    }

    @Entonces("valido que el estado del servicio sea {int}")
    public void validoQueElEstadoDelServicioSea(int statusCode) {
        apiClient.validateStatusCode(statusCode);
    }

    @Y("que la respuesta contenga el numero de paginacion solicitado")
    public void queLaRespuestaContengaElNumeroDePaginacionSolicitado() {
        apiClient.validatePageValue();
    }

    @Cuando("creo un nuevo usuario")
    public void creoUnNuevoUsuario(DataTable newUser) {
           List<Map<String, String>> rows = newUser.asMaps(String.class, String.class);
        // validacion de que la tabla tiene los datos esperados
        if (!rows.isEmpty()) {
            // Extraemos 'name' y 'job' de la primera fila
            String name = rows.get(0).get("name");
            String job = rows.get(0).get("job");

            // Verificamos que 'name' y 'job' no sean nulos o vacíos
            assertThat(name).isNotNull();
            assertThat(job).isNotNull();

            // Llamamos al metodo que crea un nuevo usuario
            apiClient.createNewUser(URL, name, job);
        } else {
            // Si la tabla no tiene filas, lanzamos un error
            throw new IllegalArgumentException("No se encontraron datos en la tabla DataTable");
        }
    }

    @Y("obtengo el id del nuevo usuario creado")
    public void obtengoElIdDelNuevoUsuarioCreado(){
        apiClient.saveIdUser();
    }

    @Cuando("actualizo el usuario creado con los nuevos datos")
    public void actualizoElUsuarioCreadoConLosNuevosDatos(DataTable updateUser) {
           List<Map<String, String>> rows = updateUser.asMaps(String.class, String.class);
        // validacion de que la tabla contiene datos
        if (!rows.isEmpty()) {
            // Extraemos 'newName' y 'newjob' de la primera fila de la tabla
            String newName = rows.get(0).get("newName");
            String newJob = rows.get(0).get("newJob");

            // Verificamos que 'newName' y 'newJob' no sean nulos ni vacíos
            assertThat(newName).isNotNull().isNotEmpty();
            assertThat(newJob).isNotNull().isNotEmpty();

            // Llamamos al metodo de la API para actualizar el usuario
            apiClient.updateUserInfo(URL, newName, newJob);
        } else {
            // Si la tabla está vacía, lanzamos un error claro
            throw new IllegalArgumentException("No se encontraron datos en la tabla DataTable");
        }
    }


    @Entonces("que los nuevos datos esten actualizados")
    public void queLosNuevosDatosEstenActualizados() {
        apiClient.validateUpdatedInfo();
    }

    /**
     * Obtiene el valor de las propiedades configuradas en el archivo serenity.conf
     *
     * @param property Nombre de la propiedad
     * @return Valor de la propiedad
     */
    public String getProperty(String property) {
        EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
        return EnvironmentSpecificConfiguration.from(variables).getProperty(property);
    }
}
