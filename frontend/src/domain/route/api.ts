import axios from "axios";
import type { RouteDto } from "./dto";

const api = axios.create({
    baseURL: "http://localhost:8080/api",
});

export const routeApi = {
    
    async geAllRoutes(): Promise<RouteDto[]> {
        const response = await api.get("/routes");
        if (response.status !== 200) {
            throw new Error("Failed to get routes");
        }
        return response.data;
    },
    async getRouteById(id: number): Promise<RouteDto> {
        const response = await api.get(`/routes/${id}`);
        if (response.status !== 200) {
            throw new Error("Failed to get route by id");
        }
        return response.data;
    },
    async createRoute(route: RouteDto): Promise<RouteDto> {
        const response = await api.post("/routes", route);
        if (response.status !== 200) {
            throw new Error("Failed to create route");
        }
        return response.data;
    },
    async updateRoute(route: RouteDto): Promise<RouteDto> {
        const response = await api.put(`/routes/${route.id}`, route);
        if (response.status !== 200) {
            throw new Error("Failed to update route");
        }
        return response.data;
    },
    async deleteRoute(id: number): Promise<void> {
        const response = await api.delete(`/routes/${id}`);
        if (response.status !== 200) {
            throw new Error("Failed to delete route");
        }
        return response.data;
    },

};