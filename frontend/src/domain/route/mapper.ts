import type { Route } from "./entity";
import type { RouteDto } from "./dto";

export function routeToDto(route: Route): RouteDto {
    return {
        id: route.id,
        name: route.name,
        coordinates: route.coordinates,
        creationDate: route.creationDate.toISOString(),
        from: route.from,
        to: route.to,
        distance: route.distance,
        rating: route.rating,
    }
}