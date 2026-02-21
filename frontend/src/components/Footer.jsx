import { Github, Linkedin, Mail } from 'lucide-react';
import { personalDetails, socialLinks } from '../data/portfolioData';
import Logo from './Logo';

export default function Footer() {
  return (
    <footer className="bg-white dark:bg-black py-12 border-t border-gray-200 dark:border-gray-800">
      <div className="container mx-auto px-6">
        <div className="flex flex-col md:flex-row justify-between items-center">
          <div className="mb-6 md:mb-0">
            <a href="#home" className="text-2xl font-bold text-primary">
              <Logo className="h-16 w-auto" />
            </a>

            <p className="text-gray-500 mt-2 text-sm">
              © 2025 {personalDetails.name}. All rights reserved.
            </p>
          </div>
          <div className="flex space-x-6">
            <a href={socialLinks.github} target="_blank" rel="noopener noreferrer" className="text-gray-500 hover:text-primary transition-colors">
              <Github size={24} />
            </a>
            <a href={socialLinks.linkedin} target="_blank" rel="noopener noreferrer" className="text-gray-500 hover:text-primary transition-colors">
              <Linkedin size={24} />
            </a>
            <a href={`mailto:${socialLinks.email}`} className="text-gray-500 hover:text-primary transition-colors">
              <Mail size={24} />
            </a>
          </div>
        </div>
      </div>
    </footer >
  );
}
