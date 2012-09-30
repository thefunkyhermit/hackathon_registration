package com.aidangordon.hackathon.registration

import javax.ws.rs.Path
import javax.ws.rs.Consumes
import javax.ws.rs.core.MediaType
import javax.ws.rs.Produces
import javax.ws.rs.GET
import javax.ws.rs.core.Response
import javax.ws.rs.QueryParam
import org.apache.commons.codec.binary.Base64

@Path('/hackathon/registration/finalize')
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class HackathonRegistrationFinalizationResource {
    @GET()
    public Response finalizeRegistration(@QueryParam('token')String token){
        String message
        if(!token){
           message = 'You need a valid token in order to finalize your registration for Hacktoria.  Please go to /hackathon/registration to get a fresh one if you need a new one'
        }else{
            message = 'Congratulations!  You are registered and we\'ll see you at Hacktoria!'
        }
        Response.ok(['parameters': token, 'message': Base64.encodeBase64String(message.getBytes())]).build()
    }
}

