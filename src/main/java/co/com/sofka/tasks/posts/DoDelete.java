package co.com.sofka.tasks.posts;

import co.com.sofka.tasks.booker.DoGet;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Delete;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class DoDelete implements Task {
    private String resource;

    public DoDelete usingTheResource(String resource){
        this.resource = resource;
        return this;

    }
    public static DoDelete doDelete(){
        return new DoDelete();

    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Delete.from(resource)
        );
    }
}
