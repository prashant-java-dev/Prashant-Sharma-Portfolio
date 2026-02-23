import axios from 'axios';

/**
 * Centralized API configuration for the frontend application.
 * 
 * DESIGN FEATURES:
 * 1. Environment-Based Configuration: Dynamically switches between Local and Production (Railway) endpoints.
 * 2. Robust File Serving: Implements a hybrid approach for resume handling—using static assets 
 *    for immediate availability and fallback.
 * 3. Modular Service Pattern: Exports clean, reusable service objects for different domain features.
 */

// Hardcoded fallback for production to ensure form works even if VITE_API_URL is missing
const API_URL = import.meta.env.VITE_API_URL || 'https://portfolio-system-production.up.railway.app/api';

// Handle routing base path for GitHub Pages deployments
const BASE_PATH = import.meta.env.BASE_URL || '/';
const RESUME_STATIC_PATH = `${BASE_PATH}Prashant_Sharma_Software_Developer_Resume.pdf`;

/**
 * Service for Resume related operations.
 */
export const resumeService = {
    /**
     * Opens the resume in a new browser tab.
     * Uses the static public path for zero-latency response.
     */
    viewResume: () => {
        window.open(RESUME_STATIC_PATH, '_blank');
    },

    /**
     * Creates a dummy download link to trigger file save.
     */
    downloadResume: () => {
        const link = document.createElement('a');
        link.href = RESUME_STATIC_PATH;
        link.setAttribute('download', 'Prashant_Sharma_Software_Developer_Resume.pdf');
        document.body.appendChild(link);
        link.click();
        link.remove();
    }
};

/**
 * Service for Contact inquiry operations.
 */
export const contactService = {
    /**
     * Sends contact form data to the Spring Boot backend.
     * @param {Object} data - {name, email, subject, message}
     */
    sendMessage: async (data) => {
        return await axios.post(`${API_URL}/contact`, data);
    }
};

// Legacy exports for backward compatibility
export const downloadResume = () => RESUME_STATIC_PATH;
export const sendContact = (data) => contactService.sendMessage(data);
