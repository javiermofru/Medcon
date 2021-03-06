import logo from "./Assets/SaludMadrid.svg.png";
import "./App.css";
import Codigo from "./Componentes/Kiosko/Codigo";
import { Navbar, Container, Nav, NavDropdown } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter, Routes, Route, useParams } from "react-router-dom";
import Home from "./Componentes/Home";
import Footer from "./Componentes/Footer";
import PacienteLogin from "./Componentes/Kiosko/LoginPaciente";
import CitasPaciente from './Componentes/Kiosko/CitasPacientes';
import LoginMedico from "./Componentes/Medico/LoginMedico";
import CitasMedico from "./Componentes/Medico/CitasMedico";
import SalaEspera from "./Componentes/SalaEspera/SalaEspera";
import Crear2 from "./Componentes/Medico/Crear2";
//import Crear3 from "./Componentes/Medico/crear3";
import Pacientes from "./Componentes/Medico/Pacientes";
import Info from "./Componentes/Medico/Info";
//



//import Login from "./Components/Medico/Login";

function App() {
  return (
    <div className="App" id="main">
      <Navbar className="bg-secondary  border-bottom-3 border border-success    opacity-100">
        <Container>
          <Navbar.Brand href="/" id="logo">
            <img
              alt=""
              src={logo}
              width="50"
              height="auto"
              className="d-inline-block align-top"
            />

            <span className="text-white">MedCon</span>
          </Navbar.Brand>

        </Container>
      </Navbar>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="paciente" element={<PacienteLogin />} />
          <Route path="paciente/:dni" element={<CitasPaciente />} />
          <Route path="paciente/codigo/:citaId" element={<Codigo />} />
          <Route path="medico" element={<LoginMedico />} />
          <Route path="medico/:medico" element={<CitasMedico />} />
          <Route path="medico/:medico/:id" element={<CitasMedico />} />
          <Route path="medico/:medico/llamar/:id" element={<CitasMedico />} />
          <Route path="medico/:colegiado/crear" element={<Crear2/>} />
          <Route path="medico/:colegiado/pacientes" element={<Pacientes />}/>
          <Route path="medico/:colegiado/pacientes/:dni" element={<Info />}/>
          <Route path="salaespera" element={<SalaEspera />}/>
          {/* <Route path="/login" element={<Login />}/> */}
        </Routes>
      </BrowserRouter>
        <Footer />
    </div>
  );
}

export default App;
