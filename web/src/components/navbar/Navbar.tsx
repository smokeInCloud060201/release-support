import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'

const Navbar: React.FC = () => {
  const [activeTab, setActiveTab] = useState(0)
  const navigate = useNavigate()

  const navbarDef = [
    {
      label: 'Release Version',
      onClick: () => navigate('/release')
    },
    {
      label: 'Release Version',
      onClick: () => navigate('/release')
    }
  ]

  const RenderNavbar: React.FC = () => {
    return (
      <ul className='flex justify-start flex-col h-full items-start gap-2'>
        {navbarDef.map((item, index) => (
          <li key={index} className={`w-full rounded p-4 ${activeTab === index ? 'active-tab' : ''}`}>
            <button
              onClick={() => {
                setActiveTab(index)
                item.onClick()
              }}
            >
              <span>{item.label}</span>
            </button>
          </li>
        ))}
      </ul>
    )
  }

  return <nav className='min-w-[150px]'>{<RenderNavbar />}</nav>
}

export default Navbar
