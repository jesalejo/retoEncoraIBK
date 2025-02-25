package pe.com.bcp.techcases.testautomation.api.actors;

import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.junit.jupiter.api.Assertions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ApiClient {

    private Response response;
    private static final String contentType = "application/json";
    private String createdUserId; // Variable para almacenar el ID del usuario creado


   /* @Step
    public void readStatus(String url) {
        //Agrega lo necesario para ver en el log de ejecución la Petición
        SerenityRest.useRelaxedHTTPSValidation();
        response = SerenityRest
                .given().contentType(contentType).log().all()
                .when().get(url);
        response.
            then().log().all().assertThat().statusCode(200);
    }

    @Step
    public void readContent(String expectedMessage) {
        response.
            then().body("status", equalTo(expectedMessage));
    }*/

    @Step
    public void searchPageBy(String url, int pageNumber) {
        Serenity.setSessionVariable("expectedPage").to(pageNumber);
        //Agrega lo necesario para ver en el log de la Petición
        //Agregar la consulta de QueryParams
        SerenityRest.useRelaxedHTTPSValidation();
        response = SerenityRest
                .given().contentType(contentType)
                //.queryParam("page", pageNumber)
                .when().get(url);
    }

    @Step
    public void validateStatusCode(int statusCode) {
        //Agrega lo necesario para ver en el log de ejecución la Respuestas
        response.then()
                .assertThat()
                .statusCode(statusCode);
    }

    @Step
    public void validatePageValue() {
        //Indicar el jsonPath necesario para obtener el valor de la respuesta
        int currentPage = response.getBody().jsonPath().getInt("<Json Path>");

        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Número de página >>> {0}", currentPage);
        int expectedPage = Serenity.sessionVariableCalled("expectedPage");
        Assertions.assertEquals(expectedPage, currentPage,
                "El valor de la pagina esperado no es igual al obtenido.");
    }

    @Step
    public void createNewUser(String url, String name, String job) {
        SerenityRest.useRelaxedHTTPSValidation();

        String req = "{\n" +
                "    \"name\": \""+name+"\",\n" +
                "    \"job\": \""+job+"\"\n" +
                "}";
        //Agrega lo necesario para ver en el log de la Petición
        response = SerenityRest
                .given().contentType(contentType)
                .body("{\"name\": \"" + name + "\", \"job\": \"" + job + "\"}")
                .post(url + "/api/users");

        // Obtenemos el ID del usuario creado desde la respuesta
        createdUserId = response.jsonPath().getString("id");

        // Verificamos que el ID no sea null o vacío
        if (createdUserId == null || createdUserId.isEmpty()) {
            throw new RuntimeException("No se pudo obtener el ID del usuario creado.");
        }
    }

    @Step
    public void saveIdUser() {
        // Obtener el ID del usuario de la respuesta utilizando el jsonPath "id"
        String id = response.getBody().jsonPath().getString("id");

        // Imprimir el ID para verificar que se está extrayendo correctamente
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Id del nuevo usuario >>> {0}", id);

        // Guardar el ID en la sesión de Serenity para su uso posterior
        Serenity.setSessionVariable("newUserId").to(id);
    }


    @Step
    public void updateUserInfo(String url, String newName, String newJob) {
        SerenityRest.useRelaxedHTTPSValidation();

        String id = Serenity.sessionVariableCalled("newUserId"); // Obtener el ID del nuevo usuario creado

        // Guardamos los nuevos valores esperados
        Serenity.setSessionVariable("expectedNewName").to(newName);
        Serenity.setSessionVariable("expectedNewJob").to(newJob);

        // Construimos el cuerpo de la solicitud
        String req = "{\n" +
                "    \"name\": \"" + newName + "\",\n" +
                "    \"job\": \"" + newJob + "\"\n" +
                "}";

        // Realizamos la solicitud PUT para actualizar el usuario con el ID
        response = SerenityRest
                .given().contentType(contentType)
                .body(req) // Usamos el cuerpo de la solicitud con los datos nuevos
                .put(url + "/api/users/" + id); // Usamos el ID para realizar el PUT
    }


    @Step
    public void validateUpdatedInfo() {
        String expectedNewName = Serenity.sessionVariableCalled("expectedNewName");
        String expectedNewJob = Serenity.sessionVariableCalled("expectedNewJob");

        // Recuperar los valores actualizados de la respuesta
        String newName = response.getBody().jsonPath().getString("name");
        String newJob = response.getBody().jsonPath().getString("job");

        // Agregar log para verificar la respuesta
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Nuevo nombre del usuario >>> {0}", newName);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Nuevo trabajo del usuario >>> {0}", newJob);

        // Verificar si los valores son nulos
        if (newName == null || newJob == null) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "La respuesta contiene valores nulos para el nombre o el trabajo.");
        }

        // Validar que los datos coincidan con lo esperado
        Assertions.assertEquals(expectedNewName, newName, "El nombre actualizado no coincide.");
        Assertions.assertEquals(expectedNewJob, newJob, "El trabajo actualizado no coincide.");
    }

}
