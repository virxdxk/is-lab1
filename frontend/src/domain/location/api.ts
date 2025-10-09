import axios from "axios";
import type { Location } from "./entity";

const api = axios.create({
    baseURL: "http://localhost:8080/api",
});

export const locationApi = {
    async getAllLocations(): Promise<Location[]> {
        try {
            const response = await api.get("/locations");
            return response.data;
        } catch (error) {
            throw new Error("Failed to get locations");
        }
    },
    async getLocationById(id: number): Promise<Location> {
        try {
            const response = await api.get(`/locations/${id}`);
            return response.data;
        } catch (error) {
            throw new Error("Failed to get location by id");
        }
    },
    async createLocation(location: Location): Promise<Location> {
        try {
            const response = await api.post("/locations", location);
            return response.data;
        } catch (error) {
            throw new Error("Failed to create location");
        }
    },
    async updateLocation(location: Location): Promise<Location> {
        try {
            const response = await api.put(`/locations/${location.id}`, location);
            return response.data;
        } catch (error) {
            throw new Error("Failed to update location");
        }
    },
    async deleteLocation(id: number): Promise<void> {
        try {
            const response = await api.delete(`/locations/${id}`);
            return response.data;
        } catch (error) {
            throw new Error("Failed to delete location");
        }
    },
};