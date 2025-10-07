package itmo.is.controller;

import itmo.is.entities.Route;
import itmo.is.service.RouteService;
import jakarta.ws.rs.core.Response;
import java.util.List;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.*;
import jakarta.inject.Inject;

@Path("/routes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RouteController {

    @Inject
    private RouteService routeService;

    @GET
    public List<Route> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @GET
    @Path("/{id}")
    public Response getRouteById(@PathParam("id") Long id) {
        try {
            Route route = routeService.getRouteById(id);
            return Response.ok(route).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response createRoute(Route route) {
        try {
            routeService.addRoute(route);
            return Response.ok(route).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateRoute(@PathParam("id") Long id, Route route) {
        try {
            routeService.updateRoute(route);
            return Response.ok(route).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteRoute(@PathParam("id") Long id) {
        try {
            routeService.deleteRoute(id);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
