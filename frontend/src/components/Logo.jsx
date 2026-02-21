import React from 'react';

const Logo = ({ className }) => {
    return (
        <svg
            width="600"
            height="180"
            viewBox="0 0 600 180"
            xmlns="http://www.w3.org/2000/svg"
            className={className}
        >
            <defs>
                <linearGradient id="metalGrad" x1="0%" y1="0%" x2="100%" y2="100%">
                    <stop offset="0%" stopColor="#FF4D4D" />
                    <stop offset="50%" stopColor="#B91C1C" />
                    <stop offset="100%" stopColor="#7F1D1D" />
                </linearGradient>

                <filter id="glow" x="-50%" y="-50%" width="200%" height="200%">
                    <feDropShadow dx="0" dy="0" stdDeviation="6" floodColor="#FF4D4D" floodOpacity="0.7" />
                </filter>
            </defs>

            {/* Background with Theme-Aware Fill (Light in light mode, Dark in dark mode) */}
            <rect
                width="600"
                height="180"
                className="fill-gray-100 dark:fill-black transition-colors duration-300"
                rx="90"
                ry="90"
            />

            {/* Circular Border Around Monogram - Theme-Aware Stroke */}
            <circle
                cx="95"
                cy="70"
                r="60"
                fill="none"
                className="stroke-gray-300 dark:stroke-gray-800 transition-colors duration-300"
                strokeWidth="6"
            />

            {/* Abstract 3D PS Monogram with Glow */}
            <g transform="translate(40,40)" filter="url(#glow)">
                <path d="M0,0 L0,80 L30,80 L30,45 L55,45 Q70,45 70,20 Q70,0 55,0 L0,0 Z" fill="url(#metalGrad)" />
                <path d="M80,0 Q110,0 110,25 Q110,50 80,50 Q55,50 55,75 Q55,100 80,100 Q110,100 110,75"
                    stroke="url(#metalGrad)" strokeWidth="6" fill="none" />
            </g>

            {/* Portfolio Name with Metallic Gradient */}
            <text x="160" y="90" fontFamily="Inter, sans-serif" fontSize="50" fill="url(#metalGrad)" fontWeight="bold">
                Prashant Sharma
            </text>

            {/* Role / Tagline with Glow */}
            <text x="160" y="135" fontFamily="Inter, sans-serif" fontSize="20" fill="#FF7F7F" filter="url(#glow)">
                Java Backend Developer
            </text>

            {/* Desktop Icon positioned after tagline */}
            <g transform="translate(390,113)">
                <rect x="0" y="0" width="25" height="20" fill="url(#metalGrad)" stroke="#ffffff" strokeWidth="1" rx="2" ry="2" />
                <rect x="10" y="20" width="5" height="8" fill="url(#metalGrad)" />
                <rect x="5" y="28" width="15" height="2" fill="url(#metalGrad)" />
            </g>

            {/* Cinematic Accent Line */}
            <line x1="163" y1="147" x2="540" y2="147" stroke="url(#metalGrad)" strokeWidth="3" strokeOpacity="0.5" filter="url(#glow)" />
        </svg>
    );
};

export default Logo;
