package co.com.sofka.tasks.posts;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Put;

import java.util.HashMap;

public class DoPut implements Task  {
    private String resource;
    private HashMap<String, Object> headers = new HashMap<>();
    private String bodyRequest;

    public DoPut usingTheResource(String resource) {
        this.resource = resource;
        return this;
    }

    public DoPut withHeaders(HashMap<String, Object> headers) {
        this.headers = headers;
        return this;
    }

    public DoPut andBodyRequest(String bodyRequest) {
        this.bodyRequest = bodyRequest;
        return this;
    }
    public static DoPut doPut(){
        return new DoPut();

    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Put.to(resource).with(
                        requestSpecification -> requestSpecification.relaxedHTTPSValidation()
                                .headers(headers)
                                .log().all()
                                .body(bodyRequest)
                )
        );
    }
}
