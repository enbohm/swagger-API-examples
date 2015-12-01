package se.enbohmconsulting.swagger.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Represents a dummy service (singleton) which contains three methods, findAll,
 * find(user) and add(user). Swagger (<a href="swagger.io">Swagger</a>) is used
 * as framework for documenting this API which is based on Jave EE 7 (Jax-RS +
 * EJB).
 * 
 * <p>
 * This API is found on path {@code /contextroot/api}. If you are using
 * Glassfish as container, the context root will be / otherwise refer to how to
 * specify context path in your choice of container.
 * 
 * @author Andreas Enbohm, Enbohm Consulting AB
 *
 */
@Singleton
@Path("/api")
@Api(value = "api")
public class RestService {

	private List<String> users = new ArrayList<>();

	@GET
	@Path("/findall")
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Returns the number of added users", notes = "This lists the number of users", response = Response.class)
	public Response findAll() {
		return Response.status(200).entity("Total users = " + users.size()).build();
	}

	@POST
	@Path("/add/{user}")
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Adds (saves) a user to local storage", notes = "This adds a user into memory storage", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "User added") })
	public Response add(@ApiParam(value = "user to add", required = true) @PathParam("user") String user) {
		this.users.add(user);
		return Response.status(201).entity("User " + user + " was added!").build();
	}

	@GET
	@Path("/find/{user}")
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Finds a user by its name", notes = "This method find a user in the menory storage", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User found"),
			@ApiResponse(code = 404, message = "User not found") })
	public Response find(@ApiParam(value = "user to find", required = true) @PathParam("user") String user) {
		Optional<String> foundUser = this.users.stream().filter(u -> u.equals(user)).findAny();
		return foundUser.isPresent() ? Response.status(200).entity("User " + foundUser.get() + " was found!").build()
				: Response.status(404).entity("User " + user + " not found").build();
	}
}
