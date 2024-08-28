import React from 'react'
import ReactDOM from 'react-dom/client'
import './styles/base.scss'
import { BrowserRouter } from 'react-router-dom'
import AppRoutes from './routers/AppRoutes'

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <BrowserRouter>
      <AppRoutes />
    </BrowserRouter>
  </React.StrictMode>
)
