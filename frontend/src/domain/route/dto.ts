import type { Location } from "../location/entity";

export interface RouteDto {
    id: number;
    name: string;
    coordinates: {x: number, y: number};
    creationDate: string;
    from: Location | null;
    to: Location | null;
    distance: number | null;
    rating: number;
}