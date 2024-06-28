import React, { useState } from "react";

import "./NavBar.css";
import { Link, NavLink } from "react-router-dom";

export default function NavBar() {
  const [menuOpen, setMenuOpen] = useState(false);

  return (
    <nav>
      <Link to="/" className="title">
        Home
      </Link>
      <div className="menu" onClick={() => setMenuOpen(!menuOpen)}>
        <span></span>
        <span></span>
        <span></span>
      </div>
      <ul className={menuOpen ? "open" : ""}>
        <li>
          <NavLink to="/addUsuario">Añadir Usuario</NavLink>
        </li>
        <li>
          <NavLink to="/deleteUsuario">Eliminar Usuario</NavLink>
        </li>
        <li>
          <NavLink to="/getUsuarioById">Obtener por Id</NavLink>
        </li>
        <li>
          <NavLink to="/updateUsuario">Actualizar usuario</NavLink>
        </li>
      </ul>
    </nav>
  );
};