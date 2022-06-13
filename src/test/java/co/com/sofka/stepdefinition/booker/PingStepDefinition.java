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

import static co.com.sofka.questions.ReturnStringValue.returnStringValue;
import static co.com.sofka.tasks.booker.DoGet.doGet;
import static co.com.sofka.utils.Log4jValues.LOG4J_PROPERTIES_FILE_PATH;
import static com.google.common.base.StandardSystemProperty.USER_DIR;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.CoreMatchers.containsString;

public class PingStepDefinition {

    private static final Logger LOGGER = Logger.getLogger(PingStepDefinition.class);
    private static final String URL_BASE = "https://restful-booker.herokuapp.com";
    private static final String RESOURCE = "/ping";

    private final Actor actor = Actor.named("dahiana");


    @Dado("dado que el admininistrador se encuentra validando el servicio ping")
    public void dadoQueElAdmininistradorSeEncuentraValidandoElServicioPing() {
        setUpLog4j2();
        actor.can(CallAnApi.at(URL_BASE));
    }
    @Cuando("realiza la peticion get")
    public void realizaLaPeticionGet() {
        actor.attemptsTo(
                doGet()
                        .usingTheResource(RESOURCE)
        );


    }
    @Entonces("recibe un codigo exitoso y el mensaje Created")
    public void recibeUnCodigoExitosoYElMensajeCreated() {

        String response=new String(LastResponse.received().answeredBy(actor).asByteArray(), StandardCharsets.UTF_8);
        actor.should(
                seeThatResponse(
                        "el codigo de respuesta debe ser: "+ HttpStatus.SC_CREATED,
                        validatableResponse -> validatableResponse.statusCode(HttpStatus.SC_CREATED)
                ),
                seeThat(
                        "el mensaje enviado debe ser Created",
                        returnStringValue(response),
                        containsString("Created")


                )
        );

    }

    private void setUpLog4j2(){
        PropertyConfigurator.configure(USER_DIR.value() + LOG4J_PROPERTIES_FILE_PATH.getValue());
    }
}
