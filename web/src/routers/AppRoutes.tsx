import React from 'react'
import { Route, Routes } from 'react-router-dom'
import Jira from '../pages/Jira/Jira'

const AppRoutes = () => {
  return (
    <div className='min-h-screen flex items-center flex-col'>
      <header className='min-h-[72px]'>This is header</header>
      <main className='flex-1 flex w-full'>
        <nav className='min-w-[150px]'>
          <ul>
            <li>
              <span>Release</span>
            </li>
            <li>
              <span>Release</span>
            </li>
            <li>
              <span>Release</span>
            </li>
          </ul>
        </nav>
        <section className='flex-1'>
          <Routes>
            <Route path='/*' element={<Jira />} />
          </Routes>
        </section>
      </main>
      <footer>This is footer</footer>
    </div>
  )
}

export default AppRoutes
