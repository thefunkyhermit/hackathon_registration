import com.aidangordon.hackathon.registration.HackathonRegistrationFinalizationResource;
import com.aidangordon.hackathon.registration.configuration.HackathonRegistrationConfiguration;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;

/**
 * Created by IntelliJ IDEA.
 * User: aidangordon
 * Date: 12-09-29
 * Time: 11:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class HackathonRegistrationService extends Service<HackathonRegistrationConfiguration>{

    protected HackathonRegistrationService() {
        super("HackathonRegistrationService");
    }

    public static void main(String[] args) throws Exception {
        new HackathonRegistrationService().run(args);
    }

    @Override
    protected void initialize(HackathonRegistrationConfiguration configuration, Environment environment) throws Exception {
        environment.addResource(new HackathonRegistrationResource());
        environment.addResource(new HackathonRegistrationFinalizationResource());
    }

}
