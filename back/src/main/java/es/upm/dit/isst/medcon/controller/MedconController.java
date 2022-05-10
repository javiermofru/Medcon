package es.upm.dit.isst.medcon.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.http.HttpStatus;

import es.upm.dit.isst.medcon.model.Paciente;
import es.upm.dit.isst.medcon.model.Cita;
import es.upm.dit.isst.medcon.model.Medico;
import es.upm.dit.isst.medcon.repository.CitaRepository;
import es.upm.dit.isst.medcon.repository.PacienteRepository;
import es.upm.dit.isst.medcon.repository.MedicoRepository;

@CrossOrigin(origins = "*")
@RestController
public class MedconController {
    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;


    public static final Logger log = LoggerFactory.getLogger(MedconController.class);
    
    public MedconController(CitaRepository c, PacienteRepository p, MedicoRepository m) {
        this.citaRepository = c;
        c.save(new Cita("1","30/3/2022", "12:30", false , false , "123456789", "53880976V", null, "Traumatología" , 1,"Carlos Chinchilla"));
        c.save(new Cita("2","28/3/2022", "12:30", false , false , "123456789", "53880978V", null, "Radilogía", 1 ,"Cira Pozo"));
        c.save(new Cita("3","17/3/2022", "12:30", true , true ,"987654321", "53880979V", "P57", "Traumatología", 2 ,"Javier Moreno"));
        c.save(new Cita("4","30/3/2022", "11:30", true , true ,"123412341", "53880975V", "Q93", "Ginecología",2,"Alejandro Mariscal" ));
        c.save(new Cita("5","30/3/2022", "12:00", true , true , "432143214", "53880976V", "J42", "Análisis",1,"Carlos Chinchilla" ));


        this.pacienteRepository = p;
        p.save(new Paciente("53880976V","Carlos Chinchilla", "30/3/1985", "Hombre"));
        p.save(new Paciente("53880978V","Cira Pozo", "12/8/1994", "Mujer"));
        p.save(new Paciente("53880979V","Javier Moreno", "26/07/2000", "Hombre"));
        p.save(new Paciente("53880975V","Alejandro Mariscal", "3/2/1965", "Hombre"));
        p.save(new Paciente("53880974V","Miguel Varas", "19/7/1999", "Hombre"));
    

        this.medicoRepository = m;
        m.save(new Medico( "123456789" ,"Carlos Chinchilla", "medico1"));
        m.save(new Medico( "987654321" ,"Cira Pozo", "medico2"));
        m.save(new Medico( "123412341" ,"Javier Moreno", "medico3"));
        m.save(new Medico( "432143214" ,"Alejandro Mariscal", "medico4"));

    }

    @GetMapping("/citas")
    public List<Cita> getAllCitas() {
      return (List<Cita>)citaRepository.findAll();

    }

    @GetMapping("/paciente")
    public List<Paciente> getCitas() {
      return (List<Paciente>) pacienteRepository.findAll();
    }


    @GetMapping("/paciente/{dni}")
    public List<Cita> read(@PathVariable String dni){
      return citaRepository.findBydni(dni);
    }
    
    @GetMapping("/medico/{medico}")
    public List<Cita> citasMedico(@PathVariable String medico){
      return citaRepository.findBymedico(medico);
    }

    @GetMapping("/salaespera")
    List<Cita> findAll_by_salaEspera() {
      var citas = new ArrayList<Cita>();
      var resultado = new ArrayList<Cita>();
      citaRepository.findAll().forEach(citas::add);

      for(Cita cita:citas){
        if(cita.getLlamado()==true) resultado.add(cita);
      }
      return resultado;
    }
    

    @GetMapping("/medico/cita/{id}")
    List<Paciente> tablaMedico(@PathVariable String id){
      Cita cita = citaRepository.findById(id).get();
      String dni = cita.getDni();
      
      return pacienteRepository.findBydni(dni);


    }
    




    @PostMapping("/paciente/codigo/{id}")
    ResponseEntity<Cita> registraTicket(@PathVariable String id, @RequestBody String c) {

      return citaRepository.findById(id).map(cita -> {

        cita.setTicketTurno(c);
        cita.setRegistrado(true);

        citaRepository.save(cita);

        return ResponseEntity.ok().body(cita);

      }).orElse(new ResponseEntity<Cita>(HttpStatus.NOT_FOUND));  

    }

  /*   
    @PutMapping("/cita/{id}")
    public ResponseEntity updateClient(@PathVariable Long id, @RequestBody Cita cita) {
        Cita currentCita = citaRepository.findById(id).orElseThrow(RuntimeException::new);
        currentCita.setTicket(cita.getTicket());
        currentCita = citaRepository.save(cita);


        return ResponseEntity.ok(currentCita);
    } */

    @CrossOrigin(origins = "*")
    @PutMapping("/medico/{colegiado}/crear")
    public ResponseEntity<Cita> newCita(@RequestBody Cita newCita) {
      
      Cita cita = new Cita();

      cita.setFecha(newCita.getFecha());

      cita.setHora(newCita.getHora());

      cita.setLlamado(newCita.getLlamado());

      cita.setRegistrado(newCita.getRegistrado());

      cita.setMedico(newCita.getMedico());
      cita.setDni(newCita.getDni());
      cita.setTicketTurno(newCita.getTicketTurno());
      cita.setRazon(newCita.getRazon());
      cita.setSala_consulta(newCita.getSala_consulta());
      cita.setnombrePaciente(newCita.getnombrePaciente());
      citaRepository.save(cita);

      return ResponseEntity.ok().body(cita);

    }
    
    @PostMapping("/medico/{colegiado}/{id}")
    public ResponseEntity<Cita> deleteClient(@PathVariable String id) {
      citaRepository.deleteById(id);
      return ResponseEntity.ok().build();
    }

    @PostMapping("/medico/{colegiado}/llamar/{id}")
    ResponseEntity<Cita> registraTicket(@PathVariable String id) {
      return citaRepository.findById(id).map(cita -> {
        cita.setLlamado(true);
        citaRepository.save(cita);
        return ResponseEntity.ok().body(cita);
      }).orElse(new ResponseEntity<Cita>(HttpStatus.NOT_FOUND));  
    } 

    @GetMapping("/medico/{colegiado}/pacientes/{dni}")
    public List<Paciente> readPacientes (@PathVariable String dni){
      return pacienteRepository.findBydni(dni);
    }

    @GetMapping("/medico/pacientes")
    public List<Paciente> getPacientes() {
      return (List<Paciente>) pacienteRepository.findAll();

    }

    @GetMapping("/medico")
    public List<Medico> getMedicos(){
      return (List<Medico>) medicoRepository.findAll();
    }



    /* @PostMapping("/Medcon")
    ResponseEntity<Medcon> create(@RequestBody Medcon newMedcon) throws URISyntaxException {
      Medcon result = medRepository.save(newMedcon);
      return ResponseEntity.created(new URI("/Medcons/" + result.getEmail())).body(result);
    } */

    /* PACIENTE:
     1º Para un DNI buscar las citas que tiene /kiosko/{dni} --> HECHO
     2º Confirmar presencia en la bbdd para una cita y subir el ticket /kiosko/dni/{cita} --> HECHO
     3º Para la cita seleccionada, introducir el ticket a la bbdd --> HECHO
    */

    /* SALA ESPERA:
     1º Obtener las citas con varibable llamado=true /espera/{sala} --> HECHO
    */

    /* MÉDICO: 
     1º Pasar todos los médico registrados /medico --> HECHO
     2º Para un médico, pasar todas sus citas /medico/citas --> HECHO
     3º Pasar todos los pacientes /medico/pacientes --> HECHO
     4º Para un médico, crear una nueva cita /medico/agenda
     5º Para un médico, borrar una cita --> MEDIO HECHO

    */
 
}