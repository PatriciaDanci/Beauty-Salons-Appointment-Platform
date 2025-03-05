import React from 'react';
import './Contact.css';

const Contact = () => {
  return (
    <div className="contact">
      <header className="contact-header">
        <h1>Contact Us</h1>
      </header>
      <div className="contact-content">
        <p>If you have any questions or need assistance, feel free to reach out to us:</p>
        <div className="contact-info">
          <p><strong>Email:</strong> support@glamly.com</p>
          <p><strong>Phone:</strong> +1 (555) 123-4567</p>
          <p><strong>Address:</strong> 123 Glamour Lane, Style City, SC 98765</p>
        </div>
        <div className="contact-form">
          <h2>Send Us a Message</h2>
          <form>
            <div className="form-group">
              <label htmlFor="name">Name</label>
              <input type="text" id="name" placeholder="Your name" required />
            </div>
            <div className="form-group">
              <label htmlFor="email">Email</label>
              <input type="email" id="email" placeholder="Your email" required />
            </div>
            <div className="form-group">
              <label htmlFor="message">Message</label>
              <textarea id="message" rows="5" placeholder="Your message" required></textarea>
            </div>
            <button type="submit" className="contact-button">Send</button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Contact;
