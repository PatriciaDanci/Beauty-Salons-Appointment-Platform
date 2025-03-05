import React from 'react';
import { useLocation, Link } from 'react-router-dom';
import './SearchResult.css'; // Updated styling

const SearchResults = () => {
  const location = useLocation();
  const results = Array.isArray(location.state?.results) ? location.state.results : [];

  return (
    <div className="search-results">
      <h1 className="search-header">Search Results</h1>
      {results.length > 0 ? (
        <div className="salon-grid">
          {results.map((salon) => (
            <div key={salon.id} className="salon-card">
              <img src={salon.pictureUrl} alt={salon.name} className="salon-image" />
              <div className="salon-info">
                <h2 className="salon-name">
                  <Link to={`/salons/${salon.name.toLowerCase().replace(/\s+/g, '-')}`}>
                    {salon.name}
                  </Link>
                </h2>
                <p className="salon-address">{salon.address}</p>
                <p className="salon-city">{salon.city}</p>
                <p className="salon-rating">
                  ⭐ Average Rating: {salon.averageRating && salon.averageRating !== '0.0' ? salon.averageRating : 'N/A'}
                </p>
                <Link
                  to={`/salons/${salon.name.toLowerCase().replace(/\s+/g, '-')}`}
                  className="details-link"
                >
                  View Details
                </Link>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <p className="no-results">No salons found. Try another search!</p>
      )}
    </div>
  );
};

export default SearchResults;
