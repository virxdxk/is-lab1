import axios from "axios";
import type { Location } from "./entity";

const api = axios.create({
    baseURL: "http://localhost:8080/api",
});

export const locationApi = {
    async getAllLocations(): Promise<Location[]> {
        const response = await api.get("/locations");
        if (response.status !== 200) {
            throw new Error("Failed to get locations");
        }
        return response.data;
    },
    async getLocationById(id: number): Promise<Location> {
        const response = await api.get(`/locations/${id}`);
        if (response.status !== 200) {
            throw new Error("Failed to get location by id");
        }
        return response.data;
    },
    async createLocation(location: Location): Promise<Location> {
        const response = await api.post("/locations", location);
        if (response.status !== 200) {
            throw new Error("Failed to create location");
        }
        return response.data;
    },
    async updateLocation(location: Location): Promise<Location> {
        const response = await api.put(`/locations/${location.id}`, location);
        if (response.status !== 200) {
            throw new Error("Failed to update location");
        }
        return response.data;
    },
    async deleteLocation(id: number): Promise<void> {
        const response = await api.delete(`/locations/${id}`);
        if (response.status !== 200) {
            throw new Error("Failed to delete location");
        }
        return response.data;
    },
};