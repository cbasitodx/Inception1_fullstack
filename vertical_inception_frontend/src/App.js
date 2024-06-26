import React, { useState } from 'react';
import axios from 'axios';

function App() {
  const [userId, setUserId] = useState('');
  const [units, setUnits] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleInputChange = (e) => {
    setUserId(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    fetchUnits(userId);
  };

  const fetchUnits = (id) => {
    setLoading(true);
    setError(null);
    axios.get(`/epac/usuario/getUnitsOfUserById/${id}`)
      .then(response => {
        setUnits(response.data);
        setLoading(false);
      })
      .catch(error => {
        setError('Hubo un error al hacer la solicitud. Por favor, intenta nuevamente.');
        setLoading(false);
      });
  };

  return (
    <div className="App">
      <h1>Buscar Unidades del Usuario</h1>
      <form onSubmit={handleSubmit}>
        <label>
          ID del Usuario:
          <input type="text" value={userId} onChange={handleInputChange} />
        </label>
        <button type="submit">Buscar</button>
      </form>
      {loading && <p>Cargando...</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {!loading && !error && (
        <ul>
          {units.length > 0 ? (
            units.map((unit, index) => (
              <li key={index}>{unit}</li>
            ))
          ) : (
            <p>No se encontraron unidades para este usuario.</p>
          )}
        </ul>
      )}
    </div>
  );
}

export default App;