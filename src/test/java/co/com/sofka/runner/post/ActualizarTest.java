package co.com.sofka.runner.post;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        publish = true,
        features = {"src/test/resources/feature/posts/actualizar.feature"},
        glue = {"co.com.sofka.stepdefinition.post"}
)

public class ActualizarTest {
}
