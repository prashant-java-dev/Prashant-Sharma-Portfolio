import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

export const downloadResume = () => {
    return `${API_URL}/resume/download`;
};

export const resumeService = {
    viewResume: () => {
        window.open(`${API_URL}/resume/view`, '_blank');
    },
    downloadResume: () => {
        const link = document.createElement('a');
        link.href = downloadResume();
        link.setAttribute('download', 'resume.pdf');
        document.body.appendChild(link);
        link.click();
        link.remove();
    }
};

export const contactService = {
    sendMessage: async (data) => {
        return await axios.post(`${API_URL}/contact`, data);
    }
};

export const sendContact = async (data) => {
    return await axios.post(`${API_URL}/contact`, data);
};
