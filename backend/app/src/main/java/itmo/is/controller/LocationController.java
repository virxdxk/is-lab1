package itmo.is.controller;

import itmo.is.service.LocationService;
import itmo.is.entities.Location;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import java.util.List;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;

@Path("/locations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LocationController {
    @Inject
    private LocationService locationService;

    @GET
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GET
    @Path("/{id}")
    public Response getLocationById(@PathParam("id") Long id) {
        try {
            Location location = locationService.getLocationById(id);
            return Response.ok(location).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response createLocation(Location location) {
        try {
            locationService.createLocation(location);
            return Response.ok(location).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response updateLocation(@PathParam("id") Long id, Location location) {
        try {
            locationService.updateLocation(location);
            return Response.ok(location).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteLocation(@PathParam("id") Long id) {
        try {
            locationService.deleteLocation(id);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
