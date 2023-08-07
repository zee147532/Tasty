import './footer.scss';
import {
  FaPinterestSquare,
  FaInstagram,
  FaTwitter,
  FaFacebookSquare,
} from 'react-icons/fa'

import React from 'react';

import { Col, Row } from 'reactstrap';

const Footer = () => (
  <div className="footer-container">
    <div className="footer-heading-container">
      <img
        src="https://res.cloudinary.com/nsp/image/upload/v1635840304/tastyKitchens/logowhite_t8wfhc.png"
        alt="website-footer-logo"
        className="website-footer-logo"
      />
      <h1 className="footer-heading">Tasty Kitchens</h1>
    </div>
    <p className="footer-para">
      The only thing we are serious about is food. Contact us on
    </p>
    <div className="social-container">
      <FaPinterestSquare className="social-icon" />
      <FaInstagram className="social-icon" />
      <FaTwitter className="social-icon" />
      <FaFacebookSquare className="social-icon" />
    </div>
  </div>
);

export default Footer;
