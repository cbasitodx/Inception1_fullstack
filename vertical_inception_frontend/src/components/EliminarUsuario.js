import React, { useState } from 'react';
import Box from '@mui/material/Box';
import { Container, Paper } from '@mui/material';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

export default function EliminarUsuario() {

  const paperStyle = { padding: '50px 20px', width: 600, margin: '20px auto' };

  const [id, setId] = useState('');
  const [mensaje, setMensaje] = useState('');

  const handleClick = (e) => {
    e.preventDefault();
    const url = "http://localhost:8082/epac/usuario/deleteGivenId/" + id;

    fetch(url, {
      method: "DELETE"
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Error en la petición');
        }
        return response.json();
      })
      .then(() => {
        setMensaje(`Usuario de id ${id} eliminado`);
        setId('');
      })
      .catch(() => {
        setMensaje('Error en la petición!');
      });
  };

  return (
    <Container>
      <Paper elevation={3} style={paperStyle}>
        <h1 style={{ color: "rgb(77, 77, 255)" }}>Eliminar Usuario</h1>
        <Box
          component="form"
          sx={{
            display: 'flex',
            flexDirection: 'column',
            '& > :not(style)': { m: 1, flex: 1 },
          }}
          noValidate
          autoComplete="off"
        >

          {/* UsuarioText */}
          <TextField
            id="outlined-basic"
            label="Id del usuario"
            variant="outlined"
            fullWidth
            value={id}
            onChange={(e) => setId(e.target.value)}
          />

          <Button variant="contained" color="error" fullWidth onClick={handleClick}>Eliminar</Button>

        </Box>
      </Paper>
      <div>
        {mensaje && <h1>{mensaje}</h1>}
      </div>
    </Container>
  );
}