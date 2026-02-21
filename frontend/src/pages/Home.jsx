import React from 'react';
import { motion } from 'framer-motion';
import { downloadResume } from '../services/api';
import { personalDetails } from '../data/portfolioData';

export default function Home() {
  return (
    <section
      id="home"
      className="min-h-screen flex items-center justify-center pt-20 bg-white dark:bg-black"
    >
      <div className="text-center px-4">
        <motion.h1
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="text-5xl md:text-7xl font-extrabold text-black dark:text-white mb-4"
        >
          {personalDetails.name.split(' ')[0]} <span className="text-primary">{personalDetails.name.split(' ')[1]}</span>
        </motion.h1>

        <motion.p
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.2 }}
          className="text-xl md:text-2xl text-gray-600 dark:text-gray-400 mb-8 max-w-3xl mx-auto"
        >
          {personalDetails.summary}
        </motion.p>

        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.4 }}
          className="flex flex-col sm:flex-row gap-4 justify-center"
        >
          <a
            href="#projects"
            className="bg-primary text-white px-8 py-3 rounded-full font-bold hover:bg-red-700 transition-all"
          >
            View Projects
          </a>

          <button
            onClick={() => window.open(downloadResume(), '_blank')}
            className="border-2 border-primary text-primary dark:text-white px-8 py-3 rounded-full font-bold hover:bg-primary hover:text-white transition-all"
          >
            Download Resume
          </button>
        </motion.div>
      </div>
    </section>
  );
}
