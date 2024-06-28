import './App.css';
import AnadirUsuario from './components/AnadirUsuario';
import EliminarUsuario from './components/EliminarUsuario';
import UsuarioPorId from './components/UsuarioPorId';
import ActualizarUsuario from './components/ActualizarUsuario'
import NavBar from './components/NavBar';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './components/Home';


function App() {
  return (
    <Router>
      <div className="App">
      <NavBar />
        <Routes>
          <Route path="/" element={<Home/>} />
          <Route path="/addUsuario" element={<AnadirUsuario/>} />
          <Route path="/deleteUsuario" element={<EliminarUsuario/>} />
          <Route path="/getUsuarioById" element={<UsuarioPorId/>} />
          <Route path="/updateUsuario" element={<ActualizarUsuario/>} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
