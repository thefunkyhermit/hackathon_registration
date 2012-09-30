package com.aidangordon.hackathon.registration.configuration;

import com.yammer.dropwizard.config.Configuration;

/**
 * Created by IntelliJ IDEA.
 * User: aidangordon
 * Date: 12-09-29
 * Time: 11:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class HackathonRegistrationConfiguration extends Configuration {
    String nothing;

    public String getNothing() {
        return nothing;
    }

    public void setNothing(String nothing) {
        this.nothing = nothing;
    }
}
