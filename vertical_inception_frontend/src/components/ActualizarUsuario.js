import React, { useState } from 'react';
import Box from '@mui/material/Box';
import { Container, Paper } from '@mui/material';

import TextField from '@mui/material/TextField';

import MenuItem from '@mui/material/MenuItem';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';

import Button from '@mui/material/Button';

// Funcion para hallar un string que sea una fecha con formato "AAAA-MM-DD"
function getFormattedDate() {
  const today = new Date();
  
  const year = today.getFullYear();
  const month = (today.getMonth() + 1).toString().padStart(2, '0'); // Los meses en JavaScript son base 0
  const day = today.getDate().toString().padStart(2, '0');
  
  return `${year}-${month}-${day}`;
}

export default function AnadirUsuario() {

    const paperStyle={padding:'50px 20px', width:600, margin:'20px auto'} 

    const[id, setId]=useState('')
    const[nombre, setNombre]=useState('')
    const[correo, setCorreo]=useState('')
    const[rol, setRol] = React.useState('')
    const[petHecha, setPetHecha]=useState(false) // Esta variable sirve para saber si renderizamos el usuario anadido o no
    const[usuario, setUsuario]=useState('')
    const[mensaje, setMensaje] = useState('')

    const handleChange = (event) => {
      setRol(event.target.value);
    };

    const handleClick=(e)=>{
      e.preventDefault()
      const dummy_date=getFormattedDate()
      
      const usuario=
      {
        nombre_usuario:nombre, 
        correo:correo, 
        rol:rol, 
        dummy_date:dummy_date
      } 

      if(id.replace(/ /g,'')=== '' || nombre.replace(/ /g,'')=== '' || correo.replace(/ /g,'') === '' || rol.replace(/ /g,'') === '') {
        setMensaje('Error en la petición!');
      }

      else {
        const url = "http://localhost:8082/epac/usuario/updateGivenId/" + id;
        fetch(url, 
          {
          method:"PUT",
          headers:{"Content-Type":"application/json"},
          body:JSON.stringify(usuario)
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
          setMensaje('Usuario Actualizado');
          console.log("Usuario actualizado");
          setNombre('');
          setCorreo('');
          setRol('');
          setId('');
        })
        .catch(()=>{
          setMensaje('Error en la petición');
        })
      }
    }

    return (
      <Container>
        <Paper elevation={3} style={paperStyle}>
          <h1 style={{color:"rgb(77, 77, 255)"}}>Actualizar Usuario</h1>
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
        
        <TextField id="outlined-basic" label="Id" variant="outlined" fullWidth 
        value={id}
        onChange={(e) => setId(e.target.value)}
        />     
        <TextField id="outlined-basic" label="Nombre" variant="outlined" fullWidth 
        value={nombre}
        onChange={(e) => setNombre(e.target.value)}
        />        
        <TextField id="outlined-basic" label="Correo" variant="outlined" fullWidth 
        value={correo}
        onChange={(e) => setCorreo(e.target.value)}
        />
        
        <FormControl fullWidth> 
        <InputLabel id="demo-simple-select-label">Rol</InputLabel>
        <Select
          labelId="demo-simple-select-label"
          id="demo-simple-select"
          value={rol}
          label="Rol"
          onChange={handleChange}
          fullWidth
        >
          <MenuItem value={"USUARIO"}>Usuario</MenuItem>
          <MenuItem value={"ADMINISTRADOR"}>Administrador</MenuItem>
          <MenuItem value={"SUPER_ADMINISTRADOR"}>Super-Administrador</MenuItem>
        </Select>
        </FormControl>
        
        <Button variant="contained" fullWidth onClick={handleClick}>Enviar</Button>
        
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