import { describe, it, expect, vi } from 'vitest'
import { locationApi } from './api'
import type { Location } from './entity'

describe('locationApi', () => {
  describe('getAllLocations', () => {
    it('should return all locations', async () => {
      const locations = await locationApi.getAllLocations()
      
      expect(locations).toHaveLength(3)
      expect(locations[0]).toEqual({
        id: 1,
        x: 1,
        y: 2,
        name: 'Location 1'
      })
      expect(locations[1]).toEqual({
        id: 2,
        x: 3,
        y: 4,
        name: 'Location 2'
      })
      expect(locations[2]).toEqual({
        id: 3,
        x: 5,
        y: 6,
        name: 'Location 3'
      })
    })
  })

  describe('getLocationById', () => {
    it('should return location by id', async () => {
      const location = await locationApi.getLocationById(1)
      
      expect(location).toEqual({
        id: 1,
        x: 1,
        y: 2,
        name: 'Location 1'
      })
    })

    it('should throw error when location not found', async () => {
      await expect(locationApi.getLocationById(999)).rejects.toThrow('Failed to get location by id')
    })
  })

  describe('createLocation', () => {
    it('should create new location', async () => {
      const newLocation: Location = {
        id: 4,
        x: 7,
        y: 8,
        name: 'New Location'
      }

      const createdLocation = await locationApi.createLocation(newLocation)
      
      expect(createdLocation).toEqual(newLocation)
    })
  })

  describe('updateLocation', () => {
    it('should update existing location', async () => {
      const updatedLocation: Location = {
        id: 1,
        x: 1,
        y: 2,
        name: 'Updated Location'
      }

      const result = await locationApi.updateLocation(updatedLocation)
      
      expect(result).toEqual(updatedLocation)
    })
  })

  describe('deleteLocation', () => {
    it('should delete location', async () => {
      await expect(locationApi.deleteLocation(1)).resolves.not.toThrow()
    })
  })
})
