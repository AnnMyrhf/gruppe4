// Toaster.jsx
import React, { useEffect, useState } from 'react';

const Toaster = ({ text, visible }) => {
    const [show, setShow] = useState(visible);

    useEffect(() => {
        if (visible) {
            setShow(true);
        } else {
            setShow(false);
        }
    }, [visible]);

    if (!show && !visible) return null;

    return (
        <div style={{
            ...styles.toastContainer,
            // Wenn 'visible' true ist, setzen wir transform auf 'translateX(0)',
            // ansonsten bleibt er außerhalb des Bildes.
            transform: visible ? 'translateX(0)' : 'translateX(100%)'
        }}>
            {text}
        </div>
    );
};

const styles = {
    toastContainer: {
        position: 'fixed',
        top: '20px',
        right: '20px',
        background: '#333',
        color: '#fff',
        padding: '10px 15px',
        borderRadius: '4px',
        boxShadow: '0 2px 10px rgba(0,0,0,0.3)',
        zIndex: 9999,
        // Ausgangszustand: vom rechten Rand (außerhalb des Bildschirms)
        transform: 'translateX(100%)',
        transition: 'transform 0.3s ease-in-out'
    }
};

export default Toaster;
