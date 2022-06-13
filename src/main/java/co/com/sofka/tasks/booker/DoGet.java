package co.com.sofka.tasks.booker;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class DoGet implements Task {
    private String resource;

    public DoGet usingTheResource(String resource){
        this.resource = resource;
        return this;

    }
    public static DoGet doGet(){
        return new DoGet();

    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource(resource)
        );
    }
}

