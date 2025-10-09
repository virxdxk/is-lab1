import { describe, it, expect } from 'vitest'
import { http, HttpResponse } from 'msw'
import { server } from './mocks/server'
import { locationApi } from '../domain/location/api'
import { routeApi } from '../domain/route/api'

describe('API Error Handling', () => {
  describe('locationApi error handling', () => {
    it('should handle 500 error for getAllLocations', async () => {
      server.use(
        http.get('http://localhost:8080/api/locations', () => {
          return HttpResponse.json({ error: 'Internal Server Error' }, { status: 500 })
        })
      )

      await expect(locationApi.getAllLocations()).rejects.toThrow('Failed to get locations')
    })

    it('should handle 400 error for createLocation', async () => {
      server.use(
        http.post('http://localhost:8080/api/locations', () => {
          return HttpResponse.json({ error: 'Bad Request' }, { status: 400 })
        })
      )

      const location = { id: 1, x: 1, y: 2, name: 'Test Location' }
      await expect(locationApi.createLocation(location)).rejects.toThrow('Failed to create location')
    })

    it('should handle 404 error for getLocationById', async () => {
      server.use(
        http.get('http://localhost:8080/api/locations/:id', () => {
          return HttpResponse.json({ error: 'Not Found' }, { status: 404 })
        })
      )

      await expect(locationApi.getLocationById(999)).rejects.toThrow('Failed to get location by id')
    })

    it('should handle 500 error for updateLocation', async () => {
      server.use(
        http.put('http://localhost:8080/api/locations/:id', () => {
          return HttpResponse.json({ error: 'Internal Server Error' }, { status: 500 })
        })
      )

      const location = { id: 1, x: 1, y: 2, name: 'Updated Location' }
      await expect(locationApi.updateLocation(location)).rejects.toThrow('Failed to update location')
    })

    it('should handle 500 error for deleteLocation', async () => {
      server.use(
        http.delete('http://localhost:8080/api/locations/:id', () => {
          return HttpResponse.json({ error: 'Internal Server Error' }, { status: 500 })
        })
      )

      await expect(locationApi.deleteLocation(1)).rejects.toThrow('Failed to delete location')
    })
  })

  describe('routeApi error handling', () => {
    it('should handle 500 error for geAllRoutes', async () => {
      server.use(
        http.get('http://localhost:8080/api/routes', () => {
          return HttpResponse.json({ error: 'Internal Server Error' }, { status: 500 })
        })
      )

      await expect(routeApi.geAllRoutes()).rejects.toThrow('Failed to get routes')
    })

    it('should handle 400 error for createRoute', async () => {
      server.use(
        http.post('http://localhost:8080/api/routes', () => {
          return HttpResponse.json({ error: 'Bad Request' }, { status: 400 })
        })
      )

      const route = {
        id: 1,
        name: 'Test Route',
        coordinates: { x: 1, y: 2 },
        creationDate: '2024-01-01T00:00:00Z',
        from: null,
        to: null,
        distance: null,
        rating: 0,
      }
      await expect(routeApi.createRoute(route)).rejects.toThrow('Failed to create route')
    })

    it('should handle 404 error for getRouteById', async () => {
      server.use(
        http.get('http://localhost:8080/api/routes/:id', () => {
          return HttpResponse.json({ error: 'Not Found' }, { status: 404 })
        })
      )

      await expect(routeApi.getRouteById(999)).rejects.toThrow('Failed to get route by id')
    })

    it('should handle 500 error for updateRoute', async () => {
      server.use(
        http.put('http://localhost:8080/api/routes/:id', () => {
          return HttpResponse.json({ error: 'Internal Server Error' }, { status: 500 })
        })
      )

      const route = {
        id: 1,
        name: 'Updated Route',
        coordinates: { x: 1, y: 2 },
        creationDate: '2024-01-01T00:00:00Z',
        from: null,
        to: null,
        distance: null,
        rating: 0,
      }
      await expect(routeApi.updateRoute(route)).rejects.toThrow('Failed to update route')
    })

    it('should handle 500 error for deleteRoute', async () => {
      server.use(
        http.delete('http://localhost:8080/api/routes/:id', () => {
          return HttpResponse.json({ error: 'Internal Server Error' }, { status: 500 })
        })
      )

      await expect(routeApi.deleteRoute(1)).rejects.toThrow('Failed to delete route')
    })
  })
})
