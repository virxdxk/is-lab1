import { http, HttpResponse } from 'msw'

const mockLocations = [
  { id: 1, x: 1, y: 2, name: 'Location 1' },
  { id: 2, x: 3, y: 4, name: 'Location 2' },
  { id: 3, x: 5, y: 6, name: 'Location 3' },
]

const mockRoutes = [
  {
    id: 1,
    name: 'Route 1',
    coordinates: { x: 1, y: 2 },
    creationDate: '2024-01-01T00:00:00Z',
    from: { x: 1, y: 2, name: 'From Location' },
    to: { x: 3, y: 4, name: 'To Location' },
    distance: 10.5,
    rating: 4.5,
  },
  {
    id: 2,
    name: 'Route 2',
    coordinates: { x: 3, y: 4 },
    creationDate: '2024-01-02T00:00:00Z',
    from: { x: 3, y: 4, name: 'From Location 2' },
    to: { x: 5, y: 6, name: 'To Location 2' },
    distance: 15.2,
    rating: 3.8,
  },
]

export const handlers = [
  // Location API handlers
  http.get('http://localhost:8080/api/locations', () => {
    return HttpResponse.json(mockLocations)
  }),

  http.get('http://localhost:8080/api/locations/:id', ({ params }) => {
    const id = Number(params.id)
    const location = mockLocations.find(loc => loc.id === id)
    if (!location) {
      return HttpResponse.json({ error: 'Location not found' }, { status: 404 })
    }
    return HttpResponse.json(location)
  }),

  http.post('http://localhost:8080/api/locations', async ({ request }) => {
    const newLocation = await request.json()
    return HttpResponse.json(newLocation, { status: 201 })
  }),

  http.put('http://localhost:8080/api/locations/:id', async ({ request }) => {
    const updatedLocation = await request.json()
    return HttpResponse.json(updatedLocation)
  }),

  http.delete('http://localhost:8080/api/locations/:id', () => {
    return HttpResponse.json({}, { status: 204 })
  }),

  // Route API handlers
  http.get('http://localhost:8080/api/routes', () => {
    return HttpResponse.json(mockRoutes)
  }),

  http.get('http://localhost:8080/api/routes/:id', ({ params }) => {
    const id = Number(params.id)
    const route = mockRoutes.find(r => r.id === id)
    if (!route) {
      return HttpResponse.json({ error: 'Route not found' }, { status: 404 })
    }
    return HttpResponse.json(route)
  }),

  http.post('http://localhost:8080/api/routes', async ({ request }) => {
    const newRoute = await request.json()
    return HttpResponse.json(newRoute, { status: 201 })
  }),

  http.put('http://localhost:8080/api/routes/:id', async ({ request }) => {
    const updatedRoute = await request.json()
    return HttpResponse.json(updatedRoute)
  }),

  http.delete('http://localhost:8080/api/routes/:id', () => {
    return HttpResponse.json({}, { status: 204 })
  }),
]
