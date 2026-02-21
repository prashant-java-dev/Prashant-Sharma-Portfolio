import React, { useState } from 'react';
import { Send, Mail, MapPin, Phone } from 'lucide-react';
import { contactService } from '../services/api';

export default function Contact() {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    subject: '',
    message: ''
  });
  const [status, setStatus] = useState({ type: '', message: '' });
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setStatus({ type: '', message: '' });

    // Build payload — auto-fill subject if blank
    const payload = {
      name: formData.name.trim(),
      email: formData.email.trim(),
      subject: formData.subject.trim() || 'General Inquiry',
      message: formData.message.trim(),
    };

    // Frontend validation matching backend constraints
    if (payload.name.length < 2) {
      setStatus({ type: 'error', message: 'Name must be at least 2 characters.' });
      setLoading(false);
      return;
    }
    if (payload.subject.length < 3) {
      setStatus({ type: 'error', message: 'Subject must be at least 3 characters.' });
      setLoading(false);
      return;
    }
    if (payload.message.length < 10) {
      setStatus({ type: 'error', message: 'Message must be at least 10 characters.' });
      setLoading(false);
      return;
    }

    try {
      await contactService.sendMessage(payload);
      setStatus({ type: 'success', message: '✅ Message sent successfully!' });
      setFormData({ name: '', email: '', subject: '', message: '' });
    } catch (error) {
      const msg = error.response?.data?.message || error.response?.data?.errors?.join(', ') || 'Failed to send message. Please try again.';
      setStatus({ type: 'error', message: msg });
    } finally {
      setLoading(false);
    }
  };

  return (
    <section id="contact" className="py-24 bg-gray-50 dark:bg-secondary-light">
      <div className="container mx-auto px-6">
        <h2 className="text-3xl font-bold mb-12 text-center">
          Get In <span className="text-primary">Touch</span>
        </h2>

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 max-w-6xl mx-auto">
          {/* Contact Info */}
          <div>
            <h3 className="text-2xl font-bold mb-6">Contact Information</h3>
            <p className="text-gray-600 dark:text-gray-400 mb-8">
              I'm always open to discussing new projects, creative ideas or opportunities to be part of your visions.
            </p>

            <div className="space-y-6">
              <div className="flex items-center">
                <div className="w-12 h-12 bg-primary/10 rounded-lg flex items-center justify-center mr-4">
                  <Mail className="text-primary" size={24} />
                </div>
                <div>
                  <p className="text-sm text-gray-500">Email</p>
                  <p className="font-bold">prashantsharma3018@gmail.com</p>
                </div>
              </div>

              <div className="flex items-center">
                <div className="w-12 h-12 bg-primary/10 rounded-lg flex items-center justify-center mr-4">
                  <Phone className="text-primary" size={24} />
                </div>
                <div>
                  <p className="text-sm text-gray-500">Phone</p>
                  <p className="font-bold">7302083744</p>
                </div>
              </div>

              <div className="flex items-center">
                <div className="w-12 h-12 bg-primary/10 rounded-lg flex items-center justify-center mr-4">
                  <MapPin className="text-primary" size={24} />
                </div>
                <div>
                  <p className="text-sm text-gray-500">Location</p>
                  <p className="font-bold">Moradabad, India</p>
                </div>
              </div>
            </div>
          </div>

          {/* Contact Form */}
          <form
            onSubmit={handleSubmit}
            className="bg-white dark:bg-black p-8 rounded-2xl shadow-sm border border-gray-100 dark:border-gray-800"
          >
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
              <div>
                <label className="block text-sm font-medium mb-2">Name</label>
                <input
                  type="text"
                  name="name"
                  value={formData.name}
                  onChange={handleChange}
                  required
                  placeholder="John Doe"
                  className="w-full px-4 py-3 rounded-lg bg-gray-50 dark:bg-gray-900 border border-gray-200 dark:border-gray-800 focus:border-primary outline-none transition-colors"
                />
              </div>
              <div>
                <label className="block text-sm font-medium mb-2">Email</label>
                <input
                  type="email"
                  name="email"
                  value={formData.email}
                  onChange={handleChange}
                  required
                  placeholder="john@example.com"
                  className="w-full px-4 py-3 rounded-lg bg-gray-50 dark:bg-gray-900 border border-gray-200 dark:border-gray-800 focus:border-primary outline-none transition-colors"
                />
              </div>
            </div>

            <div className="mb-6">
              <label className="block text-sm font-medium mb-2">Subject</label>
              <input
                type="text"
                name="subject"
                value={formData.subject}
                onChange={handleChange}
                required
                placeholder="Project Inquiry"
                className="w-full px-4 py-3 rounded-lg bg-gray-50 dark:bg-gray-900 border border-gray-200 dark:border-gray-800 focus:border-primary outline-none transition-colors"
              />
            </div>

            <div className="mb-6">
              <label className="block text-sm font-medium mb-2">Message</label>
              <textarea
                name="message"
                value={formData.message}
                onChange={handleChange}
                required
                rows="4"
                placeholder="Your message here..."
                className="w-full px-4 py-3 rounded-lg bg-gray-50 dark:bg-gray-900 border border-gray-200 dark:border-gray-800 focus:border-primary outline-none transition-colors resize-none"
              />
            </div>

            {status.message && (
              <div
                className={`mb-6 p-4 rounded-lg ${status.type === 'success' ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'
                  }`}
              >
                {status.message}
              </div>
            )}

            <button
              type="submit"
              disabled={loading}
              className="w-full py-4 bg-primary text-white rounded-lg font-bold flex items-center justify-center hover:bg-primary-dark transition-all disabled:opacity-50"
            >
              {loading ? 'Sending...' : <>Send Message <Send className="ml-2" size={20} /></>}
            </button>
          </form>
        </div>
      </div>
    </section>
  );
}
