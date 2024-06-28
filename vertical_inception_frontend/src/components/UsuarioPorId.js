import React, { useState } from 'react';
import Box from '@mui/material/Box';
import { Container, Paper } from '@mui/material';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

export default function UsuarioPorId() {

    const paperStyle={padding:'50px 20px', width:600, margin:'20px auto'} 

    const[id, setId]=useState('')
    const [petHecha, setPetHecha]=useState(false) // Esta variable sirve para saber si renderizamos el usuario anadido o no
    const [usuario, setUsuario]=useState('')
    const [mensaje, setMensaje] = useState('')

    const handleClick=(e)=>{
      e.preventDefault()
      const url = "http://localhost:8082/epac/usuario/getById/" + id

      fetch(url, 
        {
        method:"GET"
        }
      )
      .then(res=>{
        if (!res.ok) {
          throw new Error('Error en la petición');
        }
        return res.json();
      })
      .then((result)=>{
        setUsuario(result);
        setPetHecha(true);
        setMensaje('Usuario Obtenido');
        console.log("Usuario de id " + id + " obtenido");
        setId('');
      })
      .catch(()=>{
        setMensaje('Error en la petición');
      })
    }

    return (
      <Container>
        <Paper elevation={3} style={paperStyle}>
          <h1 style={{color:"rgb(77, 77, 255)"}}>Obtener Usuario Por Id</h1>
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
        
        <TextField id="outlined-basic" label="Id del usuario" variant="outlined" fullWidth 
        value={id}
        onChange={(e) => setId(e.target.value)}
        />
        
        <Button variant="contained" fullWidth onClick={handleClick}>Obtener Usuario</Button>
        
        </Box>
        </Paper>
        <div>
            {mensaje && 
                (
                    <div>
                        <h1>{mensaje}</h1>
                        {
                        petHecha &&
                          <Paper elevation={6} style={{margin:"10px",padding:"15px",textAlign:"left"}} key={usuario.id_usuario}>
                          Id:{usuario.id_usuario}<br></br>
                          Nombre:{usuario.nombre_usuario}<br></br>
                          Rol:{usuario.rol}<br></br>
                          </Paper>
                        }
                    </div>
                )
            }
        </div>
      </Container>
    );
  }