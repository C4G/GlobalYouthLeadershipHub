import React from "react";
import "../styles/landingPage.css";

const LandingPage = () => {
  return (
    <div className="container">
      <div className="left-section">
        <img
          src="/organizationLogo.jpg"
          alt="Legacy International Logo"
          className="organization-logo"
        />
        <p className="description hidden-on-mobile">
          An initiative of the U.S. Department of State's Bureau of Educational
          and Cultural Affairs (ECA) administered by Legacy International
        </p>
        <img
          src="/programLogo.jpg"
          alt="Youth Leadership Logo"
          className="program-logo"
        />
      </div>

      <div className="right-section">
        <div className="form-container">
          <form>
            <div className="input-group">
              <label htmlFor="email">Email address</label>
              <input
                type="email"
                id="email"
                placeholder="Enter your email address"
                aria-required="true"
              />
            </div>
            <div className="input-group">
              <label htmlFor="password">Password</label>
              <input
                type="password"
                id="password"
                placeholder="********"
                aria-required="true"
              />
            </div>
            <button type="submit" className="sign-in-btn">
              SIGN IN
            </button>
          </form>
          <hr />
          <button type="button" className="create-account-btn">
            CREATE ACCOUNT
          </button>
        </div>
      </div>
    </div>
  );
};

export default LandingPage;
