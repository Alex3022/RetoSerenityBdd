package co.com.sofka.stepdefinition.post;


import co.com.sofka.stepdefinition.booker.PingStepDefinition;
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
import static co.com.sofka.tasks.posts.DoDelete.doDelete;
import static co.com.sofka.utils.Log4jValues.LOG4J_PROPERTIES_FILE_PATH;
import static com.google.common.base.StandardSystemProperty.USER_DIR;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.CoreMatchers.equalTo;

public class EliminarStepDefinition {

    private static final Logger LOGGER = Logger.getLogger(EliminarStepDefinition.class);
    private static final String URL_BASE = "https://jsonplaceholder.typicode.com";
    private static final String RESOURCE = "/posts/1";

    private final Actor actor = Actor.named("dahiana");
    @Dado("que el usuario tiene la informacion del post para eliminar")
    public void queElUsuarioTieneLaInformacionDelPostParaEliminar() {
        setUpLog4j2();
        actor.can(CallAnApi.at(URL_BASE));

    }
    @Cuando("el usuario haga la peticion")
    public void elUsuarioHagaLaPeticion() {
        actor.attemptsTo(
                doDelete()
                        .usingTheResource(RESOURCE)
        );

    }
    @Entonces("el usuario recibe el codigo {int}")
    public void elUsuarioRecibeElCodigo(Integer int1) {
        String response=new String(LastResponse.received().answeredBy(actor).asByteArray(), StandardCharsets.UTF_8);
        actor.should(
                seeThatResponse(
                        "el codigo de respuesta debe ser: "+ HttpStatus.SC_OK,
                        validatableResponse -> validatableResponse.statusCode(HttpStatus.SC_OK)
                ),
                seeThat(
                        "el mensaje enviado debe ser los datos actualizados",
                        returnStringValue(response),
                        equalTo("{}")
                )
        );

    }
    private void setUpLog4j2(){
        PropertyConfigurator.configure(USER_DIR.value() + LOG4J_PROPERTIES_FILE_PATH.getValue());
    }
}

