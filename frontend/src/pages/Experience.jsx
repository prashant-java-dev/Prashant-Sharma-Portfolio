import React from 'react';
import { motion } from 'framer-motion';

import { experiences } from '../data/portfolioData';

export default function Experience() {

  return (
    <section id="experience" className="py-24 bg-gray-50 dark:bg-secondary-light">
      <div className="container mx-auto px-6">
        <h2 className="text-3xl font-bold mb-12 text-center">
          Work <span className="text-primary">Experience</span>
        </h2>

        <div className="max-w-4xl mx-auto">
          {experiences.map((exp, index) => (
            <motion.div
              key={index}
              initial={{ opacity: 0, x: -20 }}
              whileInView={{ opacity: 1, x: 0 }}
              viewport={{ once: true }}
              className="relative pl-8 border-l-2 border-primary pb-12 last:pb-0"
            >
              <div className="absolute w-4 h-4 bg-primary rounded-full -left-[9px] top-1"></div>
              <div className="mb-1 flex flex-col md:flex-row md:justify-between md:items-center">
                <h3 className="text-xl font-bold">{exp.role}</h3>
                <span className="text-primary font-medium">{exp.period}</span>
              </div>
              <h4 className="text-lg text-gray-600 dark:text-gray-400 mb-4">{exp.company}</h4>
              <ul className="list-disc list-inside space-y-2 text-gray-600 dark:text-gray-400">
                {exp.description.map((item, i) => (
                  <li key={i}>{item}</li>
                ))}
              </ul>
            </motion.div>
          ))}
        </div>
      </div>
    </section>
  );
}
