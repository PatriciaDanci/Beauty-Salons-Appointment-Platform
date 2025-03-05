import React from 'react';
import { Link } from 'react-router-dom';
import './Header.css';

const Header = () => {
  return (
    <header className="header">
      <h1 className="logo">Glamly</h1>
      <nav className="nav">
        <Link to="/">Services</Link>
        <Link to="/appointments">Appointments</Link>
        <Link to="/contact">Contact</Link>
        <Link to="/login">Login</Link>
      </nav>
    </header>
  );
};

export default Header;
