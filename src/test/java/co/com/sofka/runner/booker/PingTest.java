package co.com.sofka.runner.booker;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        publish = true,
        features = {"src/test/resources/feature/booker/ping.feature"},
        glue = {"co.com.sofka.stepdefinition.booker"}
)
public class PingTest {
}
