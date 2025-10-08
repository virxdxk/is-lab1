export interface Route {
    id: number;
    name: string;
    coordinates: Coordinates;
    creationDate: Date;
    from: Location;
    to: Location;
    distance: number;
    rating: number;
}

export interface Coordinates {
    x: number;
    y: number;
}

export interface Location {
    x: number;
    y: number;
    name: string;
}