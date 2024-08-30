import React from 'react'
import { Navbar } from './components'
import AppRoutes from './routers/AppRoutes'

const App: React.FC = () => {
  return (
    <div className='app-wrapper min-h-screen flex items-center flex-col'>
      <header className='min-h-[72px]'>This is header</header>
      <main className='flex-1 flex w-full'>
        <Navbar />
        <section className='flex-1 mx-4'>
          <AppRoutes />
        </section>
      </main>
      <footer>This is footer</footer>
    </div>
  )
}

export default App
