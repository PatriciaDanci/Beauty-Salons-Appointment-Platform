import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Services.css';
import haircut from '../images/haircut1.jpg';
import manicure from '../images/nails.jpg';
import massage from '../images/massage.webp';
import skincare from '../images/skincare.jpg';
import lashes from '../images/lashes.jpg';

const Services = () => {
  const navigate = useNavigate();

  const services = [
    { image: haircut, name: 'Haircut', keyword: 'Hair' },
    { image: manicure, name: 'Manicure', keyword: 'Manicure' },
    { image: massage, name: 'Massage', keyword: 'Massage' },
    { image: skincare, name: 'Skincare', keyword: 'Facial' }, // Use "Facial" as per the database
    { image: lashes, name: 'Lashes', keyword: 'Lash' },
  ];

  const handleServiceClick = (keyword) => {
    navigate('/service-results', { state: { keyword } });
  };

  return (
    <div className="services">
      <h2>Popular Services</h2>
      <div className="services-container">
        {services.map((service, index) => (
          <div
            className="card"
            key={index}
            onClick={() => handleServiceClick(service.keyword)}
          >
            <img src={service.image} alt={service.name} />
            <p>{service.name}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Services;
