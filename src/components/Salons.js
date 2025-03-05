import React from 'react';
import { Link } from 'react-router-dom';
import './Salons.css';
import glowSalonImage from '../images/salon14.jpg';
import blissBeautyImage from '../images/salon4.jpg';
import luxeStudioImage from '../images/salon12.jpg';
import glamourRoomImage from '../images/salon2.jpg';

const Salons = () => {
  const salons = [
    { image: glowSalonImage, name: 'Glow Salon', link: '/salons/glow-salon' },
    { image: blissBeautyImage, name: 'Bliss Beauty', link: '/salons/bliss-beauty' },
    { image: luxeStudioImage, name: 'Luxe Studio', link: '/salons/luxe-studio' },
    { image: glamourRoomImage, name: 'Glamour Room', link: '/salons/glamour-room' },
  ];

  return (
    <div className="salons">
      <h2>Top-Rated Salons</h2>
      <div className="salons-container">
        {salons.map((salon, index) => (
          <div className="card" key={index}>
            <Link to={salon.link}>
              <img src={salon.image} alt={salon.name} className="salon-image" />
              <p>{salon.name}</p>
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Salons;
