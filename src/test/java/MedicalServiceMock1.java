import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MedicalServiceMock1 {


    @Test
    public void testCheckBloodPressure() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById("fde496e7-dd9f-4bfc-87ac-08400480b00e"))
                .thenReturn(new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        SendAlertService alertService = Mockito.mock(SendAlertService.class);

        ArgumentCaptor<String> argumetCaptor=ArgumentCaptor.forClass(String.class);
        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkBloodPressure("fde496e7-dd9f-4bfc-87ac-08400480b00e", new BloodPressure(100, 80));

        Mockito.verify(alertService).send(argumetCaptor.capture());
        Assertions.assertEquals("Warning, patient with id: null, need help",argumetCaptor.getValue());
    }

    @Test
    public void testCheckBloodTemperature() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById("fde496e7-dd9f-4bfc-87ac-08400480b00e"))
                .thenReturn(new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        SendAlertService alertService = Mockito.mock(SendAlertService.class);

        ArgumentCaptor<String> argumetCaptor=ArgumentCaptor.forClass(String.class);
        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkTemperature("fde496e7-dd9f-4bfc-87ac-08400480b00e", new BigDecimal("35.0"));

        Mockito.verify(alertService).send(argumetCaptor.capture());
        Assertions.assertEquals("Warning, patient with id: null, need help",argumetCaptor.getValue());
    }
}
