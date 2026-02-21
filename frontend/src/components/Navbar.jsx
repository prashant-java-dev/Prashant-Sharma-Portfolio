import React, { useState, useEffect } from 'react';
import ThemeToggle from './ThemeToggle';
import { Menu, X } from 'lucide-react';
import { navLinks, personalDetails } from '../data/portfolioData';
import Logo from './Logo';

const Navbar = ({ darkMode, setDarkMode }) => {
  const [isOpen, setIsOpen] = useState(false);
  const [activeLink, setActiveLink] = useState(window.location.hash);

  // Sync active state with URL hash (handle back/forward navigation and root URL)
  useEffect(() => {
    const handleHashChange = () => {
      setActiveLink(window.location.hash);
    };

    // Listen for hash changes and browser navigation (back/forward)
    window.addEventListener('hashchange', handleHashChange);
    window.addEventListener('popstate', handleHashChange);

    return () => {
      window.removeEventListener('hashchange', handleHashChange);
      window.removeEventListener('popstate', handleHashChange);
    };
  }, []);

  return (
    <nav className="fixed top-0 w-full z-50 bg-white/80 dark:bg-black/80 backdrop-blur-md border-b border-gray-200 dark:border-gray-800">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-20 items-center">
          {/* Logo */}
          <div className="flex-shrink-0">
            <Logo className="h-16 w-auto" />
          </div>


          {/* Desktop Menu */}
          <div className="hidden md:flex items-center space-x-8">
            {navLinks.map((link) => {
              const isActive = activeLink === link.href;
              return (
                <a
                  key={link.name}
                  href={link.href}
                  onClick={() => setActiveLink(link.href)}
                  style={{ borderStyle: 'groove' }}
                  className={`px-5 py-2.5 rounded-[30px] text-base transition-all border-2 ${isActive
                    ? 'bg-white text-black border-red-500 font-bold'
                    : 'border-transparent text-black dark:text-white font-medium hover:bg-white hover:text-black dark:hover:text-black hover:border-red-500 hover:font-bold'
                    }`}
                >
                  {link.name}
                </a>
              );
            })}
            <ThemeToggle darkMode={darkMode} setDarkMode={setDarkMode} />
          </div>

          {/* Mobile Hamburger */}
          <div className="md:hidden flex items-center gap-4">
            <ThemeToggle darkMode={darkMode} setDarkMode={setDarkMode} />
            <button
              onClick={() => setIsOpen(!isOpen)}
              className="text-gray-700 dark:text-gray-300 focus:outline-none"
            >
              {isOpen ? <X size={24} /> : <Menu size={24} />}
            </button>
          </div>
        </div>
      </div>

      {/* Mobile Menu Dropdown */}
      {isOpen && (
        <div className="md:hidden bg-white dark:bg-secondary-light border-t border-gray-200 dark:border-gray-800">
          <div className="px-2 pt-2 pb-4 space-y-1 sm:px-3">
            {navLinks.map((link) => {
              const isActive = activeLink === link.href;
              return (
                <a
                  key={link.name}
                  href={link.href}
                  onClick={() => {
                    setActiveLink(link.href);
                    setIsOpen(false);
                  }}
                  style={{ borderStyle: 'groove' }}
                  className={`block px-5 py-3 rounded-[30px] text-lg transition-all border-2 ${isActive
                    ? 'bg-white text-black border-red-500 font-bold'
                    : 'border-transparent text-black dark:text-white font-medium hover:bg-white hover:text-black dark:hover:text-black hover:border-red-500 hover:font-bold'
                    }`}
                >
                  {link.name}
                </a>
              );
            })}
          </div>
        </div>
      )}
    </nav>
  );
};

export default Navbar;
