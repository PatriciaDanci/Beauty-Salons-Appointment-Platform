import React, { useState } from 'react';
import './Hero.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'; // Import useNavigate

const Hero = () => {
  const [search, setSearch] = useState('');
  const [city, setCity] = useState('');
  const navigate = useNavigate(); // Initialize navigate

  const handleSearch = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/salons/search', {
        params: { keyword: search }, // Ensure the keyword matches your backend implementation
      });
      // Redirect to the search results page and pass the data as state
      navigate('/search-result', { state: { results: response.data } });
    } catch (error) {
      console.error('Error fetching search results:', error);
    }
  };

  return (
    <div className="hero">
      <h2>Book Appointments with Your Favorite Salons</h2>
      <input
        type="text"
        placeholder="Search service or salon"
        value={search}
        onChange={(e) => setSearch(e.target.value)}
      />
      <input
        type="text"
        placeholder="Choose city"
        value={city}
        onChange={(e) => setCity(e.target.value)}
      />
      <button onClick={handleSearch}>Search</button>
    </div>
  );
};

export default Hero;
