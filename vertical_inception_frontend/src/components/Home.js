import React, { useEffect, useState } from 'react'
import { Container, Paper } from '@mui/material';

export default function Home() {
  
    const paperStyle={padding:'50px 20px', width:600, margin:'20px auto'}
    const[usuarios, setUsuarios]=useState([])

    useEffect(()=>{
        fetch("http://localhost:8082/epac/usuario/usuarios")
        .then(res=>res.json())
        .then((result)=>{
            setUsuarios(result);
        })
    },[])

    return (
    <Container>
        <h1>Bienvenido a la vertical de la primera inception del EPAC! A continuación, los usuarios en la plataforma</h1>
        <Paper elevation={3} style={paperStyle}>
            {usuarios.map(usuario=>(
                <Paper elevation={6} style={{margin:"10px",padding:"15px",textAlign:"left"}} key={usuario.id_usuario}>
                    Id:{usuario.id_usuario}<br></br>
                    Nombre:{usuario.nombre_usuario}<br></br>
                    Rol:{usuario.rol}<br></br>
                </Paper>
            ))}
        </Paper>
    </Container>
  )
}
