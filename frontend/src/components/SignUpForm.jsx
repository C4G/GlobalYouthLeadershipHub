import { useState, useMemo } from "react";
import { useNavigate } from "react-router-dom";
import { useMutation } from "@tanstack/react-query";
import customFetcher from "@/services/api";

import styles from "./SignUpForm.module.css";

const SignUpForm = () => {
  const navigate = useNavigate();
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");

  const [day, setDay] = useState("");
  const [month, setMonth] = useState("");
  const [year, setYear] = useState("");

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const [error, setError] = useState("");

  const thisYear = new Date().getFullYear();
  const yearsRange = 40;

  const daysInMonth = useMemo(() => {
    if (!month || !year) return 31;
    return new Date(Number(year), Number(month), 0).getDate();
  }, [month, year]);

  const dayOptions = Array.from({ length: daysInMonth }, (_, i) => i + 1);
  const yearOptions = Array.from(
    { length: yearsRange },
    (_, i) => thisYear - i
  );
  const monthLabels = [
    "Jan",
    "Feb",
    "Mar",
    "Apr",
    "May",
    "Jun",
    "Jul",
    "Aug",
    "Sep",
    "Oct",
    "Nov",
    "Dec",
  ];

  const mutation = useMutation({
    mutationFn: async (userData) => {
      return await customFetcher("/auth/register", "POST", null, userData);
    },
    onSuccess: () => {
      navigate("/signup-success", { replace: true });
    },
    onError: (error) => {
      setError(error.message || "Registration failed");
    },
  });

  const handleFirstName = (e) => {
    setFirstName(e.target.value);
  };

  const handleLastName = (e) => {
    setLastName(e.target.value);
  };

  const handleDayChange = (e) => {
    const selectedDay = Number(e.target.value);
    const maxDay = daysInMonth;
    setDay(String(Math.min(selectedDay, maxDay)));
  };
  const handleMonthChange = (e) => {
    setMonth(e.target.value);
  };
  const handleYearChange = (e) => {
    setYear(e.target.value);
  };

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handlePasswordChange = (e) => {
    const newPassword = e.target.value;
    setPassword(newPassword);

    if (newPassword.length < 8) {
      setError("Password must be at least 8 characters long");
    } else if (confirmPassword && newPassword !== confirmPassword) {
      setError("Passwords do not match");
    } else {
      setError("");
    }
  };

  const handleConfirmPasswordChange = (e) => {
    setConfirmPassword(e.target.value);
    if (password && e.target.value !== password) {
      setError("Passwords do not match");
    } else {
      setError("");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!firstName || !lastName) {
      setError("First Name & Last Name are required!");
      return;
    }

    if (!email) {
      setError("Email is required!");
      return;
    }

    if (!day || !month || !year) {
      setError("Date of Birth is required!");
      return;
    }

    if (password !== confirmPassword) {
      setError("Password do not match!");
      return;
    }

    setError("");

    const selectedDateOfBirth = new Date(
      `${year}-${month.padStart(2, "0")}-${day.padStart(2, "0")}`
    );
    const formattedISODate = selectedDateOfBirth.toISOString();
    const userData = {
      email,
      password,
      firstName,
      lastName,
      dateOfBirth: formattedISODate,
    };

    mutation.mutate(userData);
  };

  return (
    <>
      <form className={styles.registerForm} onSubmit={handleSubmit}>
        {/* Name Fields */}
        <div className={styles.nameGroup}>
          <div className={styles.inputGroup}>
            <input
              type="text"
              id="first-name"
              name="first-name"
              placeholder="First name *"
              aria-required="true"
              value={firstName}
              onChange={handleFirstName}
              required
            />
          </div>
          <div className={styles.inputGroup}>
            <input
              type="text"
              id="last-name"
              name="last-name"
              placeholder="Last name *"
              aria-required="true"
              value={lastName}
              onChange={handleLastName}
              required
            />
          </div>
        </div>

        {/* Date of Birth */}
        <div className={styles.dobGroup}>
          <label className={styles.dobGlabel}>Date of Birth</label>
          <div className={styles.dobFields}>
            <select
              name="day"
              aria-label="Day"
              value={day}
              onChange={handleDayChange}
              required
            >
              <option value="">Day</option>
              {dayOptions.map((d) => (
                <option key={d} value={d}>
                  {d}
                </option>
              ))}
            </select>
            <select
              name="month"
              aria-label="Month"
              value={month}
              onChange={handleMonthChange}
              required
            >
              <option value="">Month</option>
              {monthLabels.map((label, i) => (
                <option key={i + 1} value={i + 1}>
                  {label}
                </option>
              ))}
            </select>
            <select
              name="year"
              aria-label="Year"
              value={year}
              onChange={handleYearChange}
              required
            >
              <option value="">Year</option>
              {yearOptions.map((y) => (
                <option key={y} value={y}>
                  {y}
                </option>
              ))}
            </select>
          </div>
        </div>

        {/* Email & Password */}
        <div className={styles.emailAddress}>
          <input
            type="email"
            id="email"
            name="email"
            placeholder="Email Address"
            aria-required="true"
            value={email}
            onChange={handleEmailChange}
            required
          />
        </div>
        <div className={styles.newPassword}>
          <input
            type="password"
            id="password"
            name="password"
            placeholder="New Password"
            value={password}
            onChange={handlePasswordChange}
            aria-required="true"
            required
          />
        </div>
        <div className={styles.confirmPassword}>
          <input
            type="password"
            id="confirmPassword"
            name="confirmPassword"
            placeholder="Confirm Password"
            value={confirmPassword}
            onChange={handleConfirmPasswordChange}
            aria-required="true"
            required
          />
        </div>

        {/* Error Message */}
        {error && <p className={styles.errorMessage}>{error}</p>}

        {/* Submit Button */}
        <button type="submit" className={styles.createAccountBtn}>
          REGISTER ACCOUNT
        </button>
      </form>
    </>
  );
};

export default SignUpForm;
