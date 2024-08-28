import React from 'react'
import { Route, Routes } from 'react-router-dom'
import Jira from '../pages/Jira/Jira'

const AppRoutes = () => {
  return (
    <Routes>
      <Route path='/*' element={<Jira />} />
    </Routes>
  )
}

export default AppRoutes
