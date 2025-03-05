import React from 'react';
import { Link } from 'react-router-dom';
import Hero from './Hero';
import Services from './Services';
import Salons from './Salons';
import './Home.css';

const Home = () => {
  return (
    <div className="home">
      <header className="home-header">
        <h1>Glamly</h1>
        <nav>
          <Link to="/">Services</Link>
          <Link to="/appointments">Appointments</Link>
          <Link to="/contact">Contact</Link>
          <Link to="/login">Login</Link>
        </nav>
      </header>
      <Hero />
      <div className="steps">
        <div>
          <h3>1. Choose Service</h3>
          <p>Select a service and salon to begin.</p>
        </div>
        <div>
          <h3>2. Pick a Time</h3>
          <p>Choose a time that works for you.</p>
        </div>
        <div>
          <h3>3. Confirm Booking</h3>
          <p>Confirm and you're all set!</p>
        </div>
      </div>
      <Services />
      <Salons />
      <footer>
        <p>© 2024 Glamly. All Rights Reserved.</p>
      </footer>
    </div>
  );
};

export default Home;
