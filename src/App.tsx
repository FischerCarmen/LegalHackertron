import React from 'react';
import './App.css';

const App: React.FC = () => {
    return (
        <div>
            <nav className="navbar">
                <div className="navbar-brand">Meine App</div>
                <div className="navbar-nav">
                    <a className="nav-item" href="#">Home</a>
                    <a className="nav-item" href="#">Über uns</a>
                </div>
            </nav>

            <div className="content">
                <h3>Willkommen zu meinem Mockup!</h3>
                <p>Dies ist ein Mockup, das mit React, TypeScript und normalem CSS erstellt wurde.</p>
            </div>
        </div>
    );
}

export default App;
