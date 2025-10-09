import { beforeAll, afterEach, afterAll } from 'vitest'
import { server } from './mocks/server'

// Start server before all tests
beforeAll(() => server.listen({ onUnhandledRequest: 'error' }))

// Reset handlers after each test `important for test isolation`
afterEach(() => server.resetHandlers())

// Clean up after the tests are finished.
afterAll(() => server.close())
