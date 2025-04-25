package org.hbn.patientservice.kafka;

import org.hbn.patientservice.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;


@Service
public class KafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    // Kafka mainly used to communicate with 1 to Many microservices
    // whereas grpc and REST are good with 1 to 1 service communication
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    //constructor injection is recommendable @Autowired is deprecated
    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient) {
        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT-CREATED")
                .build();

        try{
            kafkaTemplate.send("patient", event.toByteArray() );
        }
        catch (Exception e){
            log.error("Error sending patientCreated event {}", event);
        }
    }

}
