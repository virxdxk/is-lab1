import { HomePage, RoutePage } from '@/pages'
import './App.css'
import { Route, Routes } from 'react-router-dom'

function App() {

  return (
    <div className="background">
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/add-route" element={<RoutePage />} />
      </Routes>
    </div>
  )
}

export default App
