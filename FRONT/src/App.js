import logo from "./Assets/SaludMadrid.svg.png";
import "./App.css";
import Codigo from "./Componentes/Kiosko/Codigo";
import { Navbar, Container, Nav, NavDropdown } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./Componentes/Home";
import Footer from "./Componentes/Footer";
import PacienteLogin from "./Componentes/Kiosko/LoginPaciente";
import CitasPaciente from './Componentes/Kiosko/CitasPacientes';
import LoginMedico from "./Componentes/Medico/LoginMedico";
import CitasMedico from "./Componentes/Medico/CitasMedico";
import SalaEspera from "./Componentes/SalaEspera/SalaEspera";
import Crear from "./Componentes/Medico/Crear";
import Pacientes from "./Componentes/Medico/Pacientes"

function App() {
  return (
    <div className="App" id="main">
      <Navbar className="bg-secondary opacity-100">
        <Container>
          <Navbar.Brand href="/">
            <img
              alt=""
              src={logo}
              width="50"
              height="auto"
              className="d-inline-block align-top"
            />

            <span className="text-white">MedCon</span>
          </Navbar.Brand>
          <Nav className="me-auto">
            <Nav.Link href="paciente" className="text-white">Kiosko</Nav.Link>
            <Nav.Link href="medico" className="text-white">Médico</Nav.Link>
            <Nav.Link href="salaespera" className="text-white">Sala de espera</Nav.Link>
          </Nav>

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
          <Route path="medico/:colegiado/crear" element={<Crear />} />
          <Route path="medico/pacientes" element={<Pacientes />}/>
          <Route path="salaespera" element={<SalaEspera />}/>
        </Routes>
      </BrowserRouter>
        <Footer />
    </div>
  );
}

export default App;
