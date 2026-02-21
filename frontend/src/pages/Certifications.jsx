import React, { useEffect } from 'react';

const certifications = [
  {
    id: 1,
    title: 'Java Programming Certificate',
    issuer: 'Professional Certification',
    date: '2024',
    icon: 'fas fa-certificate',
    description: 'Comprehensive certification covering Core Java, Collections API, and Multithreading.'
  },
  {
    id: 2,
    title: 'Web Development Workshop',
    issuer: 'Technical Workshop',
    date: '2023',
    icon: 'fas fa-code',
    description: 'Hands-on training in modern frontend technologies and responsive design principles.'
  }
];

export default function Certifications() {
  useEffect(() => {
    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            entry.target.classList.add('is-visible');
          }
        });
      },
      { threshold: 0.1 }
    );

    const items = document.querySelectorAll('.fade-in-section');
    items.forEach((item) => observer.observe(item));

    return () => observer.disconnect();
  }, []);

  return (
    <section
      id="certifications"
      className="py-20 bg-white dark:bg-black transition-colors duration-300 border-t border-gray-200 dark:border-gray-800"
    >
      <div className="container mx-auto px-6">
        <div className="text-center mb-16 fade-in-section">
          <h2 className="text-3xl md:text-4xl font-bold text-neutral-900 dark:text-white mb-4">
            Certifications & Awards
          </h2>
          <div className="w-20 h-1 bg-primary mx-auto rounded-full"></div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-8 max-w-6xl mx-auto">
          {certifications.map((cert) => (
            <div
              key={cert.id}
              className="fade-in-section group relative bg-neutral-50 dark:bg-neutral-900/50 p-8 rounded-2xl border border-neutral-200 dark:border-neutral-800 hover:border-primary/50 transition-all duration-500 hover:-translate-y-2 hover:shadow-xl hover:shadow-primary/20"
            >
              <div className="mb-6 w-14 h-14 bg-white dark:bg-black rounded-xl flex items-center justify-center text-2xl text-primary shadow-sm group-hover:scale-110 group-hover:bg-primary group-hover:text-white transition-all duration-500">
                <i className={cert.icon}></i>
              </div>

              <span className="text-xs font-mono font-bold text-primary uppercase tracking-widest mb-2 block">
                {cert.date}
              </span>

              <h3 className="text-xl font-bold text-neutral-900 dark:text-white mb-2 group-hover:text-primary transition-colors">
                {cert.title}
              </h3>

              <p className="text-neutral-500 dark:text-neutral-400 text-sm font-medium mb-4">
                {cert.issuer}
              </p>

              <p className="text-neutral-600 dark:text-neutral-400 text-sm leading-relaxed">
                {cert.description}
              </p>

              <div className="absolute top-0 right-0 p-4 opacity-5 group-hover:opacity-10 transition-opacity">
                <i className={`${cert.icon} text-6xl`}></i>
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}
