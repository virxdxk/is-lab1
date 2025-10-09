import type { Location } from "../location/entity";

export interface Route {
    id: number;
    name: string;
    coordinates: {x: number, y: number};
    creationDate: Date;
    from: Location | null;
    to: Location | null;
    distance: number | null;
    rating: number;
}