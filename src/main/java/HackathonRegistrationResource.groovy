import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response
import javax.ws.rs.QueryParam
import com.aidangordon.hackathon.registration.bean.HackerInfo
import javax.ws.rs.POST
import javax.ws.rs.Consumes
import javax.ws.rs.core.MediaType
import javax.ws.rs.Produces
import org.apache.commons.codec.binary.Base64
import net.spy.memcached.AddrUtil
import net.spy.memcached.MemcachedClient
import com.mongodb.Mongo
import com.mongodb.DB
import com.mongodb.DBCollection
import com.mongodb.BasicDBObject

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/hackathon/registration")
public class HackathonRegistrationResource {
    @GET()
    public Response beginRegistration(@QueryParam('token') String token) {
        String message

        MemcachedClient c = new MemcachedClient(
                AddrUtil.getAddresses("localhost:11211"));

        if (!token) {
            token = UUID.randomUUID().toString().replace('-', '')
            c.set(token, 60, token)
            message = 'Hello, and welcome to the Hacktoria registration.  Please try again with this token included as a parameter.  (This token will expire in 10 minutes and you\'ll have to start this process again)'
        } else {
            String tokenFromMemcached = c.get(token)
            if (!tokenFromMemcached) {
                message = "Either your token never existed or has expired.  Please do a GET to this URL with no token specified to get a new one"
            } else {
                message = 'Excellent, now please post your firstName, lastName, email, and company as JSON to this URL'+tokenFromMemcached
            }
        }

        Map response = ['parameters': null, 'message': Base64.encodeBase64String(message.getBytes()), 'token': token]
        return Response.ok(response).build();
    }

    @POST()
    public Response acceptNames(@QueryParam('token') String token, HackerInfo hackerInfo) {
        String message
        if (!token) {
            message = 'Hmm...do you need to GET another token?'
        } else if (!hackerInfo.getFirstName() || !hackerInfo.getLastName() || !hackerInfo.getEmail() || !hackerInfo.getOrganization()) {
            message = 'Your firstName, lastName, email, and organization are all required in order for you to register for Hacktoria'
        } else {
            MemcachedClient c = new MemcachedClient(
                    AddrUtil.getAddresses("localhost:11211"));
            Mongo mongo = new Mongo()
            DB db = mongo.getDB('hackathonRegistration')
            DBCollection coll = db.getCollection("registrations")

            BasicDBObject registration = new BasicDBObject()
            registration.put('token', token)
            registration.put('firstName', hackerInfo.getFirstName())
            registration.put('lastName', hackerInfo.getLastName())
            registration.put('email', hackerInfo.getEmail())
            registration.put('organization', hackerInfo.getOrganization())

            coll.insert(registration)
            message = 'Nice work ' + hackerInfo.getFirstName() + ', now go to /hackathon/registration/finalize and add your company, twitter handle, and email and you\'ll bean registered for Hacktoria'
        }
        return Response.ok(['parameters': hackerInfo, 'message': Base64.encodeBase64String(message.getBytes())]).build()
    }

}
