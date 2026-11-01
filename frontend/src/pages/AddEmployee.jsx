
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function AddEmployee() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    employeeCode: "",
    firstName: "",
    lastName: "",
    email: "",
    departmentId: "",
    jobTitle: "",
    country: "",
    currency: "INR",
    annualSalary: "",
    employmentStatus: "ACTIVE",
  });

  const [error, setError] = useState("");
  const [saving, setSaving] = useState(false);

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setSaving(true);

    try {
      await api.post("/employees", {
        ...form,
        departmentId: Number(form.departmentId),
        annualSalary: Number(form.annualSalary),
      });

      alert("Employee added successfully!");
      navigate("/employees");
    } catch (error) {
      setError(
        error.response?.data?.message || "Unable to add employee. Please check the details."
      );
    } finally {
      setSaving(false);
    }
  };

  return (
    <div>
      <div className="page-heading">
        <div>
          <h1>Add Employee</h1>
          <p>Enter the employee's information below.</p>
        </div>
      </div>

      <form className="content-card employee-form" onSubmit={handleSubmit}>
        <div className="form-grid">
          <div>
            <label>Employee Code</label>
            <input
              name="employeeCode"
              value={form.employeeCode}
              onChange={handleChange}
              required
            />
          </div>

          <div>
            <label>First Name</label>
            <input
              name="firstName"
              value={form.firstName}
              onChange={handleChange}
              required
            />
          </div>

          <div>
            <label>Last Name</label>
            <input
              name="lastName"
              value={form.lastName}
              onChange={handleChange}
              required
            />
          </div>

          <div>
            <label>Email</label>
            <input
              type="email"
              name="email"
              value={form.email}
              onChange={handleChange}
              required
            />
          </div>

          <div>
            <label>Department</label>
            <select
              name="departmentId"
              value={form.departmentId}
              onChange={handleChange}
              required
            >
              <option value="">Select department</option>
              <option value="1">Engineering</option>
              <option value="2">HR</option>
              <option value="3">Finance</option>
              <option value="4">Marketing</option>
              <option value="5">Operations</option>
              <option value="6">Sales</option>
            </select>
          </div>

          <div>
            <label>Job Title</label>
            <input
              name="jobTitle"
              value={form.jobTitle}
              onChange={handleChange}
              required
            />
          </div>

          <div>
            <label>Country</label>
            <input
              name="country"
              value={form.country}
              onChange={handleChange}
              required
            />
          </div>

          <div>
            <label>Currency</label>
            <select
              name="currency"
              value={form.currency}
              onChange={handleChange}
              required
            >
              <option value="INR">INR</option>
              <option value="USD">USD</option>
              <option value="GBP">GBP</option>
              <option value="EUR">EUR</option>
            </select>
          </div>

          <div>
            <label>Annual Salary</label>
            <input
              type="number"
              min="0.01"
              step="0.01"
              name="annualSalary"
              value={form.annualSalary}
              onChange={handleChange}
              required
            />
          </div>

          <div>
            <label>Employment Status</label>
            <select
              name="employmentStatus"
              value={form.employmentStatus}
              onChange={handleChange}
            >
              <option value="ACTIVE">Active</option>
              <option value="INACTIVE">Inactive</option>
            </select>
          </div>
        </div>

        {error && <p className="error-message">{error}</p>}

        <button className="primary-button" type="submit" disabled={saving}>
          {saving ? "Saving..." : "Add Employee"}
        </button>
      </form>
    </div>
  );
}

export default AddEmployee;
