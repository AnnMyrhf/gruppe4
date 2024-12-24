import React, { useEffect, useState } from 'react';

const Toaster = ({ text, visible, status }) => {
    const [show, setShow] = useState(visible);

    useEffect(() => {
        if (visible) {
            setShow(true);
        } else {
            setShow(false);
        }
    }, [visible]);

    if (!show && !visible) return null;

    // Funktion, um die Farben und Icons basierend auf dem Status zu bestimmen
    const getStatusStyles = (status) => {
        switch (status) {
            case 'success':
                return {
                    background: '#DEF2D6',
                    border: '1px solid #B9C7B6',
                    icon: '✔️',
                    color: "#516549"
                };
            case 'error':
                return {
                    background: '#EBC8C4',
                    border: '1px solid #AB938F',
                    icon: '❌', // Xmark Icon
                    color: "#A92F2C"
                };
            case 'warning':
                return {
                    background: '#FFC107',
                    border: '2px solid #FFA000',
                    icon: '⚠️' // Warning Icon
                };
            case 'info':
                return {
                    background: '#2196F3',
                    border: '2px solid #1976D2',
                    icon: 'ℹ️' // Info Icon
                };
            default:
                return {
                    background: '#333',
                    border: '2px solid #000',
                    icon: '' // Kein Icon
                };
        }
    };

    const statusStyles = getStatusStyles(status);

    return (
        <div style={{
            ...styles.toastContainer,
            background: statusStyles.background,
            border: statusStyles.border,
            transform: visible ? 'translateX(0)' : 'translateX(100%)',
            color: statusStyles.color
        }}>
            <div style={styles.icon}>{statusStyles.icon}</div>
            <span style={styles.text}>{text}</span>
        </div>
    );
};

const styles = {
    toastContainer: {
        position: 'absolute',
        top: '16px',
        right: '16px',
        color: '#fff',
        padding: '10px 15px',
        borderRadius: '4px',
        boxShadow: '0 2px 10px rgba(0,0,0,0.3)',
        zIndex: 9999,
        display: 'flex',
        alignItems: 'center',
        gap: '10px' // Abstand zwischen Icon und Text
    },
    icon: {
        fontSize: '20px', // Größe des Icons
    },
    text: {
        flex: 1 // Text nimmt den verbleibenden Platz ein
    }
};

export default Toaster;
