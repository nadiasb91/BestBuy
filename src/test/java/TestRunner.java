import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.TestNGCucumberRunner;

@CucumberOptions(
        features = "src/test/resources",
        glue = "Test"
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
