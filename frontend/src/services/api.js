import axios from 'axios';

// Uses Railway backend in production, localhost in development
const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

// Base path for static assets (GitHub Pages)
const BASE_PATH = import.meta.env.BASE_URL || '/';
const RESUME_PDF = `${BASE_PATH}Prashant_Sharma_Software_Developer_Resume.pdf`;

// ─── Resume ────────────────────────────────────────────────────────────────

export const resumeService = {
    viewResume: () => {
        console.log("Viewing resume from:", RESUME_PDF);
        // Try backend first, fallback to static PDF
        window.open(RESUME_PDF, '_blank');
    },
    downloadResume: () => {
        const link = document.createElement('a');
        link.href = RESUME_PDF;
        link.setAttribute('download', 'Prashant_Sharma_Software_Developer_Resume.pdf');
        document.body.appendChild(link);
        link.click();
        link.remove();
    }
};

// Kept for backward compatibility with Home.jsx
export const downloadResume = () => RESUME_PDF;

// ─── Contact ───────────────────────────────────────────────────────────────

export const contactService = {
    sendMessage: async (data) => {
        return await axios.post(`${API_URL}/contact`, data);
    }
};

export const sendContact = async (data) => {
    return await axios.post(`${API_URL}/contact`, data);
};
