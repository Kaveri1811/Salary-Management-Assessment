
import { useState } from "react";
import api from "../services/api";

function SalaryHistory() {
  const [employeeId, setEmployeeId] = useState("");
  const [history, setHistory] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const fetchHistory = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");
    setHistory([]);

    try {
      const response = await api.get(
        `/employees/${employeeId}/salary-revisions`
      );

      setHistory(response.data);
    } catch (error) {
      setError("Unable to retrieve salary history. Check the employee ID.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <div className="page-heading">
        <div>
          <h1>Salary History</h1>
          <p>Review employee salary revisions.</p>
        </div>
      </div>

      <section className="content-card">
        <form className="history-search" onSubmit={fetchHistory}>
          <input
            type="number"
            min="1"
            placeholder="Enter Employee ID"
            value={employeeId}
            onChange={(e) => setEmployeeId(e.target.value)}
            required
          />

          <button className="primary-button" type="submit">
            Search
          </button>
        </form>

        {loading && <p>Loading salary history...</p>}
        {error && <p className="error-message">{error}</p>}

        {!loading && history.length > 0 && (
          <div className="table-container">
            <table>
              <thead>
                <tr>
                  <th>Revision ID</th>
                  <th>Previous Salary</th>
                  <th>New Salary</th>
                  <th>Effective Date</th>
                  <th>Reason</th>
                </tr>
              </thead>

              <tbody>
                {history.map((item) => (
                  <tr key={item.id}>
                    <td>{item.id}</td>
                    <td>{Number(item.previousSalary).toLocaleString()}</td>
                    <td>{Number(item.newSalary).toLocaleString()}</td>
                    <td>{item.effectiveDate}</td>
                    <td>{item.reason}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}

        {!loading && employeeId && !error && history.length === 0 && (
          <p className="empty-message">No salary revisions found.</p>
        )}
      </section>
    </div>
  );
}

export default SalaryHistory;
