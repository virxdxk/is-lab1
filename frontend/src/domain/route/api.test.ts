import { describe, it, expect } from 'vitest'
import { routeApi } from './api'
import type { RouteDto } from './dto'

describe('routeApi', () => {
  describe('geAllRoutes', () => {
    it('should return all routes', async () => {
      const routes = await routeApi.geAllRoutes()
      
      expect(routes).toHaveLength(2)
      expect(routes[0]).toEqual({
        id: 1,
        name: 'Route 1',
        coordinates: { x: 1, y: 2 },
        creationDate: '2024-01-01T00:00:00Z',
        from: { x: 1, y: 2, name: 'From Location' },
        to: { x: 3, y: 4, name: 'To Location' },
        distance: 10.5,
        rating: 4.5,
      })
      expect(routes[1]).toEqual({
        id: 2,
        name: 'Route 2',
        coordinates: { x: 3, y: 4 },
        creationDate: '2024-01-02T00:00:00Z',
        from: { x: 3, y: 4, name: 'From Location 2' },
        to: { x: 5, y: 6, name: 'To Location 2' },
        distance: 15.2,
        rating: 3.8,
      })
    })
  })

  describe('getRouteById', () => {
    it('should return route by id', async () => {
      const route = await routeApi.getRouteById(1)
      
      expect(route).toEqual({
        id: 1,
        name: 'Route 1',
        coordinates: { x: 1, y: 2 },
        creationDate: '2024-01-01T00:00:00Z',
        from: { x: 1, y: 2, name: 'From Location' },
        to: { x: 3, y: 4, name: 'To Location' },
        distance: 10.5,
        rating: 4.5,
      })
    })

    it('should throw error when route not found', async () => {
      await expect(routeApi.getRouteById(999)).rejects.toThrow('Failed to get route by id')
    })
  })

  describe('createRoute', () => {
    it('should create new route', async () => {
      const newRoute: RouteDto = {
        id: 3,
        name: 'New Route',
        coordinates: { x: 7, y: 8 },
        creationDate: '2024-01-03T00:00:00Z',
        from: { x: 7, y: 8, name: 'New From Location' },
        to: { x: 9, y: 10, name: 'New To Location' },
        distance: 20.0,
        rating: 5.0,
      }

      const createdRoute = await routeApi.createRoute(newRoute)
      
      expect(createdRoute).toEqual(newRoute)
    })
  })

  describe('updateRoute', () => {
    it('should update existing route', async () => {
      const updatedRoute: RouteDto = {
        id: 1,
        name: 'Updated Route',
        coordinates: { x: 1, y: 2 },
        creationDate: '2024-01-01T00:00:00Z',
        from: { x: 1, y: 2, name: 'Updated From Location' },
        to: { x: 3, y: 4, name: 'Updated To Location' },
        distance: 12.0,
        rating: 4.8,
      }

      const result = await routeApi.updateRoute(updatedRoute)
      
      expect(result).toEqual(updatedRoute)
    })
  })

  describe('deleteRoute', () => {
    it('should delete route', async () => {
      await expect(routeApi.deleteRoute(1)).resolves.not.toThrow()
    })
  })
})
