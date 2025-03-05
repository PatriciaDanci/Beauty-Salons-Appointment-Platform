import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './components/Home';
import Haircut from './components/services/Haircut';
import Manicure from './components/services/Manicure';
import Massage from './components/services/Massage';
import Skincare from './components/services/Skincare';
import Lashes from './components/services/Lashes';
import SignUp from './components/SignUp';
import Login from './components/Login';
import Contact from './components/Contact';
import SearchResult from './components/SearchResult';
import ServiceResults from './components/ServiceResults';
import SalonPage from './components/SalonPage';
import SpecialistsByFacilityPage from './components/SpecialistsByFacilityPage';
import FacilitiesBySpecialistPage from './components/FacilitiesBySpecialistPage';
import CalendarComponent from './components/CalendarComponent';
import Appointments from './components/Appointments';
import SalonReviewsPage from './components/SalonReviewsPage';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/services/haircut" element={<Haircut />} />
        <Route path="/services/manicure" element={<Manicure />} />
        <Route path="/services/massage" element={<Massage />} />
        <Route path="/services/skincare" element={<Skincare />} />
        <Route path="/services/lashes" element={<Lashes />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/login" element={<Login />} />
        <Route path="/contact" element={<Contact />} />
        <Route path="/search-result" element={<SearchResult />} />
        <Route path="/service-results" element={<ServiceResults />} />
        <Route path="/salons/:id" element={<SalonPage />} />
        <Route
          path="/salons/:salonId/facilities/:facilityId/specialists"
          element={<SpecialistsByFacilityPage />}
        />
        <Route
          path="/salons/:salonId/specialists/:specialistId/facilities"
          element={<FacilitiesBySpecialistPage />}
        />
        <Route
          path="/salons/:salonId/facilities/:facilityId/specialists/:specialistName/calendar"
          element={<CalendarComponent />}
        />
        <Route path="/appointments" element={<Appointments />} />
        <Route path="/salons/:salonId/reviews" element={<SalonReviewsPage />} />
      </Routes>
    </Router>
  );
}

export default App;
