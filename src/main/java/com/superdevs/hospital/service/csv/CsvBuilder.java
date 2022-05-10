package com.superdevs.hospital.service.csv;

import com.superdevs.hospital.entity.Patient;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CsvBuilder {

    private static final String PATIENT_FILE_PATH = "./patient-data/patient.csv";

    public File createCsvFileWithPatientData(Patient patient) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(PATIENT_FILE_PATH));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.builder()
                .setHeader("ID", "Name", "Age", "LastVisitDate")
                .build())) {
            csvPrinter.printRecord(patient.getId(), patient.getName(), patient.getAge(), patient.getLastVisitDate());
            csvPrinter.flush();
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
        return new File(PATIENT_FILE_PATH);
    }
}
