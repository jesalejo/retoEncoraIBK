package pe.com.bcp.techcases.testautomation.ui.steps.orange;

import net.thucydides.core.annotations.Step;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pe.com.bcp.techcases.testautomation.ui.pages.orange.OrangeLoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import net.serenitybdd.core.pages.PageObject;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.Keys;
import java.util.List;
import java.util.Map;


public class OrangeHRMSteps extends PageObject {

    OrangeLoginPage loginPage;

    @Step("Abrir la página de inicio de sesión de OrangeHRM")
    public void abrirPaginaLogin() {
        loginPage.open();
    }

    @Step("Ingresar credenciales: usuario {0} y contraseña {1}")
    public void ingresarCredenciales(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @Step("Hacer clic en el botón de login")
    public void clickEnLogin() {
        loginPage.clickLogin();
    }

    @Step("Navegar a la opción {0} y seleccionar {1}")
    public void navegarAOpcion(String menu, String subMenu) {
        WebElement menuElement = getDriver().findElement(By.xpath("//span[text()='" + menu + "']"));
        menuElement.click();

        if (subMenu != null) {
            WebElement subMenuElement = getDriver().findElement(By.xpath("//a[text()='" + subMenu + "']"));
            subMenuElement.click();
        }
    }

    @Step("Ingresar los datos del nuevo empleado")
    public void ingresarDatosEmpleado(DataTable dataTable) {
        List<Map<String, String>> datosEmpleado = dataTable.asMaps(String.class, String.class);
        Map<String, String> empleado = datosEmpleado.get(0);
        getDriver().findElement(By.name("firstName")).sendKeys(empleado.get("First Name"));
        getDriver().findElement(By.name("middleName")).sendKeys(empleado.get("Middle Name"));
        getDriver().findElement(By.name("lastName")).sendKeys(empleado.get("Last Name"));

        WebDriverWait wait = new WebDriverWait(getDriver(), 5);
        WebElement campoEmployeeId = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//body/div[@id='app']/div[1]/div[2]/div[2]/div[1]/div[1]/form[1]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[2]/input[1]"))
        );

        campoEmployeeId.sendKeys(Keys.CONTROL + "a");
        campoEmployeeId.sendKeys(Keys.BACK_SPACE);
        campoEmployeeId.sendKeys(empleado.get("Employee Id"));
    }


    @Step("Guardar el nuevo empleado")
    public void guardarEmpleado() {
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
    }

    @Step("Validar que el perfil del empleado fue creado correctamente")
    public void validarPerfilEmpleado() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 5);
        WebElement perfil = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Personal Details']")));
        assert perfil.isDisplayed();
    }

    @Step("Completar datos personales adicionales")
    public void completarDatosPersonales(Map<String, String> datosAdicionales) {
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);

        for (Map.Entry<String, String> entry : datosAdicionales.entrySet()) {
            String campoLabel = entry.getKey();
            String valor = entry.getValue();

            String xpathCampo = getXpathForCampo(campoLabel);
            WebElement campo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathCampo)));

            if (campoLabel.equals("Nationality") || campoLabel.equals("Marital Status")) {

                campo.click();
                WebElement opcion = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@role='listbox']//span[text()='" + valor + "']")
                ));
                opcion.click();
            } /*else if (campoLabel.equals("Gender")) {
                // Manejo de radio buttons para género
                String xpathGenero = "//label[contains(text(),'" + valor + "')]/../input[@type='radio']";
                WebElement radioButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathGenero)));
                radioButton.click();
            } else {
                campo.clear();
                campo.sendKeys(valor);
            }*/
        }

        WebElement botonGuardar = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//body/div[@id='app']/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/form[1]/div[5]/button[1]")));
        botonGuardar.click();
    }

    private String getXpathForCampo(String campoLabel) {
        switch(campoLabel) {
            case "NickName":
                return "//body/div[@id='app']/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/form[1]/div[2]/div[1]/div[2]/div[1]/div[2]/input[1]";
            case "Driver's License Number":
                return "//body/div[@id='app']/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/form[1]/div[2]/div[2]/div[1]/div[1]/div[2]/input[1]";
            case "License Expiry Date":
                return "//body/div[@id='app']/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/form[1]/div[2]/div[2]/div[2]/div[1]/div[2]/div[1]/div[1]/input[1]";
            case "Nationality":
                return "//body/div[@id='app']/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/form[1]/div[3]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/i[1]";
            case "Marital Status":
                return "//body/div[@id='app']/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/form[1]/div[3]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[2]/i[1]";
            case "Date of Birth":
                return "//body/div[@id='app']/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/form[1]/div[3]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/input[1]";
            //case "Gender":
            //    return "//body/div[@id='app']/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/form[1]/div[3]/div[2]/div[2]/div[1]/div[2]";
            default:
                System.out.println("Campo no reconocido: " + campoLabel);
                return "//input";
        }
    }

    @Step("Validar actualización de datos personales")
    public void validarActualizacionDatos() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        WebElement perfil = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(),'Employee Full Name')]")));
        assert perfil.isDisplayed();


    }

    @Step("Navegar a la opción Employee List")
    public void navegarAEmployeeList() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 5);
        WebElement subMenuElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[text()='Employee List']")));
           subMenuElement.click();
    }

    @Step("Buscar empleado por {0}")
    public void buscarEmpleado(String criterio) {

        WebElement campoBusqueda = getDriver().findElement(By.xpath("//body/div[@id='app']/div[1]/div[2]/div[2]/div[1]/div[1]/div[2]/form[1]/div[1]/div[1]/div[2]/div[1]/div[2]/input[1]"));
        campoBusqueda.sendKeys(criterio);
        campoBusqueda.submit();
    }


    @Step("Validar que el empleado existe en la lista")
    public void validarEmpleadoEnLista() {
        WebElement resultado = getDriver().findElement(By.xpath("//body/div[@id='app']/div[1]/div[2]/div[2]/div[1]/div[2]/div[2]/div[1]/span[1]"));
        assert resultado.isDisplayed();
    }

    @Step("Cerrar sesión del sistema")
    public void cerrarSesion() {
        getDriver().findElement(By.xpath("//span[text()='Logout']")).click();
    }
}
