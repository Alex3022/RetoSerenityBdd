package co.com.sofka.stepdefinition.post;

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
import static co.com.sofka.tasks.posts.DoPut.doPut;
import static co.com.sofka.utils.FileUtilities.readFile;
import static co.com.sofka.utils.Log4jValues.LOG4J_PROPERTIES_FILE_PATH;
import static com.google.common.base.StandardSystemProperty.USER_DIR;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.CoreMatchers.equalTo;

public class ActualizarStepDefinition {

    private static final Logger LOGGER = Logger.getLogger(ActualizarStepDefinition.class);
    private static final String UPDATE_DATA = System.getProperty("user.dir")+"/src/test/resources/file/posts/UpDate.json";
    private static final String URL_BASE = "https://jsonplaceholder.typicode.com";
    private static final String RESOURCE = "/posts/1";

    private final HashMap<String,Object> headers = new HashMap<>();
    private final Actor actor = Actor.named("dahiana");

    private String bodyRequest;



    @Dado("el usuario tiene toda la informacion del post a actualizar")
    public void elUsuarioTieneTodaLaInformacionDelPostAActualizar() {
        setUpLog4j2();
        actor.can(CallAnApi.at(URL_BASE));

        headers.put("Content-Type","application/json; charset=UTF-8");

        bodyRequest = defineBodyRequest();

    }
    @Cuando("el usuario realiza la peticion")
    public void elUsuarioRealizaLaPeticion() {

        actor.attemptsTo(
                doPut()
                        .usingTheResource(RESOURCE)
                        .withHeaders(headers)
                        .andBodyRequest(bodyRequest)
        );

    }
    @Entonces("recibe el codigo {int} y el contenido actualizado")
    public void recibeElCodigoYElContenidoActualizado(Integer int1) {

        String response=new String(LastResponse.received().answeredBy(actor).asByteArray(), StandardCharsets.UTF_8);
        actor.should(
                seeThatResponse(
                        "el codigo de respuesta debe ser: "+ HttpStatus.SC_OK,
                        validatableResponse -> validatableResponse.statusCode(HttpStatus.SC_OK)
                ),
                seeThat(
                        "el mensaje enviado debe ser los datos actualizados",
                        returnStringValue(response+"\n"),
                        equalTo(defineBodyRequest())
                )
        );


    }

    private String defineBodyRequest(){
        return readFile(UPDATE_DATA);
    }

    private void setUpLog4j2(){
        PropertyConfigurator.configure(USER_DIR.value() + LOG4J_PROPERTIES_FILE_PATH.getValue());
    }
}
