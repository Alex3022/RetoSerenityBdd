package co.com.sofka.stepdefinition.booker;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static co.com.sofka.questions.ReturnStringValue.returnStringValue;
import static co.com.sofka.tasks.booker.DoPost.doPost;
import static co.com.sofka.utils.FileUtilities.readFile;
import static co.com.sofka.utils.Log4jValues.LOG4J_PROPERTIES_FILE_PATH;
import static com.google.common.base.StandardSystemProperty.USER_DIR;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GenerateTokenStepDefinition {

    private static final Logger LOGGER = Logger.getLogger(GenerateTokenStepDefinition.class);
    private static final String AUTH_DATA = System.getProperty("user.dir")+"/src/test/resources/file/booker/auth.json";
    private static final String URL_BASE = "https://restful-booker.herokuapp.com";
    private static final String RESOURCE = "/auth";

    private final HashMap<String,Object> headers = new HashMap<>();
    private final Actor actor = Actor.named("dahiana");

    private String bodyRequest;


    @Dado("el usuario esta en la pagina de inicio de sesion con el de usuario {string} y contraseña {string}")
    public void elUsuarioEstaEnLaPaginaDeInicioDeSesionConElDeUsuarioYContrasenia(String string, String string2) {
        setUpLog4j2();
        actor.can(CallAnApi.at(URL_BASE));

        headers.put("Content-Type","application/json");

        bodyRequest = defineBodyRequest();

    }
    @Cuando("el usuario ingrese la peticion que desea")
    public void elUsuarioIngreseLaPeticionQueDesea() {
        actor.attemptsTo(
                doPost()
                        .usingTheResource(RESOURCE)
                        .withHeaders(headers)
                        .andBodyRequest(bodyRequest)
        );

    }
    @Entonces("se mostrara un codiogo y una respuesta  exitosa y un token")
    public void seMostraraUnCodiogoYUnaRespuestaExitosaYUnToken() {
        String response=new String(LastResponse.received().answeredBy(actor).asByteArray(), StandardCharsets.UTF_8);
        actor.should(
                seeThatResponse(
                        "el codigo de respuesta debe ser: "+ HttpStatus.SC_OK,
                        validatableResponse -> validatableResponse.statusCode(HttpStatus.SC_OK)
                ),
                seeThat(
                        "el mensaje enviado debe ser los datos actualizados",
                        returnStringValue(response),
                        notNullValue()
                )
        );

    }

    private String defineBodyRequest(){
        return readFile(AUTH_DATA);
    }

    private void setUpLog4j2(){
        PropertyConfigurator.configure(USER_DIR.value() + LOG4J_PROPERTIES_FILE_PATH.getValue());
    }
}
