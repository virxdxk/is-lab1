import axios from "axios";
import type { RouteDto } from "./dto";

const api = axios.create({
    baseURL: "http://localhost:8080/api",
});

export const routeApi = {
    
    async geAllRoutes(): Promise<RouteDto[]> {
        try {
            const response = await api.get("/routes");
            return response.data;
        } catch (error) {
            throw new Error("Failed to get routes");
        }
    },
    async getRouteById(id: number): Promise<RouteDto> {
        try {
            const response = await api.get(`/routes/${id}`);
            return response.data;
        } catch (error) {
            throw new Error("Failed to get route by id");
        }
    },
    async createRoute(route: RouteDto): Promise<RouteDto> {
        try {
            const response = await api.post("/routes", route);
            return response.data;
        } catch (error) {
            throw new Error("Failed to create route");
        }
    },
    async updateRoute(route: RouteDto): Promise<RouteDto> {
        try {
            const response = await api.put(`/routes/${route.id}`, route);
            return response.data;
        } catch (error) {
            throw new Error("Failed to update route");
        }
    },
    async deleteRoute(id: number): Promise<void> {
        try {
            const response = await api.delete(`/routes/${id}`);
            return response.data;
        } catch (error) {
            throw new Error("Failed to delete route");
        }
    },

};