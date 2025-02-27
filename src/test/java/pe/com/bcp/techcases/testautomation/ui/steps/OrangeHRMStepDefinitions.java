package pe.com.bcp.techcases.testautomation.ui.steps;
import pe.com.bcp.techcases.testautomation.ui.steps.orange.OrangeHRMSteps;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.thucydides.core.annotations.Steps;
import io.cucumber.datatable.DataTable;
import java.util.Map;


public class OrangeHRMStepDefinitions {

    @Steps
    OrangeHRMSteps orangeHRMSteps;

    @Dado("que el usuario inicia sesión en Orange HRM con las credenciales correctas")
    public void iniciarSesion() {
        orangeHRMSteps.abrirPaginaLogin();
        orangeHRMSteps.ingresarCredenciales("Admin", "admin123");
        orangeHRMSteps.clickEnLogin();
    }

    @Cuando("navega a la opción {string} y selecciona {string}")
    public void navegarAOpcion(String menu, String subMenu) {
        orangeHRMSteps.navegarAOpcion(menu, subMenu);
    }

    @Cuando("ingresa los siguientes datos del nuevo empleado:")
    public void ingresarDatosEmpleado(DataTable dataTable) {
        orangeHRMSteps.ingresarDatosEmpleado(dataTable);
    }


    @Cuando("guarda el nuevo empleado")
    public void guardarNuevoEmpleado() {
        orangeHRMSteps.guardarEmpleado();
    }

    @Entonces("el sistema muestra el perfil del empleado con la información ingresada")
    public void validarPerfilEmpleado() {
        orangeHRMSteps.validarPerfilEmpleado();
    }

    @Cuando("completa y guarda los datos personales adicionales:")
    public void completarDatosPersonales(Map<String, String> datosAdicionales) {
        orangeHRMSteps.completarDatosPersonales(datosAdicionales);
    }

    @Entonces("el sistema confirma que los datos personales fueron actualizados correctamente")
    public void validarActualizacionDatosPersonales() {
        orangeHRMSteps.validarActualizacionDatos();
    }

    @Cuando("navega a la opción Employee List")
    public void navegaALaOpciónEmployeeList() {
        orangeHRMSteps.navegarAEmployeeList();
    }

    @Cuando("busca al nuevo empleado por su {string}")
    public void buscarEmpleado(String criterio) {
        orangeHRMSteps.buscarEmpleado(criterio);
    }


    @Entonces("valida que el empleado existe en la lista")
    public void validarEmpleadoEnLista() {
        orangeHRMSteps.validarEmpleadoEnLista();
    }

    @Cuando("el usuario cierra sesión del sistema")
    public void cerrarSesion() {
        orangeHRMSteps.cerrarSesion();
    }

}
